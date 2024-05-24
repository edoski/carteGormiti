package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

	@FXML
	private Button loadGameBtn;

	@FXML
	private Button newGameBtn;

	@FXML
	private Button quitBtn;

	Parent root;
	Scene scene;
	Stage stage;

	@FXML
	void loadGame(ActionEvent event) throws IOException {
		AnchorPane pane = (AnchorPane) loadGameBtn.getScene().getRoot();
		pane.setDisable(true);
		openPopupWindow("LoadGame.fxml", pane);
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

	public void switchToScene(String fxmlFile, Button btn) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
		root = loader.load();
		scene = new Scene(root);
		stage = (Stage) btn.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	public void openPopupWindow(String fxmlFile, AnchorPane pane) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
		root = loader.load();
		scene = new Scene(root);
		stage = new Stage();
		stage.setScene(scene);
		stage.show();

		// Passo il riferimento all'AnchorPane del MainMenuController al LoadGameController
		LoadGameController controller = loader.getController();
		controller.mainMenu = pane;
	}
}