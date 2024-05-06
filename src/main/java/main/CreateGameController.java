package main;

import game.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

public class CreateGameController {
	@FXML
	private Button addRobotBtn;

	@FXML
	private Label codeLabel;

	@FXML
	private Button addPlayerBtn;

	@FXML
	private ListView<String> playerListView;
	private Player[] players;

	@FXML
	private TextField playerNameTextField;

	@FXML
	private Label gameTypeLabel;

	@FXML
	private Button removeRecentBtn;

	@FXML
	private Button startGameBtn;

	@FXML
	private Button goBackBtn;

	Parent root;
	Scene scene;
	Stage stage;

	//	Per tenere traccia dei codici attivi di partite e tornei
	static private final HashSet<String> activeCodes = new HashSet<>();

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
		switchToScene("Main.fxml", goBackBtn);
	}

	@FXML
	void addRobot(ActionEvent event) {
		/*
		 * addRobotBtn, aggiunge un robot alla lista di giocatori,
		 * il nome del robot viene automaticamente inizializzato come "CPU 1" o "CPU 2" etc.
		 *
		 * Se l'inserimento del robot corrisponde al terzo giocatore, viene lanciato un alert (vedi commenti su addHuman() al riguardo)
		 */
	}

	@FXML
	void addHuman(ActionEvent event) {
		/*
		 * addPlayerBtn, aggiunge un umano alla partita, il quale nome deriva dalla stringa immessa nel text field playerNameTextField.
		 *
		 * All'inserimento del terzo giocatore, viene lanciato un alert per avvertire il fatto che si entra in un torneo aggiungendolo,
		 * e dando l'opzione o di confermare il terzo giocatore, o di annullare il suo inserimento
		 */
	}

	public String generateCode() {
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
				code += rand.nextInt(26) + 65;
			} else {
//				Generiamo due interi casuali in [0,9] per la seconda parte del codice
				code += rand.nextInt(9);
			}
		}
		return code;
	}

	@FXML
	void removeRecent(ActionEvent event) {
		/*
		 * removeRecentBtn, rimuove l'inserimento del giocatore (robot o umano) più recente dalla lista
		 */
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