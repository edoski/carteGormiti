package main;

import java.util.ArrayList;
import java.util.Random;

public class Player {
    final String name;
    ArrayList<Card> playerHand;
    int playerScore;
    Card roundWildCard;
    Card chosenCard;

    public Player(String name) {
        this.name = name.substring(0, Math.min(name.length(), 16));
        this.playerHand = new ArrayList<>();
        this.chosenCard = null;
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

    public class Human {
        public Human(String name) {
            super();
        }
    }

    public class Robot {
        public Robot() {
            super();
        }

        // TODO: CHECK IF IT WORKS
        // TODO: IMPLEMENT LOGIC THAT CHECKS FOR THE BEST CARD TO PLAY BASED ON CURRENT WILD CARD
        public Card playTurn() {
            ArrayList<Card> cards = getHand();
            Random rand = new Random();
            Card selectedCard = cards.get(rand.nextInt(cards.size()));
            setChosenCard(selectedCard);
            return selectedCard;
        }
    }
}