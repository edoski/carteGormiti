package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class LoadGameController {

	@FXML
	private Button deleteGameBtn;

	@FXML
	private ChoiceBox<?> gamesChoiceBox;

	@FXML
	private Button goBackBtn;

	@FXML
	private Button loadGameBtn;

	@FXML
	void deleteGame(ActionEvent event) {
		/*
		 * deleteGameBtn
		 * Elimina la partita selezionata tra quelle disponibili in gamesChoiceBox, manda un alert di conferma prima di farlo
		 * Se non ci sono partite da eliminare, manda un error alert se si prova a cliccare il bottone
		 */
	}

	@FXML
	void goBackLoginScreen(ActionEvent event) {
		/*
		 * goBackBtn
		 * Riporta alla schermata di login precedente → "1NewGameOrExisting.fxml"
		 */
	}

	@FXML
	void loadGame(ActionEvent event) {
		/*
		 * loadGameBtn
		 * Carica la partita selezionata in gamesChoiceBox
		 * Se non ci sono partite esistenti, manda un error alert se si prova a cliccare il bottone
		 */
	}
}