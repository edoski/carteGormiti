package login_menu;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
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

    Parent root;
    Stage stage;
    Scene scene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventHandler<KeyEvent> enterKey = keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                validateEmptyFields();
                loginButton.fire();
            }
        };

        adminUsername.setOnKeyPressed(enterKey);
        adminPassword.setOnKeyPressed(enterKey);

//        AGGIUNTO LISTENER CHE VERIFICANO SE L'UTENTE HA COMINCIATO AD INSERIRE USERNAME/PASSWORD DOPO AVER PROVATO A FARE LOGIN CON I FIELD VUOTI
        adminUsername.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty())
                colorFieldBorder(adminUsername, "none");
            else
                colorFieldBorder(adminUsername, "red");
        });

        adminPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty())
                colorFieldBorder(adminPassword, "none");
            else
                colorFieldBorder(adminPassword, "red");
        });

    }

    //    TEXTINPUTCONTROL È CLASSE PADRE SIA DI TEXTFIELD CHE DI PASSWORDFIELD
    public void colorFieldBorder(TextInputControl field, String color) {
        field.setStyle("-fx-border-color: " + color + ";");
    }

    //    SE FIELD USERNAME O PASSWORD VUOTA, COLORO DI ROSSO IL PERIMETRO E SPOSTO CURSORE LÌ
    private void validateEmptyFields() {
        if (adminUsername.getText().isEmpty()) {
            adminUsername.requestFocus();
            colorFieldBorder(adminUsername, "red");
        } else if (adminPassword.getText().isEmpty()) {
            adminPassword.requestFocus();
            colorFieldBorder(adminPassword, "red");
        }
    }

    public void switchToScene(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void loginAdmin() throws IOException {
        if (adminUsername.getText().equals(username) && adminPassword.getText().equals(password))
            switchToScene("LoginScene.fxml");

        else {
            String errorCase;
            if (!adminUsername.getText().equals(username) && !adminPassword.getText().equals(password)) {
                errorCase = "username & password";
                colorFieldBorder(adminUsername, "red");
                colorFieldBorder(adminPassword, "red");
            } else {
                boolean correctUsername = adminUsername.getText().equals(username);

                errorCase = correctUsername ? "password" : "username";

                if (correctUsername) {
                    colorFieldBorder(adminPassword, "red");
                    adminPassword.requestFocus();
                } else {
                    colorFieldBorder(adminUsername, "red");
                    adminUsername.requestFocus();
                }
            }

            checkLoginLabel.setText("Error, incorrect " + errorCase);
            checkLoginLabel.setTextFill(Color.RED);

            trialsLabel.setText("Attempt: " + ++trials);

            if (trials == 3) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("You are about to exceed the number of trials");
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
