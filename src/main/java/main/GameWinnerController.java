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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

	static String gameCode;

	public void switchToScene(String fxmlFile) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
		root = loader.load();
		stage = (Stage) gameWinnerLabel.getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void setPlayersDisplay(Player p1, Player p2) {
		p1EndGameLabel.setText(p1.getName() + "\n" + p1.playerScore + " punti");
		p2EndGameLabel.setText(p2.getName() + "\n" + p2.playerScore + " punti");
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
		alert.setTitle("Conferma uscita");
		alert.setHeaderText("Sei sicuro di voler uscire?");
		alert.setContentText("L'applicazione verrà chiusa.");
		alert.showAndWait();
		if (alert.getResult().getText().equals("OK")) {
			System.exit(0);
		} else {
			alert.close();
		}
	}

	public static void logGame(Player p1, Player p2, Player winner) {
		gameCode = Game.code;
		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String dateFormatted = date.format(formatter);
		// TODO: GOES TO CURRENT WORKING DIRECTORY, CHECK IF IS SAME AS JAR FILE
		String currDir = System.getProperty("user.dir");
		File directory = new File(currDir, "archive");
		if (!directory.exists()) {
			directory.mkdirs(); // Create the directory if it doesn't exist
		}
		File logFile = new File(directory, gameCode + ".txt");
		try {
			FileWriter writer = new FileWriter(logFile);
			PrintWriter printer = new PrintWriter(writer);

			printer.println("CODE: " + gameCode);
			printer.println("DATE: " + dateFormatted);
			printer.println("PLAYER 1: " + p1.getName() + ", SCORE: " + p1.playerScore);
			printer.println("PLAYER 2: " + p2.getName() + ", SCORE: " + p2.playerScore);
			printer.println("WINNER: " + winner.getName());

			printer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}