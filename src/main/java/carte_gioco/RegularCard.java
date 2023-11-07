package carte_gioco;

public class RegularCard extends Card {
    public enum Element {
        TERRA, ARIA, MARE, FORESTA, MAGNA
    }

    public String nomeCarta;
    public Element element;

}
