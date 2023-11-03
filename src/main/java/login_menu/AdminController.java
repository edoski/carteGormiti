package login_menu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class AdminController {
    @FXML
    Label checkLoginLabel;
    @FXML
    Label trialsLabel;
    @FXML
    TextField adminUsername;
    @FXML
    PasswordField adminPassword;
    @FXML
    Button loginButton;

    private final String username = "admin";
    private final String password = "onward";
    private int trials = 0;

    public void loginAdmin() {
        if (adminUsername.getText().equals(username) && adminPassword.getText().equals(password)) {
            // switch to loginscene
        } else {
            String errorCase;
            if (adminUsername.getText().equals(username))
                errorCase = "password";
            else if (adminPassword.getText().equals(password))
                errorCase = "username";
            else
                errorCase = "username & password";

            checkLoginLabel.setText("Error, incorrect " + errorCase);
            checkLoginLabel.setTextFill(Color.RED);
            checkLoginLabel.setStyle("-fx-font-weight: bold;");
//            set to bold checkLoginLabel


//            trials aumenta di 2 ogni click di loginButton


            trials++;
            trialsLabel.setText("Attempt: " + trials);
            trialsLabel.setTextFill(Color.RED);
            trialsLabel.setStyle("-fx-font-weight: bold;");

            //if (trials == 3)
        }

    }
}
