package main;

import javafx.scene.image.Image;
import org.json.JSONObject;

import java.io.InputStream;

public class Card {
	public enum Element {
		FIRE, WATER, FOREST
	}

	public enum Wild {
		SUPER, POTENZIAMENTO, INDEBOLIMENTO
	}

	private final String name;
	private final Element element;
	private Wild wild;
	private double damage;
	private final String art;

	//	Per le Carte Normali
	public Card(final String name, final Element element, final double damage, final String art) {
		this.name = name;
		this.element = element;
		this.damage = damage;
		this.art = art;
	}

	public static Card fromJson(JSONObject jsonObject, Element element) {
		String name = jsonObject.getString("name");
		double damage = jsonObject.getDouble("damage");
		String art = jsonObject.getString("art");

		return new Card(name, element, damage, art);
	}


	//	Per le Carte Wild
	public Card(final String name, final Wild wild, final Element element, final String art) {
		this.name = name;
		this.wild = wild;
		this.element = element;
		this.art = art;
	}

	public static Card fromJson(JSONObject jsonObject, Wild wild) {
		String name = jsonObject.getString("name");
		String art = jsonObject.getString("art");
		String elementString = jsonObject.getString("element");

		Element element = Element.valueOf(elementString.toUpperCase());

		return new Card(name, wild, element, art);
	}

	public String getName() {
		return name;
	}

	public Element getElement() {
		return element;
	}

	public double getDamage() {
		return damage;
	}

	public Image getArt() {
		try {
			// substring(19) per rimuovere il prefisso "src/main/resources/"
			String artPath = this.art.substring(19);
			InputStream is = getClass().getResourceAsStream(artPath);
			return new Image(is);
		} catch (NullPointerException e) {
			System.out.println("An error occurred while trying to load the image: " + this.art);
			return null;
		}
	}

	public String toStringElement() {
		return name + "\t" + element + "\t" + damage + "\n";
	}

	public String toStringWild() {
		return name + "\t" + wild + "\n";
	}
}