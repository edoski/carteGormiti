package main;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class CreateGameController implements Initializable {
	@FXML
	private Label createGameTitleLabel;

	@FXML
	private Label codeLabel;
	static String code;
	// HashSet per memorizzare i codici generati
	static HashSet<String> activeCodes = new HashSet<>();

	@FXML
	private Label player1, player2, player3, player4;
	static ArrayList<Player> players;
	static int CPUCount = 1;
	@FXML
	private TextField playerNameTextField;
	@FXML
	private Button startGameBtn;
	@FXML
	private Button goBackBtn;

	Parent root;
	Scene scene;
	Stage stage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		players = new ArrayList<>();

		// TODO: MAYBE INSTEAD OF ALERTS, USE LABEL ATOP OF PLAYERS TO SHOW EITHER "PARTITA SINGOLA" OR "TORNEO" DEPENDING ON players.size()

		// FOR STYLE CONSISTENCY IF USER LEAVES CREATE GAME SCREEN AND RETURNS
		createGameTitleLabel.setStyle("-fx-font-weight: bold");
		codeLabel.setStyle("-fx-font-weight: bold");
		player1.setStyle("-fx-font-weight: bold");
		player2.setStyle("-fx-font-weight: bold");
		player3.setStyle("-fx-font-weight: bold");
		player4.setStyle("-fx-font-weight: bold");

		// TODO: GET CODES FROM FILESYSTEM AND LOAD THEM ONTO THE HASHSET
		generateCode();
	}

	public void switchToScene(String fxmlFile, Button btn) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
		root = loader.load();
		scene = new Scene(root);
		stage = (Stage) btn.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void goBackLoginScreen() throws IOException {
		switchToScene("MainMenu.fxml", goBackBtn);
	}

	/*
	 * addRobotBtn, aggiunge un robot alla lista di giocatori,
	 * il nome del robot viene automaticamente inizializzato come "CPU 1" o "CPU 2" etc.
	 *
	 * Se l'inserimento del robot corrisponde al terzo giocatore, viene lanciato un alert (vedi commenti su addHuman() al riguardo)
	 */
	@FXML
	void addRobot() {
		if (players.size() < 4) {
			Player player = new Player("CPU 0" + CPUCount++);
			player.isCPU = true;
			players.add(player);

			switch (players.size()) {
				case 1:
					player1.setText("Player 1: " + player.getName());
					break;
				case 2:
					player2.setText("Player 2: " + player.getName());
					break;
				case 3:
					player3.setText("Player 3: " + player.getName());
					break;
				case 4:
					player4.setText("Player 4: " + player.getName());
					break;
			}
		} else {
			// Non puoi aggiungere più di 4 giocatori
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Non puoi aggiungere più di 4 giocatori");
			alert.showAndWait();
		}
	}

	/*
	 * addPlayerBtn, aggiunge un umano alla partita, il quale nome deriva dalla stringa immessa nel text field playerNameTextField.
	 *
	 * All'inserimento del terzo giocatore, viene lanciato un alert per avvertire il fatto che si entra in un torneo aggiungendolo,
	 * e dando l'opzione o di confermare il terzo giocatore, o di annullare il suo inserimento
	 */
	@FXML
	void addHuman() {
		String playerName = playerNameTextField.getText();

		// Check if the name is already taken
		for (Player player : players) {
			if (player != null && player.getName().equals(playerName)) {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText("Nome già in uso");
				alert.showAndWait();
				return;
			}
		}

		// Check if the text field is empty
		if (playerName.isEmpty()) {
			playerNameTextField.setStyle("-fx-border-color: red");
			playerNameTextField.setStyle("-fx-prompt-text-fill: red");
			playerNameTextField.setPromptText("Inserisci un nome valido");
		} else {
			if (players.size() < 4) {
				playerNameTextField.setStyle("-fx-border-color: none");
				playerNameTextField.setStyle("-fx-prompt-text-fill: grey");
				playerNameTextField.setPromptText("Player Name");

				Player player = new Player(playerName);
				players.add(player);

				switch (players.size()) {
					case 1:
						player1.setText("Player 1: " + playerName);
						break;
					case 2:
						player2.setText("Player 2: " + playerName);
						break;
					case 3:
						player3.setText("Player 3: " + playerName);
						break;
					case 4:
						player4.setText("Player 4: " + playerName);
						break;
				}

				playerNameTextField.clear();
			} else {
				// Non puoi aggiungere più di 4 giocatori
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText("Non puoi aggiungere più di 4 giocatori");
				alert.showAndWait();
			}
		}
	}

	/*
	 * Admin genera una stringa che viene associata alla partita appena creata,
	 * attraverso questo codice si può riprendere una partita.
	 *
	 * Il codice viene generato appena inserito il primo giocatore.
	 */
	public void generateCode() {
		Random rand = new Random();
		code = "";
		for (int i = 0; i < 4; i++) {
			if (i < 2) {
//				Generiamo due caratteri maiuscoli in A-Z per la prima parte del codice
				code += (char) (rand.nextInt(26) + 'A');

			} else {
//				Generiamo due interi casuali in [0,9] per la seconda parte del codice
				code += rand.nextInt(9);
			}
		}

		if (activeCodes.contains(code)) {
			generateCode();
		} else {
			codeLabel.setText("Code: " + code);
		}
	}

	/*
	 * removeRecentBtn, rimuove l'inserimento del giocatore (robot o umano) più recente dalla lista
	 */
	@FXML
	void removeRecent() {
		if (!players.isEmpty()) {
			switch (players.size()) {
				case 1:
					player1.setText("Player 1: ");
					break;
				case 2:
					player2.setText("Player 2: ");
					break;
				case 3:
					player3.setText("Player 3: ");
					break;
				case 4:
					player4.setText("Player 4: ");
					break;
			}

			Player player = players.getLast();
			if (player.getName().contains("CPU") && CPUCount > 1) {
				CPUCount--;
			}
			players.removeLast();
		}
	}

	/*
	 * startGameBtn, lancia la partita, o torneo, in base al numero di giocatori aggiunti.
	 *
	 * Partita singola: 2 giocatori
	 * Torneo: 4 giocatori
	 *      - Arrotondato a 4 se sono aggiunti 3 giocatori/robot
	 */
	@FXML
	void startGame() throws IOException {
		Game.isNewGame = true;
		switch (players.size()) {
			case 0:
				// Non ci sono giocatori
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Warning");
				alert.setHeaderText("Non ci sono giocatori");
				alert.setContentText("Aggiungi almeno un giocatore per iniziare la partita");
				alert.showAndWait();
				break;
			case 1:
				// Partita singola
				Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
				alert1.setTitle("Partita singola");
				alert1.setHeaderText("Sei sicuro di voler iniziare una partita singola?");
				alert1.setContentText("Confermando, verrà aggiunto un giocatore CPU per completare la partita");
				alert1.showAndWait();
				if (alert1.getResult().getText().equals("OK")) {
					Player player2 = new Player("CPU 0" + ++CPUCount);
					player2.isCPU = true;
					players.add(player2);
					Game.setPlayers(players);
					activeCodes.add(code);
					switchToScene("Game.fxml", startGameBtn);
				} else {
					alert1.close();
				}
				break;
			case 2:
				// Partita singola
				Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
				alert2.setTitle("Partita singola");
				alert2.setHeaderText("Sei sicuro di voler iniziare una partita singola?");
				alert2.setContentText("Confermando, comincerà la partita con i due giocatori inseriti");
				alert2.showAndWait();
				if (alert2.getResult().getText().equals("OK")) {
					Game.setPlayers(players);
					activeCodes.add(code);
					switchToScene("Game.fxml", startGameBtn);
				} else {
					alert2.close();
				}
				break;
			case 3:
				// Torneo
				Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION);
				alert3.setTitle("Torneo");
				alert3.setHeaderText("Sei sicuro di voler iniziare un torneo?");
				alert3.setContentText("Confermando, verrà aggiunto un giocatore CPU per completare il torneo");
				alert3.showAndWait();
				if (alert3.getResult().getText().equals("OK")) {
					Player player4 = new Player("CPU 0" + ++CPUCount);
					player4.isCPU = true;
					players.add(player4);

					// TODO: Start Torneo
					Game.isTournament = true;
					Random rand = new Random();
					int p1, p2;
					ArrayList<Player> match1 = new ArrayList<>(), match2 = new ArrayList<>(), match3 = new ArrayList<>();

					p1 = rand.nextInt(players.size());
					match1.add(players.remove(p1));
					p2 = rand.nextInt(players.size());
					match1.add(players.remove(p2));
					Game.setPlayers(match1);
					activeCodes.add(code + "-1");
					switchToScene("Game.fxml", startGameBtn);
					Player winner1 = Game.selectGameWinner();

					p1 = rand.nextInt(players.size());
					match2.add(players.remove(p1));
					p2 = rand.nextInt(players.size());
					match2.add(players.remove(p2));
					Game.setPlayers(match2);
					activeCodes.add(code + "-2");
					switchToScene("Game.fxml", startGameBtn);
					Player winner2 = Game.selectGameWinner();

//					match3.add(winner1);
//					match3.add(winner2);
//					Game.setPlayers(match3);
//					activeCodes.add(code + "-3");
//					switchToScene("Game.fxml", startGameBtn);
				} else {
					alert3.close();
				}
				break;
			case 4:
				// Torneo
				Alert alert4 = new Alert(Alert.AlertType.CONFIRMATION);
				alert4.setTitle("Torneo");
				alert4.setHeaderText("Sei sicuro di voler iniziare un torneo?");
				alert4.setContentText("Confermando, comincerà il torneo con i quattro giocatori inseriti");
				alert4.showAndWait();
				if (alert4.getResult().getText().equals("OK")) {
					// TODO: Start Torneo
					Game.isTournament = true;
					Random rand = new Random();
					ArrayList<Player> match1 = new ArrayList<>();
					ArrayList<Player> match2 = new ArrayList<>();
					ArrayList<AtomicReference<Player>> match3 = new ArrayList<>();
					AtomicInteger p1, p2;
					AtomicReference<Player> winner1 = new AtomicReference<>();
					AtomicReference<Player> winner2 = new AtomicReference<>();
					AtomicReference<Player> winner3 = new AtomicReference<>();

					p1 = new AtomicInteger(rand.nextInt(players.size()));
					match1.add(players.remove(p1.get()));
					p2 = new AtomicInteger(rand.nextInt(players.size()));
					match1.add(players.remove(p2.get()));

					Task<Player> match1Task = new Task<>() {
						@Override
						protected Player call() throws Exception {
							System.out.println("Entered Thread 1");
							Game.setPlayers(match1);
							code += "-1";
							activeCodes.add(code);
							switchToScene("Game.fxml", startGameBtn);
							winner1.set(Game.selectGameWinner());
							System.out.println(winner1.get().getName() + " ttttttt");
							return winner1.get();
						}
					};

					System.out.println("Exited Thread 1");

					match1Task.setOnSucceeded(e -> {
						System.out.println("Entered Thread 2");
						p1.set(rand.nextInt(players.size()));
						match2.add(players.remove(p1.get()));
						p2.set(rand.nextInt(players.size()));
						match2.add(players.remove(p2.get()));
						Game.setPlayers(match2);
						code += "-2";
						activeCodes.add(code);
						try {
							switchToScene("Game.fxml", startGameBtn);
						} catch (IOException ex) {
							throw new RuntimeException(ex);
						}
						winner2.set(Game.selectGameWinner());
						System.out.println(winner2.get().getName());
					});

					new Thread(match1Task).start();

//					match3.add(winner1);
//					match3.add(winner2);
//					Game.setPlayersFinalTournamentGame(match3);
//					code += "-3";
//					activeCodes.add(code);
//					switchToScene("Game.fxml", startGameBtn);
				} else {
					alert4.close();
				}
				break;
		}
	}
}