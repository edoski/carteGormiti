package login;

import game.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class LoginController {

    GameModeController gameState;

    private ArrayList<Player> players;
    private final int count = 0;
    private final String code = "";


    @FXML
    private Label codeLabel;

    @FXML
    private RadioButton human, robot;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField loginTextField;

    @FXML
    private ProgressBar progress;

    @FXML
    void addPlayer(ActionEvent event) {


    }

    public void generateCode() {
        /*
         * Admin genera una stringa che viene associata alla partita appena creata,
         * attraverso questo codice si può riprendere una partita
         */
    }

    public void initializePlayers() {
        /*
         * Inizializza i dati dei giocatori, il loro nome (String), e semmai un ID univoco (int)
         */
    }
}