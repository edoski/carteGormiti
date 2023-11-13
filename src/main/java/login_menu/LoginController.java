package login_menu;

import dati_utente.Game;
import dati_utente.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Random;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    private ArrayList<Player> players;
    private int count = 0;
    private String code = "";

    @FXML
    private ChoiceBox<String> gameMode;

    @FXML
    private Label codeLabel;

    @FXML
    private RadioButton human;

    @FXML
    private RadioButton robot;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField loginTextField;

    @FXML
    private ProgressBar progress;

    @FXML
    void addPlayer(ActionEvent event) {
        gameMode.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals("Torneo"))
                giocatoriTorneo();
            else if(oldValue.equals("1vs1"))
                giocatori1vs1();
        });

    }




//    private EventListener listener = gameMode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//        if (gameMode.getSelectionModel().getSelectedItem().equals("1vs1")) {
//            if (count < 2)
//            if (count == 2)
//        }
//
//    });


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameMode.getItems().addAll("1vs1", "Torneo");
        gameMode.setValue("1vs1");



    }//Initialize


    public  void giocatoriTorneo(){
        System.out.println("Thomas");
    }

    public void giocatori1vs1(){
        System.out.println("Jean");
    }















//    ADD REMOVE PLAYER METHOD




   /* @FXML
    void addPlayer(ActionEvent event) {
        if (count == 0) {
            Random rand = new Random();
            for (int i = 0; i < 6; i++) {
                if(i < 2)
                    code += (char) (rand.nextInt(26) + 65);
                else
                    code += rand.nextInt(10);
            }//for
             loginBtn.setText("Aggiungi Giocatore");
             count++;
             codeLabel.setText(code);
             game.setCode(code);
        }else {

        }

    }

    */
}//loginController
