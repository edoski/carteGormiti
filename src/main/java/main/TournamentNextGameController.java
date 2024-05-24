package main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static main.CreateGameController.*;

public class TournamentNextGameController {

	Parent root;
	Scene scene;
	Stage stage;

	@FXML
	private Label gameWinnerLabel;

	@FXML
	private Button homeBtn;

	@FXML
	private Button nextGameBtn;

	@FXML
	private Label p1EndGameLabel;

	@FXML
	private Label p2EndGameLabel;

	@FXML
	private Button quitBtn;

	static String nextGameCode;

	static Player match2Player1;
	static Player match2Player2;


	public void switchToScene(String fxmlFile) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
		root = loader.load();
		stage = (Stage) gameWinnerLabel.getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void setPlayersDisplay(Player p1, Player p2) {
		p1EndGameLabel.setText(p1.getName() + "\n" + p1.playerScore + " punti");
		p2EndGameLabel.setText(p2.getName() + "\n" + p2.playerScore + " punti");
	}

	public void setGameWinner(Player winner) {
		gameWinnerLabel.setText(winner.getName() + " ha vinto la partita!");
		gameWinnerLabel.setStyle("-fx-text-fill: lime");
	}

	@FXML
	void goHome(ActionEvent event) {
		// GameSaveLoad.saveGame();
		try {
			switchToScene("MainMenu.fxml");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@FXML
	void goNextGame(ActionEvent event) {
		Game.isTournament = true;
		Game.isNewGame = true;
//		code = nextGameCode;
//		if (Game.isTournament) {
//			if (nextGameCode.endsWith("-2")) {
////				Game.player1 = match2.getFirst();
////				Game.player2 = match2.getLast();
////				Game.setPlayers(match2);
//			} else {
//				Game.player1 = CreateGameController.match3.getFirst().get();
//				Game.player2 = CreateGameController.match3.getLast().get();
//				Game.setPlayersFinalTournamentGame(CreateGameController.match3);
//			}
//		}

		try {
			switchToScene("Game.fxml");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@FXML
	void quit(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Conferma uscita");
		alert.setHeaderText("Sei sicuro di voler uscire?");
		alert.showAndWait();
		if (alert.getResult().getText().equals("OK")) {
			System.exit(0);
		} else {
			alert.close();
		}
	}

	public void startTournament() {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		CountDownLatch latch1 = new CountDownLatch(1);
		CountDownLatch latch2 = new CountDownLatch(1);

		Runnable match1Task = () -> {
			System.out.println("Entered Thread 1");
			Game.isNewGame = true;
			Game.isTournament = true;
			match1.add(players.removeFirst());
			match1.add(players.removeFirst());
			Game.setPlayers(match1);
			code += "-1";
			activeCodes.add(code);
			Platform.runLater(() -> {
				try {
					switchToScene("Game.fxml");
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
				latch1.countDown();
			});
		};

		executor.submit(match1Task);

		Runnable match2Task = () -> {
			try {
				latch1.await();
				System.out.println("Entered Thread 2");
				Game.isTournament = true;
				Game.isNewGame = true;
				match2.add(players.removeFirst());
				match2.add(players.removeFirst());
				Game.setPlayers(match2);
				code = code.substring(0, code.length() - 1) + "2";
				activeCodes.add(code);
				Platform.runLater(() -> {
					try {
						switchToScene("Game.fxml");
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}
				});
//				System.out.println(TournamentWinnerController.game1Winner.getName());
				latch2.countDown();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		};

		executor.submit(match2Task);

		Runnable match3Task = () -> {
			try {
				latch2.await();
				// ... existing code from CreateGameController ...
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		};

//		executor.submit(match3Task);
		executor.shutdown();
	}
}