package game;

import java.util.ArrayList;

public class Deck {

  private ArrayList<Card> cards;

    public Deck(){
      createDeck();
    }

    public void createDeck(){
      /*
       *
       * Questo metodo inizializza il deck all'inizio della partita complessiva:
       * - 18 carte normali, 3 tipi: water, fire, earth
       * - 8 carte wild, 3 tipi: indebolimento, potenziamento, vincita/perdita
       *
       */
    }

  public ArrayList<Card> createPlayerDeck() {
      ArrayList<Card> playerHand = new ArrayList<Card>();

    /*
     *
     * Questo metodo distribuisce le carte a ciascun giocatore all'inizio della partita una volta,
     * a ciascun giocatore vengono distribuite 9 carte normali:
     * - 3 fuoco
     * - 3 acqua
     * - 3 terra
     *
     */

      return playerHand;
    }

    public boolean savePlayerHand(ArrayList<Card> playerHand){

      //To do: save the player hand
      return false;
    }

    public boolean saveDeck(String path){
      //To do: save the deck
      return false;
    }

    public ArrayList<Card> getPlayerHand(String pathPlayerHand){
      //To do: get the player hand from a file
      return null;
    }

    public ArrayList<Card> getDeck(String path){
      //To do: get the deck from a file
      return null;
    }
}