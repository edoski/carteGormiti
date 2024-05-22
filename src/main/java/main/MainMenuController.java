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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/*TODO
 * - IF THE LISTVIEWS ARE TOO MUCH OF A HASSLE, SCRAP THEM AND ENLARGE AND MOVE LOAD GAME AND NEW GAME BUTTONS TO THE CENTER OF THE SCREEN
 */

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