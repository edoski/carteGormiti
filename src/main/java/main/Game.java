package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;


/*TODO:
 * - DISABLE THE CARD VIEW ONCE THE CARD HAS BEEN SELECTED SO THAT IT CAN'T BE SELECTED AGAIN IN NEXT ROUNDS
 * - MAKE THE CARDS OPACITY SLIGHTLY DIMMER, AND THEN FULLY VISIBLE WHEN HOVERED OVER
 * - ADD A TIEBREAKER ROUND IF THE GAME IS A DRAW
 * - FOR SELECTWILDCARD MAKE IT SO THAT THE WILD CARD IS NOT THE SAME AS THE PREVIOUS ROUND
 */


public class Game implements Initializable {

	Parent root;
	Scene scene;
	Stage stage;

	// Round Winner scene elements
	@FXML
	private Button endRoundQuitBtn;

	@FXML
	private Label endRoundWinnerLabel;

	@FXML
	private Button nextRoundBtn;

	@FXML
	private ImageView p1EndRoundCard;

	@FXML
	private Label p1EndRoundDmg;

	@FXML
	private Label p1EndRoundLabel;

	@FXML
	private ImageView p2EndRoundCard;

	@FXML
	private Label p2EndRoundDmg;

	@FXML
	private Label p2EndRoundLabel;

	// Game scene elements
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
	private MenuItem saveMB;

	@FXML
	private Label roundLabel;

	@FXML
	private ImageView wildCardImage;

	@FXML
	private Label wildEffectsLabel;

	@FXML
	private Button confirmCardBtn;

	@FXML
	private Label damageCardChoice;

	// Per migliorare la leggibilità del codice
	static final Card.Element FIRE = Card.Element.FIRE;
	static final Card.Element WATER = Card.Element.WATER;
	static final Card.Element FOREST = Card.Element.FOREST;

	static Deck deck = new Deck();
	static final ArrayList<Card> wildsDeck = deck.getWildsDeck();
	static Player player1;
	static Player player2;
	static Player currentPlayer;

	static ArrayList<Card> player1Hand;
	static ArrayList<Card> player2Hand;

	static int roundNumber;
	// card map to map the image view to the card
	private final HashMap<ImageView, Card> cardMap = new HashMap<>();

	// wild cards for the round
	static Card player1WildCard, p1PreviousWildCard;
	static Card player2WildCard, p2PreviousWildCard;

	// selected card
	private Card selectedCard;
	// final damage of the cards, keeps into account both respective wild cards and element advantages/disadvantages
	static double p1FinalDmg = 0, p2FinalDmg = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		if (startingNewGame) {
		startGame();
//		} else {
//			startGameFromSave(code);
//		}
	}

	/*
	 * TODO: SWITCH TO SCENE METHOD
	 */
	public void switchToScene(String fxmlFile, Button btn) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
		root = loader.load();
		scene = new Scene(root);
		stage = (Stage) btn.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	/*
	 * Questo metodo imposta i giocatori per la partita
	 */
	public static void setPlayers(ArrayList<Player> players) {
		player1 = players.get(0);
		player2 = players.get(1);
	}

	/*
	 * Questo metodo inizia la partita, creando un mazzo di carte e distribuendole ai giocatori.
	 */
	public void startGame() {
		// Create a deck of cards
		deck.createDeck();

		// Distribute cards to players
		player1Hand = deck.createPlayerDeck();
		player2Hand = deck.createPlayerDeck();

		// Set player hands
		player1.setHand(player1Hand);
		player2.setHand(player2Hand);

		// Display round number
		roundNumber = 1;
		roundLabel.setText("Round: " + roundNumber + " of 6");

		// Start game from player 1
		currentPlayer = player1;
		selectWildCard(player1);
		selectWildCard(player2);
		playRound();
	}

