package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class GameSave {
	static String code = Game.code;
	static Player p1, p2;
	static int p1Score, p2Score;
	static ArrayList<Card> p1Hand, p2Hand;
	static Card p1WildCard, p2WildCard;
	static int roundNumber;


	/*
	 * SAVE FILE FORMAT:
	 * code
	 * roundNumber
	 * p1Name
	 * p1Score
	 * p1WildCard
	 * p1HandSize (n)
	 * p1Hand (n lines for n cards)
	 * p2Name
	 * p2Score
	 * p2WildCard
	 * p2HandSize (n)
	 * p2Hand (n lines for n cards)
	 */
	public static void saveGame() {
		roundNumber = Game.roundNumber;
		p1 = Game.player1;
		p2 = Game.player2;
		p1Score = p1.playerScore;
		p2Score = p2.playerScore;
		p1Hand = p1.getHand();
		p2Hand = p2.getHand();
		p1WildCard = p1.getRoundWildCard();
		p2WildCard = p2.getRoundWildCard();

		String currDir = System.getProperty("user.dir");
		File directory = new File(currDir, "games");
		if (!directory.exists()) {
			directory.mkdirs();
		}
		File gameSave = new File(directory, code + ".txt");

		try {
			FileWriter writer = new FileWriter(gameSave);
			PrintWriter printer = new PrintWriter(writer);

			// PLAYER 1
			printer.println(code);
			printer.println(roundNumber);
			printer.println(p1.getName());
			printer.println(p1Score);
			printer.println(p1WildCard.getName() + "," + p1WildCard.getWild() + "," + p1WildCard.getElement() + "," + p1WildCard.getArtPath());

			int p1HandSize = p1Hand.size();
			printer.println(p1HandSize);
			for (Card card : p1Hand) {
				printer.println(card.getName() + "," + card.getElement() + "," + card.getDamage() + "," + card.getArtPath());
			}

			// PLAYER 2
			printer.println(p2.getName());
			printer.println(p2Score);
			printer.println(p2WildCard.getName() + "," + p2WildCard.getWild() + "," + p2WildCard.getElement() + "," + p2WildCard.getArtPath());

			int p2HandSize = p2Hand.size();
			printer.println(p2HandSize);
			for (Card card : p2Hand) {
				printer.println(card.getName() + "," + card.getElement() + "," + card.getDamage() + "," + card.getArtPath());
			}

			printer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * Questo metodo fa partire un gioco da un file di salvataggio collocato nella cartella "games" del progetto.
	 * Fa leva sul metodo Game.
	 */
}