package main;

public class Player {
    final String name;
    Card[] playerHand;
    int playerScore;

    public Player(String name) {
        this.name = name;
        // TODO: MAYBE ADD this.playerHand = playerHand passed from Game
        playerScore = 0;
    }

    public String getName() {
        return name;
    }

    public void setHand(Card[] playerHand) {
        this.playerHand = playerHand;
    }

    public Card[] getCards() {
        return playerHand;
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
    }
}