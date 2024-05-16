
package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Game implements Initializable {

	@FXML
	private ImageView enemyCardChoiceImage;

	@FXML
	private MenuBar menuBar;

	@FXML
	private ImageView playerCard1, playerCard2, playerCard3, playerCard4, playerCard5, playerCard6, playerCard7, playerCard8, playerCard9;

	@FXML
	private ImageView playerCardChoiceImage;

	@FXML
	private Label playerLabel;

	@FXML
	private MenuItem quitAndSaveMB;

	@FXML
	private MenuItem restartMB;

	@FXML
	private Label roundLabel;

	@FXML
	private MenuItem saveMB;

	@FXML
	private ImageView wildCardImage;

	@FXML
	private Label wildEffectsLabel;

	private ArrayList<Player> players;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Select wild card
	}

	public void setPlayers(Player player1, Player player2) {
		this.players.add(player1);
		this.players.add(player2);
	}

	@FXML
	void quitAndSaveGame(ActionEvent event) {

	}

	@FXML
	void restartGame(ActionEvent event) {

	}

	@FXML
	void saveGame(ActionEvent event) {

	}

	public void selectVictor() {
		/*
		 * Seleziona il vincitore a fine partita, in base al numero massimo di punti conseguiti
		 */
	}

	public void displayLeaderboard() {
		/*
		 * Parte a termine partita, una volta determinato il vincitore [selectVictor()]
		 */
	}

	public void selectWildCard() {
		/*
		 * Viene chiamato all'inizio di ogni round (2 turni), si applica una delle carte Wild (Indebolimento, Potenziamento, Vincita/Perdita)
		 */
	}
}