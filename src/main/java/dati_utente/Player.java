package dati_utente;


//vorrei non includere il conteggio dei round in modo da avere un round unico
//per ogni partita visto che ogni carta ha la propria vita e chi rimane senza carte perde.
public class Player {
    private final String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
