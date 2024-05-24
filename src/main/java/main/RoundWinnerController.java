package main;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RoundWinnerController implements Initializable {

	Parent root;
	Scene scene;
	Stage stage;

	static int roundNumber;

	@FXML
	private ImageView p1EndRoundCard;

	@FXML
	private Label p1RoundDmg;

	@FXML
	private Label p1RoundLabel;

	@FXML
	private Label p1RoundCardDetails;

	@FXML
	private ImageView p2EndRoundCard;

	@FXML
	private Label p2RoundDmg;

	@FXML
	private Label p2RoundLabel;

	@FXML
	private Label p2RoundCardDetails;

	@FXML
	private Label roundWinnerLabel;

	@FXML
	private Button nextRoundBtn;

	static double p1ElemMult;
	static double p2ElemMult;
	static double p1WildMult;
	static double p2WildMult;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GameSave.saveGame();
		Game.activateCPU = false;
		if (Game.player1.isCPU && Game.player2.isCPU) {
			// PAUSE 1S THEN HIT NEXT ROUND BUTTON
			PauseTransition pause = new PauseTransition(Duration.seconds(1));
			pause.setOnFinished(event -> nextRoundBtn.fire());
			pause.play();
		}
	}

	public void switchToScene(String fxmlFile) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
		root = loader.load();
		stage = (Stage) roundWinnerLabel.getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void setPlayers(Player p1, Player p2) {
		p1RoundLabel.setText(p1.getName());
		p2RoundLabel.setText(p2.getName());
		p1EndRoundCard.setImage(p1.getChosenCard().getArt());
		p2EndRoundCard.setImage(p2.getChosenCard().getArt());
		p1RoundDmg.setText("Attack: " + Game.p1FinalDmg);
		p2RoundDmg.setText("Attack: " + Game.p2FinalDmg);

		double p1Mult = Math.round(p1ElemMult * p1WildMult * 100.0) / 100.0;
		String p1CardDetails =
				p1.getChosenCard().getName() + ":\n" +
						"- Elem. Multiplier: " + p1ElemMult + "x\n" +
						"- Wild  Multiplier: " + p1WildMult + "x\n" +
						"- Final Multiplier: " + p1Mult + "x\n\n" +
						p1.getChosenCard().getDamage() + " * " + p1Mult + " = " + Game.p1FinalDmg;
		p1RoundCardDetails.setText(p1CardDetails);

		double p2Mult = Math.round(p2ElemMult * p2WildMult * 100.0) / 100.0;
		String p2CardDetails =
				p2.getChosenCard().getName() + ":\n" +
						"- Elem. Multiplier: " + p2ElemMult + "x\n" +
						"- Wild  Multiplier: " + p2WildMult + "x\n" +
						"- Final Multiplier: " + p2Mult + "x\n\n" +
						p2.getChosenCard().getDamage() + " * " + p2Mult + " = " + Game.p2FinalDmg;
		p2RoundCardDetails.setText(p2CardDetails);
	}

	public static int getRoundNumber() {
		return roundNumber;
	}

	public void setRoundNumber(int roundNumber) {
		RoundWinnerController.roundNumber = roundNumber;
	}

	public void setRoundWinner(Player winner) {
		if (winner == null) {
			roundWinnerLabel.setText("Pareggio!");
		} else {
			roundWinnerLabel.setText(winner.getName() + " vince!");
			if (winner == Game.player1) {
				p1RoundLabel.setStyle("-fx-text-fill: lime");
				p2RoundLabel.setStyle("-fx-text-fill: red");
			} else {
				p1RoundLabel.setStyle("-fx-text-fill: red");
				p2RoundLabel.setStyle("-fx-text-fill: lime");
			}
		}
	}

	@FXML
	void playRound() {
		Game.activateCPU = true;
		Game.isNewGame = false;
		try {
			switchToScene("Game.fxml");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@FXML
	void quitAndSaveGame() throws IOException {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Conferma uscita");
		alert.setHeaderText("Sei sicuro di voler uscire?");
		alert.setContentText("Il gioco verrà salvato e chiuso.");
		alert.showAndWait();
		if (alert.getResult().getText().equals("OK")) {
			// MAYBE UNNECESSARY, BUT JUST TO BE SURE
			GameSave.saveGame();
			switchToScene("MainMenu.fxml");
		} else {
			alert.close();
		}
	}
}