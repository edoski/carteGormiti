package main;

import javafx.animation.*;
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
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    Label adminLabel;
    @FXML
    Line line;
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

    private final String username = "1";
    private final String password = "1";
    private int trials = 0;

    Parent root;
    Stage stage;
    Scene scene;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        START ANIMATIONS
//        FADE & SCALE IN TITOLO ADMIN, FROM CENTER

        FadeTransition fadeTitle = Animations.fadeIn(adminLabel, 1, 1, Interpolator.EASE_IN);
        ScaleTransition scaleTitleIn = Animations.scale(adminLabel, 0.8, 0.8, 1, Interpolator.EASE_BOTH);

        ParallelTransition adminTitleIn = new ParallelTransition(fadeTitle, scaleTitleIn);
        adminTitleIn.play();

//        MOVE UP & SCALE OUT TITOLO ADMIN, FINAL POSITION

        ScaleTransition scaleTitleOut = Animations.scale(adminLabel, -0.6, -0.6, 1, Interpolator.EASE_BOTH);
        TranslateTransition moveTitle = Animations.translateY(adminLabel, -120, 1, Interpolator.EASE_BOTH);

        TranslateTransition moveLine = Animations.translateY(line, -125, 1, Interpolator.EASE_BOTH);
        FadeTransition fadeLine = Animations.fadeIn(line, 1, 1, Interpolator.EASE_IN);

//        ESPANSIONE RIGA DAL SUO CENTRO
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(line.endXProperty(), 160, Interpolator.EASE_BOTH),
                        new KeyValue(line.startXProperty(), -160, Interpolator.EASE_BOTH)));

        ParallelTransition adminTitleOut = new ParallelTransition(scaleTitleOut, moveTitle, moveLine, timeline, fadeLine);
        final double titleDelay = 1.1;
        adminTitleOut.setDelay(Duration.seconds(titleDelay));
        adminTitleOut.play();

//        FADE IN LOGIN AREA, FROM CENTER (FINAL POSITION)

        FadeTransition fadeCredentialsLabel = Animations.fadeIn(checkLoginLabel, 1, 1, Interpolator.EASE_IN);
        FadeTransition fadeTextField = Animations.fadeIn(adminUsername, 1, 1, Interpolator.EASE_IN);
        FadeTransition fadePasswordField = Animations.fadeIn(adminPassword, 1, 1, Interpolator.EASE_IN);
        FadeTransition fadeButton = Animations.fadeIn(loginButton, 1, 1, Interpolator.EASE_IN);

        ParallelTransition fadeInLoginArea = new ParallelTransition(fadeCredentialsLabel, fadeTextField, fadePasswordField, fadeButton);
        fadeInLoginArea.setDelay(Duration.seconds(titleDelay + 0.55)); // TO AVOID TITLE VISUALLY OVERLAPPING WITH LOGIN AREA
        fadeInLoginArea.play();

//        END ANIMATIONS

        EventHandler<KeyEvent> enterKey = keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                validateEmptyFields();
                loginButton.fire();
            }
        };

        adminUsername.setOnKeyPressed(enterKey);
        adminPassword.setOnKeyPressed(enterKey);

//        LISTENER CHE VERIFICANO SE L'UTENTE HA COMINCIATO AD INSERIRE USERNAME/PASSWORD DOPO AVER PROVATO A FARE LOGIN CON I FIELD VUOTI
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
            switchToScene("MainMenu.fxml");

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
	            alert.setHeaderText("Stai per superare il numero di tentativi");
	            alert.setContentText("Se non inserisci le credenziali corrette al prossimo tentativo, il programma terminerà.");
                alert.showAndWait();
            }

            if (trials > 3) {
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();
            }
        }
    }
}