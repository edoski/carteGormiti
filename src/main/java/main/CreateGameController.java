package main;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.ResourceBundle;
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
	@FXML
	private Button addPlayerBtn;

	Parent root;
	Scene scene;
	Stage stage;

	static ArrayList<Player> match1 = new ArrayList<>();
	static ArrayList<Player> match2 = new ArrayList<>();
	static ArrayList<AtomicReference<Player>> match3 = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		EventHandler<KeyEvent> enterKey = keyEvent -> {
			if (keyEvent.getCode().equals(KeyCode.ENTER)) {
				addPlayerBtn.fire();
			}
		};
		playerNameTextField.setOnKeyPressed(enterKey);

		players = new ArrayList<>();

		// FOR STYLE CONSISTENCY IF USER LEAVES CREATE GAME SCREEN AND RETURNS
		createGameTitleLabel.setStyle("-fx-font-weight: bold");
		codeLabel.setStyle("-fx-font-weight: bold");
		player1.setStyle("-fx-font-weight: bold");
		player2.setStyle("-fx-font-weight: bold");
		player3.setStyle("-fx-font-weight: bold");
		player4.setStyle("-fx-font-weight: bold");

		// GET CODES FROM ACTIVE GAMES DIR AND LOAD THEM ONTO THE HASHSET
		File dir = new File(System.getProperty("user.dir") + "/games");
		if (dir.exists()) {
			File[] files = dir.listFiles();
			assert files != null;
			for (File file : files) {
				activeCodes.add(file.getName().substring(0, 4));
			}
		} else {
			dir.mkdir();
		}
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

