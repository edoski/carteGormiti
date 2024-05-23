package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class TournamentNextGameController {

	Parent root;
	Scene scene;
	Stage stage;

	@FXML
	private Label gameWinnerLabel;

	@FXML
	private Button homeBtn;

	@FXML
	private Button nextGameBtn;

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
		p1EndGameLabel.setText(p1.getName() + "\n" + p1.playerScore + " punti");
		p2EndGameLabel.setText(p2.getName() + "\n" + p2.playerScore + " punti");
	}

	public void setGameWinner(Player winner) {
		gameWinnerLabel.setText(winner.getName() + " ha vinto la partita!");
		gameWinnerLabel.setStyle("-fx-text-fill: lime");
	}

	@FXML
	void goHome(ActionEvent event) {

	}

	@FXML
	void goNextGame(ActionEvent event) {
		Game.isNewGame = true;
		try {
			switchToScene("game.fxml");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@FXML
	void quit(ActionEvent event) {

	}

}