	/*
	 * Questo metodo gioca un round della partita, con due turni, uno per ciascun giocatore.
	 * Il metodo seleziona una carta per ciascun giocatore, determina il vincitore, aggiorna i punteggi e il numero del round.
	 */
	public void playRound() {
		confirmCardBtn.setDisable(true);
		playTurn(currentPlayer);

		// Check if both players have chosen a card
		if (player1.getChosenCard() != null && player2.getChosenCard() != null) {
			// Determine the round winner
			p1FinalDmg = finalCardDamage(player1.getChosenCard(), player2.getChosenCard(), player1WildCard);
			p2FinalDmg = finalCardDamage(player2.getChosenCard(), player1.getChosenCard(), player2WildCard);

			if (p1FinalDmg == p2FinalDmg) {
				player1.playerScore++;
				player2.playerScore++;
				// TODO: CONVERT TO "RoundWinner.fxml" SCENE
				System.out.println("Round Draw" + p1FinalDmg + " vs " + p2FinalDmg);
			} else {
				Player winner = selectRoundWinner();
				winner.playerScore++;
				// TODO: CONVERT TO "RoundWinner.fxml" SCENE
				System.out.println(winner.name + " ha vinto il round, " + p1FinalDmg + " vs " + p2FinalDmg);
			}

			// TODO: DEBUG PRINT STATEMENTS
//			System.out.printf("%s / playRound %d: %s%n", player1.getName(), roundNumber, player1WildCard.getName());
//			System.out.printf("%s / playRound %d: %s%n", player2.getName(), roundNumber, player2WildCard.getName());

			// Increment the round number
			roundLabel.setText("Round: " + ++roundNumber + " of 6");

			// END GAME SEQUENCE
			if (roundNumber > 3) {
				// TODO: REMOVE THE ALERT WHEN THE BELOW TODO IS IMPLEMENTED
//				roundLabel.setText("Game Over");
//				Alert alert = new Alert(Alert.AlertType.INFORMATION);
//				alert.setTitle("Game Over");
//				alert.setHeaderText("Game Over");
//				alert.setContentText("Game Over");
//				alert.showAndWait();
//				System.exit(0);

				// TODO: DISPLAY GAME OVER SCENE
//				if (player1.playerScore == player2.playerScore) {
				// TIEBREAKER ROUND
//					roundLabel.setText("Tiebreaker Round");
//  				player1.setChosenCard(null);
//	    			player2.setChosenCard(null);
//	    			selectWildCard(player1);
//	    			selectWildCard(player2);
//	    			displayPlayerCards(currentPlayer);
//					playRound();
//				} else {
				// GAME WINNER DISPLAY
//					selectGameWinner(player1, player2);
//					displayLeaderboard(player1, player2);
//				}

				// RESET GAME ASSETS / VARIABLES
				reset();

				try {
					switchToScene("MainMenu.fxml", confirmCardBtn);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			} else {
				// ROUND WINNER DISPLAY
//				try {
//					switchToScene("RoundWinner.fxml", confirmCardBtn);
				// TODO: NEXT ROUND BUTTON SHOULD JUST SWITCH SCENE BACK TO "Game.fxml"
//				} catch (IOException e) {
//					throw new RuntimeException(e);
//				}

				// PREPARING FOR THE NEXT ROUND
				// Reset chosen cards for both players in preparation for the next round
				player1.setChosenCard(null);
				player2.setChosenCard(null);

				// Select new wild cards for the next round
				selectWildCard(player1);
				selectWildCard(player2);

				// Display the next round
				displayPlayerCards(currentPlayer);
			}
		}
	}

	/*
	 * Questo salva la carta selezionata dal giocatore in un round
	 */
	public void playTurn(Player player) {
		playerLabel.setText("Scegli una carta, " + player.getName());
		displayPlayerCards(player);
		Card selectedCard = player.getChosenCard();
		player.setChosenCard(selectedCard);
	}

	/*
	 * Connesso al bottone confirmCardBtn, questo metodo conferma la carta selezionata dal giocatore
	 * e termina il round una volta che entrambi hanno giocato, confrontando le carte selezionate dai giocatori
	 */
	@FXML
	void onConfirmCard() {
		currentPlayer.removeUsedCard();
//		System.out.printf("%s / PRE-onConfirmCard: %s%n", currentPlayer.getName(), currentPlayer.getRoundWildCard().getName());
		currentPlayer = (currentPlayer == player1) ? player2 : player1;
//		System.out.printf("%s / POST-onConfirmCard: %s%n", currentPlayer.getName(), currentPlayer.getRoundWildCard().getName());

		// Set the playerCardChoiceImage to the default card image
		InputStream is = getClass().getResourceAsStream("/cards/default_card.jpeg");
		Image defaultCard = new Image(is);
		playerCardChoiceImage.setImage(defaultCard);
		damageCardChoice.setText("Danno: ");

		playRound();
	}

	/*
	 * Questo metodo fa il display delle carte disponibili per il giocatore e il suo wild card
	 *
	 */
	void displayPlayerCards(Player player) {
		ArrayList<Card> cards = player.getHand();
		ImageView[] cardViews = {
				playerCard1, playerCard2, playerCard3,
				playerCard4, playerCard5, playerCard6,
				playerCard7, playerCard8, playerCard9
		};

		// Clear the images, a refresh of the cards
		for (ImageView cardView : cardViews) {
			cardView.setImage(null);
		}

		// Sort the cards by element (0 = Fire, 1 = Water, 2 = Forest, like in the enum)
		cards.sort(Comparator.comparing(card -> card.getElement().ordinal()));

		cardMap.clear();
		for (int i = 0; i < cards.size(); i++) {
			Image image = cards.get(i).getArt();
			cardViews[i].setImage(image);
			cardMap.put(cardViews[i], cards.get(i));
		}

		Card wildCard = player.getRoundWildCard();
		// Display wild card effects
		String wildEffects =
				"Effetto: " + wildCard.getWild().toString() + " " + wildCard.getElement().toString() +
						"\n--> " + (wildCard.getWild().toString().equals("INDEBOLIMENTO") ? "0.8x DANNO" : "1.2x DANNO");
		wildEffectsLabel.setText(wildEffects);

		// Display wild card image
		Image image = wildCard.getArt();
		wildCardImage.setImage(image);

//		System.out.printf("%s / displayPlayerCards: %s%n", player.getName(), player.getRoundWildCard().getName());
	}

	/*
	 * Viene chiamato all'inizio di ogni round, si applica una delle carte Wild (Indebolimento, Potenziamento)
	 */
	public void selectWildCard(Player player) {
		if (player == player1) {
			p1PreviousWildCard = player1WildCard;
		} else {
			p2PreviousWildCard = player2WildCard;
		}

		// Seleziona una carta Wild a caso dal mazzo che non sia la stessa del round precedente per il giocatore
		Random rand = new Random();
		Card wildCard;
		do {
			wildCard = wildsDeck.get(rand.nextInt(wildsDeck.size()));
		} while (wildCard == p1PreviousWildCard && player == player1 || wildCard == p2PreviousWildCard && player == player2);

		player.setRoundWildCard(wildCard);

		if (player == player1) {
			player1WildCard = wildCard;
		} else {
			player2WildCard = wildCard;
		}

//		System.out.printf("%s / selectWildCard: %s%n", player.getName(), wildCard.getName());
	}

	/*
	 * Questo metodo permette al giocatore di selezionare una carta dal proprio mazzo
	 */
	@FXML
	void selectCard(MouseEvent event) {
		confirmCardBtn.setDisable(false);
		ImageView clickedCard = (ImageView) event.getSource();
		selectedCard = cardMap.get(clickedCard);
		// get clicked card
		if (selectedCard != null) {
			Image cardImage = clickedCard.getImage();
			playerCardChoiceImage.setImage(cardImage);
		} else {
			InputStream is = getClass().getResourceAsStream("/cards/default_card.jpeg");
			Image defaultCard = new Image(is);
			playerCardChoiceImage.setImage(defaultCard);
		}

		Card playerWildCard;
		if (currentPlayer == player1) {
			playerWildCard = player1WildCard;
		} else {
			playerWildCard = player2WildCard;
		}

//		System.out.printf("%s / selectCard: %s%n", currentPlayer.getName(), playerWildCard.getName());

		// get card damage
		if (selectedCard != null) {
			confirmCardBtn.setDisable(false);
			double mult = wildCardMultiplier(selectedCard, playerWildCard);
			double cardDamage = selectedCard.getDamage();
			damageCardChoice.setText("Danno: " + Math.round((cardDamage * mult) * 10.0) / 10.0);
			currentPlayer.setChosenCard(selectedCard);
		} else {
			confirmCardBtn.setDisable(true);
			damageCardChoice.setText("Danno: ");
		}
	}

	/*
	 * Calcola il moltiplicatore della carta selezionata, in base alla carta Wild del round
	 * È diverso da finalCardDamage() perché considera solo la carta selezionata e la carta Wild del round
	 * e non la carta dell'avversario. Inoltre, fornisce solo il moltiplicatore e non il danno finale.
	 */
	static double wildCardMultiplier(Card card, Card wildCard) {
		double mult = 1.0;

//		System.out.printf("%s / wildCardMultiplier: %s%n", currentPlayer.getName(), wildCard.getName());

		// Per migliorare la leggibilità del codice
		Card.Element cardElem = card.getElement();
		Card.Element wildElem = wildCard.getElement();

		if (wildCard.getWild() == Card.Wild.POTENZIAMENTO) {
			if (cardElem == FIRE && wildElem == FIRE) {
				mult = 1.2;
			} else if (cardElem == WATER && wildElem == WATER) {
				mult = 1.2;
			} else if (cardElem == FOREST && wildElem == FOREST) {
				mult = 1.2;
			}
		} else {
			if (cardElem == FIRE && wildElem == FIRE) {
				mult = 0.8;
			} else if (cardElem == WATER && wildElem == WATER) {
				mult = 0.8;
			} else if (cardElem == FOREST && wildElem == FOREST) {
				mult = 0.8;
			}
		}

		return mult;
	}

	/*
	 * Questo metodo determina il vincitore del round, in base al danno finale delle carte selezionate dai giocatori
	 */
	Player selectRoundWinner() {
		return p1FinalDmg > p2FinalDmg ? player1 : player2;
	}

	/*
	 * Questo metodo calcola il danno finale di una carta, in base al danno base e ad eventuali modificatori:
	 * - Indebolimento: 0.8x danno
	 * - Potenziamento: 1.2x danno
	 *
	 * - Fuoco attacca Terra: 1.2x danno (0.8x se vice-versa)
	 * - Terra attacca Acqua: 1.2x danno (0.8x se vice-versa)
	 * - Acqua attacca Fuoco: 1.2x danno (0.8x se vice-versa)
	 */
	double finalCardDamage(Card playerCard, Card opponentCard, Card roundWildCard) {
		double dmg = playerCard.getDamage();
		double mult = 1.0;

		// Per migliorare la leggibilità del codice
		final Card.Element playerElem = playerCard.getElement();
		final Card.Element opponentElem = opponentCard.getElement();

		// Situazione di vantaggio elementale
		if (playerElem == FIRE && opponentElem == FOREST) {
			mult = 1.2;
		} else if (playerElem == FOREST && opponentElem == WATER) {
			mult = 1.2;
		} else if (playerElem == WATER && opponentElem == FIRE) {
			mult = 1.2;
		}

		// Situazione di svantaggio elementale
		if (playerElem == FIRE && opponentElem == WATER) {
			mult = 0.8;
		} else if (playerElem == WATER && opponentElem == FOREST) {
			mult = 0.8;
		} else if (playerElem == FOREST && opponentElem == FIRE) {
			mult = 0.8;
		}

//		System.out.printf("%s / finalCardDamage: %s%n", currentPlayer.getName(), currentPlayer.getRoundWildCard().getName());
//		System.out.println("Elem. Multiplier: " + mult);

		mult *= wildCardMultiplier(playerCard, roundWildCard);

		// Debug print statements
//		System.out.println(playerCard.getElement().toString() + ", " + dmg);
//		System.out.println("Wild Card: " + roundWildCard.getName());
//		System.out.println("Enemy: " + opponentCard.getElement().toString());
//		System.out.println("Final Multiplier: " + mult);
//		System.out.println("Final Damage: " + Math.round((dmg * mult) * 10.0) / 10.0);
//		System.out.println();

		return Math.round((dmg * mult) * 10.0) / 10.0;
	}

	/*
	 * Seleziona il vincitore a fine partita, in base al numero massimo di punti conseguiti
	 */
	public Player selectGameWinner(Player player1, Player player2) {
		return player1.playerScore > player2.playerScore ? player1 : player2;
	}

	/*
	 * TODO: GAME DRAW SCENARIO
	 *  ALL IT IS IS player1.playerScore == player2.playerScore
	 *  MAYBE THIS METHOD ISN'T NEEDED, JUST LAUNCH A TIEBREAKER ROUND WITH playRound()
	 */
	public void gameDraw() {
		// TIEBREAKER ROUND
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

	/*
	 * Questo metodo fa ripartire la partita da capo, utilizzando gli stessi giocatori

	 */
	@FXML
	void restartGame(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Restart Game");
		alert.setHeaderText("Sei sicuro di voler ricominciare la partita?");
		alert.setContentText("I punteggi verranno azzerati");
		alert.showAndWait();

		if (alert.getResult().getText().equals("OK")) {
			// Salva i nomi dei giocatori pre-reset
			String p1Name = player1.getName(), p2Name = player2.getName();
			reset();
			// Riavvia la partita con gli stessi giocatori
			CreateGameController.players = new ArrayList<>();
			CreateGameController.players.add(new Player(p1Name));
			CreateGameController.players.add(new Player(p2Name));
			CreateGameController.activeCodes.add(CreateGameController.code);
			setPlayers(CreateGameController.players);
			roundNumber = 1;
			InputStream is = getClass().getResourceAsStream("/cards/default_card.jpeg");
			Image defaultCard = new Image(is);
			playerCardChoiceImage.setImage(defaultCard);
			damageCardChoice.setText("Danno: ");
			startGame();
		} else {
			alert.close();
		}
	}

	/*
	 * Questo metodo resetta le variabili della partita, in preparazione per una nuova partita
	 */
	void reset() {
		CreateGameController.players = null;
		deck.clearAllDecks();
		player1 = null;
		player2 = null;
		player1Hand = null;
		player2Hand = null;
		player1WildCard = null;
		player2WildCard = null;
		p1PreviousWildCard = null;
		p2PreviousWildCard = null;
		selectedCard = null;
		roundLabel.setText("Round: " + roundNumber + " of 6");
		CreateGameController.activeCodes.remove(CreateGameController.code);
	}

	@FXML
	void saveGame(ActionEvent event) {
	}
}