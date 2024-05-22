package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoadGameController {
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

	@FXML
	void back(ActionEvent event) {
		stage = (Stage) backBtn.getScene().getWindow();
		stage.close();
		mainMenu.setDisable(false);
	}

	@FXML
	void startGame(ActionEvent event) {
		/*
		 * IF GAME CODE IS VALID → LOAD GAME FROM JSON
		 * SWITCH TO GAME SCENE
		 * ELSE
		 * ERROR MESSAGE + COLOR RED ON INPUT FIELD
		 */
	}
}