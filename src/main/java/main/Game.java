package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
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

	static Deck deck = new Deck();
	static Player player1;
	static Player player2;
	static boolean player1Turn = true;
	static int roundNumber = 1;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		startGame();
	}

	/*
	 * Questo metodo imposta i giocatori per la partita
	 */
	public static void setPlayers(Player[] players) {
		player1 = players[0];
		player2 = players[1];
	}

	/*
	 * Questo metodo inizia la partita, creando un mazzo di carte e distribuendole ai giocatori.
	 */
	public void startGame() {
		// Distribute cards to players
		Card[] player1Hand = deck.createPlayerDeck();
		Card[] player2Hand = deck.createPlayerDeck();

		// Set player hands
		player1.setHand(player1Hand);
		player2.setHand(player2Hand);

		// Display round number
		roundLabel.setText("Round: " + roundNumber + " of 3");

		// Start game from player 1
		player1Turn = true;
		playRound();
	}

	/*
	 * Questo metodo gioca un round della partita, con due turni, uno per ciascun giocatore.
	 * Il metodo seleziona una carta per ciascun giocatore, determina il vincitore, aggiorna i punteggi e il numero del round.
	 */
	public void playRound() {
		if (player1Turn) {
			playerLabel.setText("Scegli una carta, " + player1.getName());
			// Display player 1 card
			displayPlayerCards(player1);
			// Player 1 selects a card
//			Card player1Card = player1.selectCard();

			playerLabel.setText("Scegli una carta, " + player2.getName());
			// Display player 2 cards
//			displayPlayerCards(player2);
			// Player 2 selects a card
//			Card player2Card = player2.selectCard();
			// Flip turn for next round
			player1Turn = false;
		} else {
			playerLabel.setText("Scegli una carta, " + player2.getName());
			// Display player 2 cards
//			displayPlayerCards(player2);
			// Player 2 selects a card
//			Card player2Card = player2.selectCard();

			playerLabel.setText("Scegli una carta, " + player1.getName());
			// Display player 1 card
//			displayPlayerCards(player1);
			// Player 1 selects a card
//			Card player1Card = player1.selectCard();
			// Flip turn for next round
			player1Turn = true;
		}

		// Determine winner
//		Player winner = selectRoundWinner(player1Card, player2Card);
		// Display winner
//		displayWinner(winner);
		// Update player scores
		// TODO: DOES THIS UPDATE THE PLAYER1 OR PLAYER2 SCORE? OR JUST THE WINNER OBJECT MADE HERE?
//		winner.playerScore++;
		// Update round number
		roundNumber++;

		// TODO: MAYBE CONSIDER MORE ROUNDS, JUST NEED TO CHANGE THE BELOW IF NUMBER & ROUND LABEL
		// Start next round, if round 3 is not reached
//		if (roundNumber < 3) {
//			playRound();
//		} else {
		// End game
//          if (gameDraw()) {
//              TODO: Implement this method, if the game is a draw, play a tiebreaker round
//              playRound();
// 		    } else {
//			    selectGameWinner(player1, player2);
//			    displayLeaderboard();
//		    }
//		}
	}

	/*
	 * Questo metodo fa il display delle carte disponibili per il giocatore e il suo wild card
	 * TODO: Display the wild card selected for that round
	 *
	 * @param player Il giocatore che seleziona la carta
	 */
	void displayPlayerCards(Player player) {
		Card[] cards = player.getCards();
		ImageView[] cardViews = {
				playerCard1, playerCard2, playerCard3,
				playerCard4, playerCard5, playerCard6,
				playerCard7, playerCard8, playerCard9
		};

		// Sort the cards by element
		Arrays.sort(cards, Comparator.comparing(card -> card.getElement().ordinal()));

		for (int i = 0; i < cards.length; i++) {
			Image image = cards[i].getArt();
			cardViews[i].setImage(image);
		}

		// Display wild card
		Card wildCard = selectWildCard();
		Image image = wildCard.getArt();
		wildCardImage.setImage(image);
	}

	/*
	 * Viene chiamato all'inizio di ogni round, si applica una delle carte Wild (Indebolimento, Potenziamento)
	 */
	public static Card selectWildCard() {
		Random rand = new Random();
		Card[] wilds = deck.getWildsDeck();
		return wilds[rand.nextInt(wilds.length)];
	}

	/*
	 * Questo metodo permette al giocatore di selezionare una carta dal proprio mazzo
	 * TODO: In scene builder, set the onAction property of each card to this method
	 * TODO: showCardPopup() should be called when a card is clicked, the scene is still to be done (follow pdf)
	 */
	Card selectCard(ActionEvent event) {
		return null;
	}

	/*
	 * Questo metodo mostra la carta selezionata dal giocatore quando clicca su di essa
	 */
	void showCardPopup() {
	}

	/*
	 * Questo metodo determina il vincitore del round, in base alle carte selezionate dai giocatori
	 */
