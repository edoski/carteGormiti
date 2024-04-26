package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class Main {

	@FXML
	private ImageView boardGame;

	@FXML
	private Button createGameBtn;

	@FXML
	private Button loadGameBtn;

	@FXML
	private ListView<?> savedGamesListView;

	@FXML
	private ListView<?> scoreboardListView;

	@FXML
	private Font x1;

	Parent root;
	Scene scene;
	Stage stage;

	@FXML
	void createGame(ActionEvent event) throws IOException {
		switchToScene("CreateGame.fxml", createGameBtn);
	}

	@FXML
	void loadGame(ActionEvent event) throws IOException {
		/*
		 * Carica una delle partite esistenti in base alla list view
		 */
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