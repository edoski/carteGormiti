package main;

import game.Player;
import javafx.event.ActionEvent;
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
import java.util.HashSet;
import java.util.Random;
import java.util.ResourceBundle;

public class CreateGameController implements Initializable {
	@FXML
	private Button addRobotBtn;

	@FXML
	private Label codeLabel;
	// HashSet per memorizzare i codici generati
	private final HashSet<String> activeCodes = new HashSet<>();

	@FXML
	private Button addPlayerBtn;
	@FXML
	private Label player1, player2, player3, player4;
	private Player[] players;
	private int plyrIndex = 0;

	@FXML
	private TextField playerNameTextField;

	@FXML
	private Button removeRecentBtn;

	@FXML
	private Button startGameBtn;

	@FXML
	private Button goBackBtn;

	Parent root;
	Scene scene;
	Stage stage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		players = new Player[4];

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
	void goBackLoginScreen(ActionEvent event) throws IOException {
		/*
		 * Riporta alla schermata principale
		 */
		switchToScene("MainMenu.fxml", goBackBtn);
	}

	@FXML
	void addRobot(ActionEvent event) {
		/*
		 * addRobotBtn, aggiunge un robot alla lista di giocatori,
		 * il nome del robot viene automaticamente inizializzato come "CPU 1" o "CPU 2" etc.
		 *
		 * Se l'inserimento del robot corrisponde al terzo giocatore, viene lanciato un alert (vedi commenti su addHuman() al riguardo)
		 */

		Player player = new Player("CPU " + (plyrIndex + 1));
		players[plyrIndex++] = player;
	}

	@FXML
	void addHuman(ActionEvent event) {
		/*
		 * addPlayerBtn, aggiunge un umano alla partita, il quale nome deriva dalla stringa immessa nel text field playerNameTextField.
		 *
		 * All'inserimento del terzo giocatore, viene lanciato un alert per avvertire il fatto che si entra in un torneo aggiungendolo,
		 * e dando l'opzione o di confermare il terzo giocatore, o di annullare il suo inserimento
		 */

		// Get the text from the text field
		String playerName = playerNameTextField.getText();

		// Check if the text field is empty
		if (playerName.isEmpty()) {
			playerNameTextField.setStyle("-fx-border-color: red");
			playerNameTextField.setStyle("-fx-prompt-text-fill: red");
			playerNameTextField.setPromptText("Inserisci un nome valido");
		} else {
			if (plyrIndex < 4) {
				playerNameTextField.setStyle("-fx-border-color: none");
				playerNameTextField.setStyle("-fx-prompt-text-fill: grey");
				playerNameTextField.setPromptText("Player Name");

				switch (plyrIndex) {
					case 0:
						player1.setText("Player 1: " + playerName);
						break;
					case 1:
						player2.setText("Player 2: " + playerName);
						break;
					case 2:
						player3.setText("Player 3: " + playerName);
						break;
					case 3:
						player4.setText("Player 4: " + playerName);
						break;
				}

				Player player = new Player(playerName);
				players[plyrIndex++] = player;
			} else {
				// Non puoi aggiungere più di 4 giocatori
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText("Non puoi aggiungere più di 4 giocatori");
				alert.showAndWait();
			}
		}
	}

	public void generateCode() {
		/*
		 * Admin genera una stringa che viene associata alla partita appena creata,
		 * attraverso questo codice si può riprendere una partita.
		 *
		 * Il codice viene generato appena inserito il primo giocatore.
		 */

		Random rand = new Random();
		String code = "";
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
			activeCodes.add(code);
			codeLabel.setText("Code: " + code);
		}
	}

	@FXML
	void removeRecent(ActionEvent event) {
		/*
		 * removeRecentBtn, rimuove l'inserimento del giocatore (robot o umano) più recente dalla lista
		 */

		if (plyrIndex > 0) {
			plyrIndex--;
			switch (plyrIndex) {
				case 0:
					player1.setText("Player 1: ");
					break;
				case 1:
					player2.setText("Player 2: ");
					break;
				case 2:
					player3.setText("Player 3: ");
					break;
				case 3:
					player4.setText("Player 4: ");
					break;
			}
			players[plyrIndex] = null;
		}
	}

	@FXML
	void startGame(ActionEvent event) {
		/*
		 * startGameBtn, lancia la partita, o torneo, in base al numero di giocatori aggiunti.
		 *
		 * Partita singola: 2 giocatori
		 * Torneo: 4 giocatori
		 *      - Arrotondato a 4 se sono aggiunti 3 giocatori/robot
		 *
		 */
	}
}