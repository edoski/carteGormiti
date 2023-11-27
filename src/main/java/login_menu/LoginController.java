package login_menu;

import dati_utente.Player;
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
}
