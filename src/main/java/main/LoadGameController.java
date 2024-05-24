package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

public class LoadGameController implements Initializable {
	Parent root;
	Scene scene;
	Stage stage;
	AnchorPane mainMenu;
	static String code;

	@FXML
	private Button backBtn;

	@FXML
	private Button confirmBtn;

	@FXML
	private TextField gameCodeInputField;

	static HashSet<String> activeCodes = new HashSet<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
	}

	public void switchToScene(String fxmlFile) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
		root = loader.load();
		stage = (Stage) confirmBtn.getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void back(ActionEvent event) {
		stage = (Stage) backBtn.getScene().getWindow();
		stage.close();
		mainMenu.setDisable(false);
	}

	/*
	 * IF GAME CODE IS VALID → LOAD GAME FROM JSON
	 * SWITCH TO GAME SCENE
	 * ELSE
	 * ERROR MESSAGE + COLOR RED ON INPUT FIELD
	 */
	@FXML
	void startGame(ActionEvent event) {
		code = gameCodeInputField.getText();
		if (activeCodes.contains(code)) {
			Game.isLoadGame = true;
			try {
				stage = (Stage) backBtn.getScene().getWindow();
				stage.close();
				switchToScene("Game.fxml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			gameCodeInputField.setStyle("-fx-text-inner-color: red");
			gameCodeInputField.setPromptText("Codice non valido");
		}
	}
}