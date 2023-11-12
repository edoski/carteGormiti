package carte_gioco;


import java.util.ArrayList;

public class Deck {

  public ArrayList<RegularCard> regularCards;
  public ArrayList<WildCard> wildCards;

  public Deck(ArrayList<RegularCard> regularCards, ArrayList<WildCard> wildCards) {
    this.regularCards = regularCards;
    this.wildCards = wildCards;
  }
}
