package main;

import org.json.JSONObject;

public class Card {
	public enum Element {
		FIRE, WATER, FOREST
	}

	public enum Wild {
		SUPER, POTENZIAMENTO, INDEBOLIMENTO
	}

	private final String name;
	private Element element;
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
	public Card(final String name, final Wild wild, final String art) {
		this.name = name;
		this.wild = wild;
		this.art = art;
	}

	public static Card fromJson(JSONObject jsonObject, Wild wild) {
		String name = jsonObject.getString("name");
		String art = jsonObject.getString("art");

		return new Card(name, wild, art);
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

	public String getArt() {
		return art;
	}

	public String toStringElement() {
		return name + "\t" + element + "\t" + damage + "\n";
	}

	public String toStringWild() {
		return name + "\t" + wild + "\n";
	}
}