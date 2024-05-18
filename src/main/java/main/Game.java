package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Random;

public class Game {

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
	Player player1, player2;
	boolean player1Turn = true;
	int roundNumber = 1;

	public void setPlayers(Player player1, Player player2) {
		players = new ArrayList<>();
		this.players.add(player1);
		this.players.add(player2);
	}

	/*
	 * Questo metodo inizia la partita, creando un mazzo di carte e distribuendole ai giocatori.
	 */
	public void startGame() {
		// Create a deck
		Deck deck = new Deck();

		// Distribute cards to players
		Card[] player1Hand = deck.createPlayerDeck();
		Card[] player2Hand = deck.createPlayerDeck();

		// Set player hands
		player1.setHand(player1Hand);
		player2.setHand(player2Hand);

		// Display round number
		roundLabel.setText("Round: " + roundNumber + " of 3");

		// Start game
		player1Turn = true;
		playRound();
	}

	/*
	 * Questo metodo gioca un round della partita, con due turni, uno per ciascun giocatore.
	 * Il metodo seleziona una carta per ciascun giocatore, determina il vincitore, aggiorna i punteggi e il numero del round.
	 */
	public void playRound() {
		if (player1Turn) {
			// Player 1 selects a card
			Card player1Card = player1.selectCard();
			// Player 2 selects a card
			Card player2Card = player2.selectCard();
			player1Turn = false;
		} else {
			// Player 2 selects a card
			Card player2Card = player2.selectCard();
			// Player 1 selects a card
			Card player1Card = player1.selectCard();
			player1Turn = true;
		}

		// Determine winner
		Player winner = selectRoundWinner(player1Card, player2Card);
		// Display winner
		displayWinner(winner);
		// Update player scores
		winner.playerScore++;
		// Update round number
		roundNumber++;

		// Start next round, if round 3 is not reached
		if (roundNumber < 3) {
			playRound();
		} else {
			// End game
			selectGameWinner();
			displayLeaderboard();
		}
	}

	/*
	 * Questo metodo permette al giocatore di selezionare una carta dal proprio mazzo
	 */
	Card selectCard() {
		return null;
	}

	/*
	 * Questo metodo determina il vincitore del round, in base alle carte selezionate dai giocatori
	 */
	Player selectRoundWinner(Card player1Card, Card player2Card) {
		double p1Dmg = finalCardDamage(player1Card, player2Card, player1.getWildCard());
		double p2Dmg = finalCardDamage(player2Card, player1Card, player2.getWildCard());

		if (p1Dmg == p2Dmg) {
			return roundDraw();
		} else {
			return p1Dmg > p2Dmg ? player1 : player2;
		}
	}

	/*
	 * Questo metodo calcola il danno finale di una carta, in base al danno base e ad eventuali modificatori:
	 * - Indebolimento: 0.8x danno
	 * - Potenziamento: 1.2x danno
	 *
	 * - Fuoco attacca Terra: 1.2x danno
	 * - Terra attacca Acqua: 1.2x danno
	 * - Acqua attacca Fuoco: 1.2x danno
	 */
	double finalCardDamage(Card attackerCard, Card defenderCard, Card.Wild wildCard) {
		double dmg = attackerCard.getDamage();
		double mult = 1.0;

		if (attackerCard.getElement() == Card.Element.FIRE && defenderCard.getElement() == Card.Element.FOREST) {
			mult = 1.2;
		} else if (attackerCard.getElement() == Card.Element.FOREST && defenderCard.getElement() == Card.Element.WATER) {
			mult = 1.2;
		} else if (attackerCard.getElement() == Card.Element.WATER && defenderCard.getElement() == Card.Element.FIRE) {
			mult = 1.2;
		}

		if (wildCard == Card.Wild.INDEBOLIMENTO) {
			mult *= 0.8;
		} else if (wildCard == Card.Wild.POTENZIAMENTO) {
			mult *= 1.2;
		}

		return dmg * mult;
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

	/*
	 * Seleziona il vincitore a fine partita, in base al numero massimo di punti conseguiti
	 */
	public void selectGameWinner() {
	}

	/*
	 * Parte a termine partita, una volta determinato il vincitore [selectVictor()]
	 * Fa un switchToScene() alla schermata di fine partita
	 */
	public void displayLeaderboard() {
	}

	/*
	 * Viene chiamato all'inizio di ogni round (2 turni), si applica una delle carte Wild (Indebolimento, Potenziamento)
	 */
	public void selectWildCard() {
		// Randomly select a wild card
		Random rand = new Random();
		int wildIndex = rand.nextInt(2);
		Card.Wild wildCard = wildIndex == 0 ? Card.Wild.INDEBOLIMENTO : Card.Wild.POTENZIAMENTO;

	}
}