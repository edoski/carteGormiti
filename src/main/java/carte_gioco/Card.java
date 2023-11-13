package carte_gioco;

public class Card {

    private final String name;
    private  Element element;

    public Card(String name, Element element) {
        this.name = name;
        this.element = element;
    }

    public Card(String name) {
        this.name = name;
    }

    public Element getElement() {
        return element;
    }

    public String getName() {
        return name;
    }

}
