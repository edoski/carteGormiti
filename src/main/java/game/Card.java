package game;


public class Card {

	public enum Element {
		FIRE, WATER, FOREST,
		SUPER, POTENZIAMENTO, INDEBOLIMENTO;

		private static final Element[] elements = Element.values();

		public static Element getElement(int i) {
			return Element.elements[i];
		}
	}


	private final String name;
	private final Element element;


}