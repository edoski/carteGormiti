package game;

public class Card {


    public class Wild {
        private final Impact impact;

        public Wild(String name, Impact impact) {
            super();
            this.impact = impact;
        }

        public Impact getImpact() {
            return impact;
        }

        public String getName() {
            return getName();
        }

        public String toString() {
            return getName() + " " + this.impact;
        }

    }


    public class Regular {

        //RegularCard extends Card {String nome, double vita, Elemento elemento,
        // double magia, double potenza, double velocità}


        private final double health;
        private final double magic;
        private final double power;
        private final double speed;


        public Regular(String name, Element element, double health, double magic, double power, double speed) {
            super();
            this.health = health;
            this.magic = magic;
            this.power = power;
            this.speed = speed;
        }

        public String getName() {
            return getName();
        }

        public Element getElement() {
            return getElement();
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
            return (magic + power + speed) / 5;
        }

        public String toString() {
            return "Nome carta: " + getName() + "\n" +
                    "Vita: " + health + "\n" +
                    "Elemento: " + getElement() + "\n" +
                    "Magia: " + magic + "\n" +
                    "Potenza: " + power + "\n" +
                    "Velocità: " + speed + "\n";
        }

    }


    public enum Element {
        TERRA, ARIA, ACQUA, FUOCO
    }


    public enum Impact {
        INCREMENTO, RINASCITA, CONDANNA, CONDIZIONE, RAGGIRO, PROTEZIONE, KAMIKAZE, CONGELAMENT0, NOBILE

        /*
        Incremento: aumenta le di una carta del abilità del 20%
        Rinascita: una carta a scelta tra quelle nel cimitero puo tornare nel campo di battaglia
        Condanna: una carta a scelta tra quelle nel campo di battaglia viene distrutta
        Condizione: se possiedi 4 carte dello stesso tipo puoi infliggere un attacco simultaneo a 4 carte avversarie
        Raggiro: scambia 2 carte a scelta con quelle dell'avversario
        Protezione: una carta a scelta tra quelle nel campo di battaglia non puo essere attaccata
        KAMIKAZE: Sacrifichi una carta per distruggere 3 carte avversarie
        Congelamento: una carta a scelta tra quelle nel campo di battaglia non puo essere utilizzata per 2 turni
        Nobile: la carta su cui viene applicato questo effetto puo attaccare 2 volte ogni turno
         */
    }





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
