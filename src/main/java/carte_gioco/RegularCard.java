package carte_gioco;

public class RegularCard {

    //RegularCard extends Card {String nome, double vita, Elemento elemento,
    // double magia, double potenza, double velocità}

    private final String name;
    private final double health;
    private final Element element;
    private final double magic;
    private final double power;
    private final double speed;


    public RegularCard(String name, double health, Element element, double magic, double power, double speed) {
        this.name = name;
        this.health = health;
        this.element = element;
        this.magic = magic;
        this.power = power;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public double getHealth() {
        return health;
    }

    public Element getElement() {
        return element;
    }

    public double getMagic() {
        return magic;
    }

    public double getPower() {
        return power;
    }

    public double getSpeed() {
        return speed;
    }

    //ancora da decidere ma l'idea è implementare un metodo che calcoli il danno
    public double getDamage() {
        return (health + magic + power + speed) / 6;
    }

    public String toString() {
        return "Nome carta: " + name + "\n" +
                "Vita: " + health + "\n" +
                "Elemento: " + element + "\n" +
                "Magia: " + magic + "\n" +
                "Potenza: " + power + "\n" +
                "Velocità: " + speed + "\n";
    }

}
