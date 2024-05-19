package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.*;


/*TODO:
 * - MAKE THE CARDS OPACITY SLIGHTLY DIMMER, AND THEN FULLY VISIBLE WHEN HOVERED OVER
 * - CHECK WHY CPU02 STARTS BEFORE CPU01 WHEN CPU01 IS SELECTED FIRST
 * - ADD A TIEBREAKER ROUND IF THE GAME IS A DRAW
 */


public class Game implements Initializable {

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

	@FXML
	private Button confirmCardBtn;

	@FXML
	private Label damageCardChoice;

	static Deck deck = new Deck();
	static Player player1;
	static Player player2;
	static boolean player1Turn = true;
	static int roundNumber = 1;
	// card map to map the image view to the card
	private final HashMap<ImageView, Card> cardMap = new HashMap<>();
	// wild card for the round
	static Card roundWildCard;
	// selected card
	private Card selectedCard;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		confirmCardBtn.setDisable(true);
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
			displayPlayerCards(player1);
			player1Turn = false;
		} else {
			playerLabel.setText("Scegli una carta, " + player2.getName());
			displayPlayerCards(player2);
			player1Turn = true;
		}
	}

	/*
	 * Connesso al bottone confirmCardBtn, questo metodo conferma la carta selezionata dal giocatore
	 */
	@FXML
	Card confirmCard(ActionEvent event) {
		if (selectedCard != null) {
			return selectedCard;
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Errore");
			alert.setHeaderText("Carta non selezionata");
			alert.setContentText("Seleziona una carta prima di confermare");
			alert.showAndWait();
			return null;
		}
	}

	@FXML
	void onConfirmCard(ActionEvent event) {
		Card selectedCard = confirmCard(event);
//		Player winner = selectRoundWinner(player1Card, player2Card);
//		displayWinner(winner);
		// Update player scores
		// TODO: DOES THIS UPDATE THE PLAYER1 OR PLAYER2 SCORE? OR JUST THE WINNER OBJECT MADE HERE?
//		winner.playerScore++;
		// TODO: NEEDS TO UPDATE ONLY AFTER FULL ROUND
		//  Update round number
//		roundNumber++;

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
			cardMap.put(cardViews[i], cards[i]);  // Add the ImageView and Card to the map
		}

		// Display wild card
		Card wildCard = selectWildCard();
		Image image = wildCard.getArt();
		wildCardImage.setImage(image);
	}

	/*
	 * Viene chiamato all'inizio di ogni round, si applica una delle carte Wild (Indebolimento, Potenziamento)
	 */
	public Card selectWildCard() {
		Random rand = new Random();
		Card[] wilds = deck.getWildsDeck();
		Card wildCard = wilds[rand.nextInt(wilds.length)];
		Player currentPlayer = getCurrentPlayer();
		currentPlayer.setWildCard(wildCard);

		// Display wild card effects
		String wildEffects =
				"Effetto:" +
						"\n-->" + wildCard.getWild().toString() + " " + wildCard.getElement().toString() +
						"\n--> " + (wildCard.getWild().toString().equals("INDEBOLIMENTO") ? "0.8x DANNO" : "1.2x DANNO");
		wildEffectsLabel.setText(wildEffects);

		roundWildCard = wildCard;
		return wildCard;
	}

	/*
	 * Questo metodo permette al giocatore di selezionare una carta dal proprio mazzo
	 * TODO: GET THE WILD CARD STATS AND REFLECT THE DAMAGE IN THE LABEL IF APPICABLE
	 */
	@FXML
	void selectCard(MouseEvent event) {
		confirmCardBtn.setDisable(false);
		Player player = getCurrentPlayer();
		ImageView clickedCard = (ImageView) event.getSource();
		selectedCard = cardMap.get(clickedCard);
		// get clicked card
		Image cardImage = clickedCard.getImage();
		playerCardChoiceImage.setImage(cardImage);
		// get card damage
		double mult = cardMultiplier(selectedCard, roundWildCard);
		double cardDamage = selectedCard.getDamage();
		damageCardChoice.setText("Danno: " + Math.round((cardDamage * mult) * 10.0) / 10.0);
	}

	static Player getCurrentPlayer() {
		return player1Turn ? player1 : player2;
	}

	/*
	 * Calcola il moltiplicatore della carta selezionata, in base alla carta Wild del round
	 * È diverso da finalCardDamage() perché considera solo la carta selezionata e la carta Wild del round
	 * e non la carta dell'avversario. Inoltre, fornisce solo il moltiplicatore e non il danno finale.
	 */
	static double cardMultiplier(Card card, Card wildCard) {
		double mult = 1.0;

		if (wildCard.getWild() == Card.Wild.POTENZIAMENTO) {
			if (card.getElement() == Card.Element.FIRE && roundWildCard.getElement() == Card.Element.FIRE) {
				mult = 1.2;
			} else if (card.getElement() == Card.Element.WATER && roundWildCard.getElement() == Card.Element.WATER) {
				mult = 1.2;
			} else if (card.getElement() == Card.Element.FOREST && roundWildCard.getElement() == Card.Element.FOREST) {
				mult = 1.2;
			}
		} else {
			if (card.getElement() == Card.Element.FIRE && roundWildCard.getElement() == Card.Element.FIRE) {
				mult = 0.8;
			} else if (card.getElement() == Card.Element.WATER && roundWildCard.getElement() == Card.Element.WATER) {
				mult = 0.8;
			} else if (card.getElement() == Card.Element.FOREST && roundWildCard.getElement() == Card.Element.FOREST) {
				mult = 0.8;
			}
		}

		return mult;
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
	double finalCardDamage(Card attackerCard, Card defenderCard) {
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

		// TODO: CHECK IF THE SPECIFIC ELEMENT OF THE WILD CARD WORKS
		mult *= cardMultiplier(attackerCard, roundWildCard);

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