package dati_utente;

public class Human extends Player{
    private String name = "";

    public Human(String name) {
        super(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
