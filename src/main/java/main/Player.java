package main;

import java.util.ArrayList;
import java.util.Random;

public class Player {
    final String name;
    ArrayList<Card> playerHand;
    int playerScore;
    Card roundWildCard;
    Card chosenCard;
    boolean isCPU;

    public Player(String name) {
        this.name = name.substring(0, Math.min(name.length(), 12));
        this.playerHand = new ArrayList<>();
        this.chosenCard = null;
        this.isCPU = false;
        playerScore = 0;
    }

    public String getName() {
        return name;
    }

    public void setHand(ArrayList<Card> playerHand) {
        this.playerHand = playerHand;
    }

    public void setRoundWildCard(Card roundWildCard) {
        this.roundWildCard = roundWildCard;
    }

    public void setChosenCard(Card chosenCard) {
        this.chosenCard = chosenCard;
    }

    public Card getRoundWildCard() {
        return roundWildCard;
    }

    public Card getChosenCard() {
        return chosenCard;
    }

    public ArrayList<Card> getHand() {
        return playerHand;
    }

    public void removeUsedCard() {
        this.getHand().remove(this.getChosenCard());
    }

    // TODO: IMPLEMENT LOGIC THAT CHECKS FOR THE BEST CARD TO PLAY BASED ON CURRENT WILD CARD
    // TODO: ADD 1S DELAY TO CPU MOVES
    public Card selectCardCPU() {
        ArrayList<Card> cards = getHand();
        Random rand = new Random();
        Card selectedCard = cards.get(rand.nextInt(cards.size()));
        setChosenCard(selectedCard);
        return selectedCard;
    }
}