//	public synchronized void switchToScene(String fxmlFile, Button btn) throws IOException {
//		Platform.runLater(() -> {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
//			Parent root;
//			try {
//				root = loader.load();
//			} catch (IOException e) {
//				throw new RuntimeException(e);
//			}
//			Scene scene = new Scene(root);
//			Stage stage = (Stage) btn.getScene().getWindow();
//			stage.setScene(scene);
//			stage.show();
//		});
//	}

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
	 *
	 * FOR TOURNAMENT PIPELINE TO BE CORRECT PLAYERS.SIZE() MUST BE 2 AT ANY GIVEN TIME
	 * WHERE 2 IS THE TWO PLAYERS IN THE CURRENT MATCH
	 */
	@FXML
	void startGame() throws IOException {
		Game.isNewGame = true;
		switch (players.size()) {
			// Non ci sono giocatori
			case 0:
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Warning");
				alert.setHeaderText("Non ci sono giocatori");
				alert.setContentText("Aggiungi almeno un giocatore per iniziare la partita");
				alert.showAndWait();
				break;
			// Partita singola
			case 1:
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
			// Partita singola
			case 2:
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
			// Torneo
			case 3:
				Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION);
				alert3.setTitle("Torneo");
				alert3.setHeaderText("Sei sicuro di voler iniziare un torneo?");
				alert3.setContentText("Confermando, verrà aggiunto un giocatore CPU per completare il torneo");
				alert3.showAndWait();
				if (alert3.getResult().getText().equals("OK")) {
					Player player4 = new Player("CPU 0" + ++CPUCount);
					player4.isCPU = true;
					players.add(player4);

					Game.isTournament = true;
					Random rand = new Random();
					int p1, p2;
					match1 = new ArrayList<>();
					match2 = new ArrayList<>();
					match3 = new ArrayList<>();

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
			// Torneo
			case 4:
				Alert alert4 = new Alert(Alert.AlertType.CONFIRMATION);
				alert4.setTitle("Torneo");
				alert4.setHeaderText("Sei sicuro di voler iniziare un torneo?");
				alert4.setContentText("Confermando, comincerà il torneo con i quattro giocatori inseriti");
				alert4.showAndWait();

				if (alert4.getResult().getText().equals("OK")) {
//					Game.isTournament = true;

					// MATCH 2
//					TournamentWinnerController.match2Player1 = match2.getFirst();
//					TournamentWinnerController.match2Player2 = match2.getLast();

					/*TODO
					 * APPROACH: EXECUTOR SERVICE WITH COUNTDOWN LATCHES
					 */
//
//					ExecutorService executor = Executors.newSingleThreadExecutor();
//					CountDownLatch latch1 = new CountDownLatch(1);
//					CountDownLatch latch2 = new CountDownLatch(1);
//
//					// MATCH 1
//					Runnable match1Task = () -> {
//						System.out.println("Entered Thread 1");
//						Game.isNewGame = true;
//						Game.isTournament = true;
//						match1.add(players.removeFirst());
//						match1.add(players.removeFirst());
//						match2.add(players.getFirst());
//						match2.add(players.getFirst());
//						Game.setPlayers(match1);
//						code += "-1";
//						activeCodes.add(code);
//						Platform.runLater(() -> {
//							try {
//								switchToScene("Game.fxml", startGameBtn);
//							} catch (IOException ex) {
//								throw new RuntimeException(ex);
//							}
//						});
//						TournamentWinnerController.game1Winner = Game.selectGameWinner();
//						System.out.println(TournamentWinnerController.game1Winner.getName());
//						latch1.countDown();
//					};
//
//					executor.submit(match1Task);
//
//					Runnable match2Task = () -> {
//						try {
//							latch1.await();
//							System.out.println("Entered Thread 2");
//							Game.isTournament = true;
//							Game.isNewGame = true;
//							Game.setPlayers(match2);
//							code += "-2";
//							activeCodes.add(code);
//							Platform.runLater(() -> {
//								try {
//									switchToScene("Game.fxml", startGameBtn);
//								} catch (IOException ex) {
//									throw new RuntimeException(ex);
//								}
//							});
//							TournamentWinnerController.game2Winner = Game.selectGameWinner();
//							System.out.println(TournamentWinnerController.game2Winner.getName());
//							latch2.countDown();
//						} catch (InterruptedException e) {
//							Thread.currentThread().interrupt();
//						}
//					};
//
//					executor.submit(match2Task);
//
//					Runnable match3Task = () -> {
//						try {
//							latch2.await();
//							System.out.println("Entered Thread 3");
//							match3.add(new AtomicReference<>(TournamentWinnerController.game1Winner));
//							match3.add(new AtomicReference<>(TournamentWinnerController.game2Winner));
//							Game.setPlayersFinalTournamentGame(match3);
//							code += "-3";
//							activeCodes.add(code);
//							Platform.runLater(() -> {
//								try {
//									switchToScene("Game.fxml", startGameBtn);
//								} catch (IOException ex) {
//									throw new RuntimeException(ex);
//								}
//							});
//						} catch (InterruptedException e) {
//							Thread.currentThread().interrupt();
//						}
//					};
//
//					executor.submit(match3Task);
//					executor.shutdown();

					/*TODO
					 *  APPROACH: THREADS WITH JOIN
					 */

					// MATCH 1
//					Thread match1Thread = new Thread(() -> {
//						System.out.println("Entered Thread 1");
//						Game.isNewGame = true;
//						Game.isTournament = true;
//						match1.add(players.removeFirst());
//						match1.add(players.removeFirst());
////						match2.add(players.getFirst());
////						match2.add(players.getFirst());
//						Game.setPlayers(match1);
//						code += "-1";
//						activeCodes.add(code);
//						Platform.runLater(() -> {
//							try {
//								switchToScene("Game.fxml", startGameBtn);
//							} catch (IOException ex) {
//								throw new RuntimeException(ex);
//							}
////							Platform.runLater(() -> {
////								System.out.println(TournamentWinnerController.game1Winner.getName());
////								TournamentWinnerController.game1Winner = Game.selectGameWinner();
////								match2.add(players.removeFirst());
////								match2.add(players.removeFirst());
////								Game.setPlayers(match2);
////							});
//						});
//					});

//					match1Thread.start();

					// MATCH 2
//					Thread match2Thread = new Thread(() -> {
//						System.out.println("Entered Thread 2");
//						try {
//							match1Thread.join();
//						} catch (InterruptedException e) {
//							throw new RuntimeException(e);
//						}
//						Game.isTournament = true;
//						Game.isNewGame = true;
//						match2.add(players.removeFirst());
//						match2.add(players.removeFirst());
////						Game.setPlayers(match2);
//						code = code.substring(0, code.length() - 1) + "2";
//						activeCodes.add(code);
//						Platform.runLater(() -> {
//							try {
//								switchToScene("Game.fxml", startGameBtn);
//							} catch (IOException ex) {
//								throw new RuntimeException(ex);
//							}
//						});
//						TournamentWinnerController.game2Winner = Game.selectGameWinner();
//						System.out.println(TournamentWinnerController.game2Winner.getName());
//					});

					// MATCH 3
//					Thread match3Thread = new Thread(() -> {
//						System.out.println("Entered Thread 3");
//						match3.add(new AtomicReference<>(TournamentWinnerController.game1Winner));
//						match3.add(new AtomicReference<>(TournamentWinnerController.game2Winner));
//						Game.setPlayersFinalTournamentGame(match3);
//						code = code.substring(0, code.length() - 1) + "3";
//						activeCodes.add(code);
//						Platform.runLater(() -> {
//							try {
//								switchToScene("Game.fxml", startGameBtn);
//							} catch (IOException ex) {
//								throw new RuntimeException(ex);
//							}
//						});
//					});

//					try {
//						match1Thread.start();
//						match1Thread.join();
//						match2Thread.start();
//						match2Thread.join();
//						match3Thread.start();
//						match3Thread.join();
//					} catch (InterruptedException e) {
//						Thread.currentThread().interrupt();
//					}

					TournamentNextGameController tournamentController = new TournamentNextGameController();
					tournamentController.startTournament();

				} else {
					alert4.close();
				}
				break;
		}
	}
}