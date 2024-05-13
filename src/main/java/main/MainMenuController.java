package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

	@FXML
	private Button loadGameBtn;

	@FXML
	private Button newGameBtn;

	@FXML
	private Button quitBtn;

	@FXML
	private ListView<?> singleGameList;

	@FXML
	private ListView<?> tournamentList;

	Parent root;
	Scene scene;
	Stage stage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// LOAD CURRENT GAMES FROM JSON
	}

	@FXML
	void loadGame(ActionEvent event) {
		// POPUP WINDOW WITH TEXT INPUT FIELD ASKING FOR GAME CODE

		// IF GAME CODE IS VALID
		// LOAD GAME FROM JSON
		// SWITCH TO GAME SCENE
		// ELSE
		// POPUP WINDOW WITH ERROR MESSAGE
	}

	@FXML
	void newGame(ActionEvent event) throws IOException {
		switchToScene("CreateGame.fxml", newGameBtn);
	}

	@FXML
	void quit(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Exit");
		alert.setHeaderText("Sei sicuro di voler uscire?");
		alert.showAndWait();
		if (alert.getResult().getText().equals("OK")) {
			System.exit(0);
		}
	}

	@FXML
	void createGame(ActionEvent event) throws IOException {
		switchToScene("CreateGame.fxml", newGameBtn);
	}

	public void switchToScene(String fxmlFile, Button btn) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
		root = loader.load();
		scene = new Scene(root);
		stage = (Stage) btn.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
}