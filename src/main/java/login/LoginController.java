package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
	Parent root;
	Stage stage;
	Scene scene;
	@FXML
	private Button newGameBtn;
	@FXML
	private Button resumeGameBtn;

	public void switchToScene(String fxmlFile, Button btn) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
		root = loader.load();
		scene = new Scene(root);
		stage = (Stage) btn.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void newGame(ActionEvent event) throws IOException {
		/*
		 * Cliccando questo bottone, newGameBtn, si passa alla scena "2InitializePlayers.fxml"
		 */
		switchToScene("2InitializePlayers.fxml", newGameBtn);
	}

	@FXML
	void resumeGame(ActionEvent event) throws IOException {
		/*
		 * Cliccando questo bottone, resumeGameBtn, si passa alla scena "2LoadGame.fxml"
		 */
		switchToScene("2LoadGame.fxml", resumeGameBtn);
	}
}