//	Player selectRoundWinner(Card player1Card, Card player2Card) {
//		double p1Dmg = finalCardDamage(player1Card, player2Card, player1.getWildCard());
//		double p2Dmg = finalCardDamage(player2Card, player1Card, player2.getWildCard());
//
	// TODO: ADD A DRAW SCENARIO
//		if (p1Dmg == p2Dmg) {
//			return roundDraw();
//		} else {
//			return p1Dmg > p2Dmg ? player1 : player2;
//		}
//	}

	/*
	 * Questo metodo calcola il danno finale di una carta, in base al danno base e ad eventuali modificatori:
	 * - Indebolimento: 0.8x danno
	 * - Potenziamento: 1.2x danno
	 *
	 * - Fuoco attacca Terra: 1.2x danno (0.8x se vice-versa)
	 * - Terra attacca Acqua: 1.2x danno (0.8x se vice-versa)
	 * - Acqua attacca Fuoco: 1.2x danno (0.8x se vice-versa)
	 */
	double finalCardDamage(Card attackerCard, Card defenderCard, Card.Wild wildCard) {
		double dmg = attackerCard.getDamage();
		double mult = 1.0;

		// Situazione di vantaggio elementale
		if (attackerCard.getElement() == Card.Element.FIRE && defenderCard.getElement() == Card.Element.FOREST) {
			mult = 1.2;
		} else if (attackerCard.getElement() == Card.Element.FOREST && defenderCard.getElement() == Card.Element.WATER) {
			mult = 1.2;
		} else if (attackerCard.getElement() == Card.Element.WATER && defenderCard.getElement() == Card.Element.FIRE) {
			mult = 1.2;
		}

		// Situazione di svantaggio elementale
		if (attackerCard.getElement() == Card.Element.FIRE && defenderCard.getElement() == Card.Element.WATER) {
			mult = 0.8;
		} else if (attackerCard.getElement() == Card.Element.WATER && defenderCard.getElement() == Card.Element.FOREST) {
			mult = 0.8;
		} else if (attackerCard.getElement() == Card.Element.FOREST && defenderCard.getElement() == Card.Element.FIRE) {
			mult = 0.8;
		}

		// TODO: THE SPECIFIC ELEMENT OF THE WILD CARD
		// Wild card effects
		if (wildCard == Card.Wild.INDEBOLIMENTO) {
			mult *= 0.8;
		} else if (wildCard == Card.Wild.POTENZIAMENTO) {
			mult *= 1.2;
		}

		return dmg * mult;
	}

	/*
	 * Seleziona il vincitore a fine partita, in base al numero massimo di punti conseguiti
	 * TODO: Implement the draw scenario between players, including the tiebreaker round OR just declare a draw (latter is easier)
	 */
	public Player selectGameWinner(Player player1, Player player2) {
		return player1.playerScore > player2.playerScore ? player1 : player2;
	}

	/*
	 * TODO: GAME DRAW SCENARIO
	 */
	public void gameDraw() {
	}

	/*
	 * Parte a termine partita, una volta determinato il vincitore [selectVictor()]
	 * Fa un switchToScene() alla schermata di fine partita
	 */
	public void displayLeaderboard() {
	}

	/*
	 * TODO: MENU BAR METHODS
	 */
	@FXML
	void quitAndSaveGame(ActionEvent event) {
	}

	@FXML
	void restartGame(ActionEvent event) {
	}

	@FXML
	void saveGame(ActionEvent event) {
	}
}