package login_menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

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

    public void loginAdmin() throws IOException {
        if (adminUsername.getText().equals(username) && adminPassword.getText().equals(password)) {
            Parent root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
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

            trialsLabel.setText("Attempt: " + ++trials);
            trialsLabel.setTextFill(Color.RED);
            trialsLabel.setStyle("-fx-font-weight: bold;");

            if (trials == 3) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("You have exceeded the number of trials");
                alert.setContentText("If you do not login with the correct credentials the next attempt, the program will shut down for safety reasons.");
                alert.showAndWait();

            }

            if (trials > 3) {
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();
            }
        }
    }
}
