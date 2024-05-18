package main;

import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;


public class Deck {

	/*
	 * Utilizziamo un ArrayList piuttosto che un array statico, perché comunque ArrayList è di base un array (dinamico), ma per quello che ci riguarda
	 * non ci interessa l'espansione, poiché il game-flow del gioco prevede una diminuzione di carte nel deck di ciascun giocatore partendo da un max
	 * iniziale fisso. Pertanto, manteniamo ArrayList per comodità dei metodi built-in, senza alcuna perdita di efficienza (come per es. LinkedList).
	 */
	private static ArrayList<Card> elementsDeck;
	private static ArrayList<Card> wildsDeck;


	public Deck() {
		createDeck();
	}


	/*
	 * Questo metodo crea il deck di carte, leggendo da file JSON le carte elementali e le carte wild.
	 */
	public static void createDeck() {
		elementsDeck = new ArrayList<>();
		wildsDeck = new ArrayList<>();

		Card.Element[] elements = Card.Element.values();
		try {
			String content = new String(Files.readAllBytes(Paths.get("src/main/resources/cards/cards.json")));
			JSONObject jsonObject = new JSONObject(content);

			for (Card.Element element : elements) {
				if (jsonObject.has(element.name().toLowerCase())) {
					JSONObject elementCards = jsonObject.getJSONObject(element.name().toLowerCase());
					for (String key : elementCards.keySet()) {
						JSONObject cardObject = elementCards.getJSONObject(key);
						Card card = Card.fromJson(cardObject, element);
						elementsDeck.add(card);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Card.Wild[] wilds = Card.Wild.values();
		try {
			String content = new String(Files.readAllBytes(Paths.get("src/main/resources/cards/wilds.json")));
			JSONObject jsonObject = new JSONObject(content);

			for (Card.Wild wild : wilds) {
				if (jsonObject.getJSONObject("wild").has(wild.name().toLowerCase())) {
					JSONObject wildCards = jsonObject.getJSONObject("wild").getJSONObject(wild.name().toLowerCase());
					for (String key : wildCards.keySet()) {
						JSONObject cardObject = wildCards.getJSONObject(key);
						for (String elementKey : cardObject.keySet()) {
							Card card = Card.fromJson(cardObject, wild);
							wildsDeck.add(card);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/*
	 * Questo metodo distribuisce le carte a ciascun giocatore all'inizio della partita una volta,
	 * a ciascun giocatore vengono distribuite 9 carte normali in maniera random
	 */
	public Card[] createPlayerDeck() {
		Card[] playerHand = new Card[9];
		// Distribuzione carte elementali, randomizzando l'ordine
		Random rand = new Random();

		for (int i = 0; i < 9; i++) {
			int randomIndex = rand.nextInt(elementsDeck.size());
			playerHand[i] = elementsDeck.get(randomIndex);
			elementsDeck.remove(randomIndex);
		}

		// print player hand
//		for (Card card : playerHand) {
//			System.out.println(card.toStringElement());
//		}
		return playerHand;
	}

	public Card[] getWildsDeck() {
		Card[] wilds = new Card[wildsDeck.size()];
		for (int i = 0; i < wildsDeck.size(); i++) {
			wilds[i] = wildsDeck.get(i);
		}
		return wilds;
	}

	public void savePlayerHand(Card[] playerHand) {
		//Todo: save the player hand to a file as a JSON object
	}

	public Card[] getPlayerHand(String pathPlayerHand) {
		//Todo: get the player hand from a file as a JSON object
		return null;
	}
	public ArrayList<Card> getDeck(String path) {
		//To do: get the deck from a file
		return null;
	}

	//	TODO: START
	//      DELETE WHEN FINISHED TESTING
	// 	TODO: END
//	public static void main(String[] args) {
//		createDeck();
//
////		int i = 0;
////    print with toString wild card deck
////		for (Card card : wildsDeck) {
////			System.out.println(card.toStringWild() + " " + i++);
////		}
////
////		i = 0;
////    print with tOString for element card deck
////		for (Card card : elementsDeck) {
////			System.out.println(card.toStringElement() + " " + i++);
////		}
//
//		Deck deck = new Deck();
//		deck.createPlayerDeck();
//	}
}