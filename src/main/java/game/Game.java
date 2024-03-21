package game;

import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players;

    private String code = "";

    public void setPlayers(Player player1, Player player2) {
        this.players.add(player1);
        this.players.add(player2);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }


}
