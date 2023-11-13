package carte_gioco;


import java.util.ArrayList;

public class Deck {

  private ArrayList<Card> cards;
  private final String path = "src/main/java/carte_gioco/deck.txt";
  private final String pathPlayerHand = "src/main/java/carte_gioco/playerHand.txt";


    public Deck(){
      createDeck();
    }

    public void createDeck(){
      //To do: create the deck of cards
    }

    public void shuffle(){
      //To do: shuffle the deck
    }

    public ArrayList<Card> createPlayerHand(){
      ArrayList<Card> playerHand = new ArrayList<Card>();
      //To do: create the player hand
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
