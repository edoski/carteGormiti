package main;

import java.util.ArrayList;

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

	/*
	 * SCEGELI LA CARTA MIGLIORE IN BASE AL DANNO E AL MOLTIPLICATORE DELLA WILD CARD
	 */
	public Card returnBestCardCPU() {
        ArrayList<Card> cards = getHand();
		double maxDmg = 0;
		Card selectedCard = null;
		for (Card card : cards) {
			double dmg = Game.wildCardMultiplier(card, roundWildCard) * card.getDamage();
			if (dmg > maxDmg) {
				maxDmg = card.getDamage();
				selectedCard = card;
			}
		}
        setChosenCard(selectedCard);
        return selectedCard;
    }
}