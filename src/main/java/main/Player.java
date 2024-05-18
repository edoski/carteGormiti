package main;

public class Player {
    final String name;
    Card[] playerHand;
    int playerScore;

    public Player(String name) {
        this.name = name;
        playerScore = 0;
    }

    public String getName() {
        return name;
    }

    public void setHand(Card[] playerHand) {
        this.playerHand = playerHand;
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