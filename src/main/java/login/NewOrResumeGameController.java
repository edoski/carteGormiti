package login;

import game.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class NewOrResumeGameController {
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
	private ProgressBar playerProgressBar;

	@FXML
	private Button removeRecentBtn;

	@FXML
	private Button startGameBtn;

	@FXML
	private Button goBackBtn;

	@FXML
	void goBackLoginScreen(ActionEvent event) {
		/*
		 * Riporta alla schermata di login precedente → "1NewGameOrExisting.fxml"
		 */
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
		 * Torneo: 4 o 8 giocatori
		 *      - Arrotondato a 4 se sono aggiunti 3 giocatori/robot
		 *      - Arrotondato a 8 se sono aggiunti 5-7 giocatori/robot
		 *
		 */
	}
}