package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class GameWinnerController {

	Parent root;
	Scene scene;
	Stage stage;

	@FXML
	private Label gameWinnerLabel;

	@FXML
	private Button homeBtn;

	@FXML
	private Label p1EndGameLabel;

	@FXML
	private Label p2EndGameLabel;

	@FXML
	private Button quitBtn;

	public void switchToScene(String fxmlFile) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
		root = loader.load();
		stage = (Stage) gameWinnerLabel.getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void setPlayers(Player p1, Player p2) {
		p1EndGameLabel.setText(p1.getName() + " " + p1.playerScore);
		p2EndGameLabel.setText(p2.getName() + " " + p2.playerScore);
	}

	public void setGameWinner(Player winner) {
		gameWinnerLabel.setText(winner.getName() + " ha vinto la partita!");
		gameWinnerLabel.setStyle("-fx-text-fill: lime");
	}

	@FXML
	void goHome(ActionEvent event) {
		try {
			switchToScene("MainMenu.fxml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void quit(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirma uscita");
		alert.setHeaderText("Sei sicuro di voler uscire?");
		alert.setContentText("L'applicazione verrà chiusa.");
		alert.showAndWait();
		if (alert.getResult().getText().equals("OK")) {
			System.exit(0);
		} else {
			alert.close();
		}
	}

	public static void logGame(Player p1, Player p2, Player winner, String code) {
		// TODO: LOG GAME CODE, PLAYER NAMES, PLAYER SCORES, WINNER TO FILE, AND CURRENT DATE

		/* EXAMPLE OF LOG FILE:
		 *
		 * -------------------------
		 * CODE: XY45
		 * DATE: 2024-05-22
		 * PLAYER 1: John, SCORE: 5
		 * PLAYER 2: Jane, SCORE: 3
		 * WINNER: John
		 * -------------------------
		 *
		 */
	}
}