package carte_gioco;

public class WildCard extends Card {

    private final Impact impact;




    public WildCard(String name, Impact impact) {
        super(name);
        this.impact = impact;
    }

    public Impact getImpact() {
        return impact;
    }
    public String getName(){
        return super.getName();
    }

    public String toString(){
        return super.getName() + " " + this.impact;
    }

}
