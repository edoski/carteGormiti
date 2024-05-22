package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class RoundWinnerController {

	Parent root;
	Scene scene;
	Stage stage;

	static int roundNumber;

	@FXML
	private Button endRoundQuitBtn;

	@FXML
	private Button nextRoundBtn;

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

		// TODO: CONVERT PRINT STATEMENTS TO LABELS
//		p1RoundCardDetails.setText(p1.getChosenCard().getName() + " - " + p1.getChosenCard().getCardType());
//		p2RoundCardDetails.setText(p2.getChosenCard().getName() + " - " + p2.getChosenCard().getCardType());
//		System.out.println("Elem. Multiplier: " + mult);
//		System.out.println(playerCard.getElement().toString() + ", " + dmg);
//		System.out.println("Wild Card: " + roundWildCard.getName());
//		System.out.println("Enemy: " + opponentCard.getElement().toString());
//		System.out.println("Final Multiplier: " + mult);
//		System.out.println("Final Damage: " + Math.round((dmg * mult) * 10.0) / 10.0);
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

	// TODO: ISSUE CURRENTLY IS THAT THIS SWITCH IS LIKE A RESTART OF THE GAME, NOT A NEW ROUND
	// 	- THE PLAYERS SHOULD KEEP THEIR SCORES AND ALL THEIR RELATED DATA
	@FXML
	void playRound() {
		try {
			Game.startingNewGame = false;
			switchToScene("Game.fxml");
		} catch (IOException e) {
			e.printStackTrace();
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
			// TODO: Implement saveGame() method
//		Game.saveGame();
			switchToScene("MainMenu.fxml");
		} else {
			alert.close();
		}
	}
}