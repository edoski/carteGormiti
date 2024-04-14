package game;

import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players;

    private final String code = "";

    public void setPlayers(Player player1, Player player2) {
        this.players.add(player1);
        this.players.add(player2);
    }

    public void startGame() {
        /*
         * Comincia una partita, lancia generateCode(), initializePlayers()
         * e poi fa partire la partita una volta generati i dati json di default
         */
    }

    public void loadGame(String code) {
        /*
         * Carica una partita prendendo un codice associato a una partita in input
         */
    }

    public void restartGame() {
        /*
         * Ricomincia una partita da capo
         */
    }

    public void saveGame() {
        /*
         * Salva la partita dall'interno del gioco, lo stato viene salvato su json
         */
    }

    public void quitGame() {
        /*
         * Chiude il gioco, lancia un popup che chiede se l'utente vuole salvare la partita [saveGame()] o meno
         */
    }

    public void selectVictor() {
        /*
         * Seleziona il vincitore a fine partita, in base al numero massimo di punti conseguiti
         */
    }

    public void displayLeaderboard() {
        /*
         * Parte a termine partita, una volta determinato il vincitore [selectVictor()]
         */
    }

    public void selectWildCard() {
        /*
         * Viene chiamato all'inizio di ogni round (2 turni), si applica una delle carte Wild (Indebolimento, Potenziamento, Vincita/Perdita)
         */
    }

    public void creaTorneo() {
        /*
         * Crea un torneo che consiste di 4 o 8 giocatori, vengono di seguito lanciate in sequenza 2 o 3 partite singole rispettivamente
         * La composizione di giocatori può essere interamente umana/robot, o un mix di entrambi
         */
    }
}