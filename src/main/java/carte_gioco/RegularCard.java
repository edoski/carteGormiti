package carte_gioco;

public class RegularCard extends Card {

    //RegularCard extends Card {String nome, double vita, Elemento elemento,
    // double magia, double potenza, double velocità}


    private final double health;
    private final double magic;
    private final double power;
    private final double speed;


    public RegularCard(String name, Element element, double health, double magic, double power, double speed) {
        super(name, element);
        this.health = health;
        this.magic = magic;
        this.power = power;
        this.speed = speed;
    }

    public String getName() {
        return super.getName();
    }

    public Element getElement() {
        return super.getElement();
    }

    public double getHealth() {
        return health;
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
        return ( magic + power + speed) / 5;
    }

    public String toString() {
        return "Nome carta: " + super.getName() + "\n" +
                "Vita: " + health + "\n" +
                "Elemento: " + super.getElement() + "\n" +
                "Magia: " + magic + "\n" +
                "Potenza: " + power + "\n" +
                "Velocità: " + speed + "\n";
    }

}
