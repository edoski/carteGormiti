package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GameModeController implements Initializable {
    @FXML
    private ChoiceBox<String> difficulty;

    @FXML
    private ChoiceBox<String> gameMode;

    public String mode, diff;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        difficulty.getItems().addAll("Easy", "Hard");
        gameMode.getItems().addAll("1vs1", "Torneo");

        gameMode.setValue("1vs1");
        difficulty.setValue("Easy");
        gameMode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(gameMode.getSelectionModel().getSelectedItem());
        });
    }

    @FXML
    void confirm(ActionEvent event) {
        mode = gameMode.getSelectionModel().getSelectedItem();
        diff = difficulty.getSelectionModel().getSelectedItem();
    }

}
