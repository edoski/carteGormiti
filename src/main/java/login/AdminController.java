package login;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

    private final String username = "admin";
    private final String password = "onward";
    private int trials = 0;

    Parent root;
    Stage stage;
    Scene scene;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        START ANIMATIONS
//        FADE & SCALE IN TITOLO ADMIN, FROM CENTER

        FadeTransition fadeTitle = fadeIn(adminLabel, 1, 1, Interpolator.EASE_IN);
        ScaleTransition scaleTitleIn = scale(adminLabel, 0.8, 0.8, 1, Interpolator.EASE_BOTH);

        ParallelTransition adminTitleIn = new ParallelTransition(fadeTitle, scaleTitleIn);
        adminTitleIn.play();

//        MOVE UP & SCALE OUT TITOLO ADMIN, FINAL POSITION

        ScaleTransition scaleTitleOut = scale(adminLabel, -0.6, -0.6, 1, Interpolator.EASE_BOTH);
        TranslateTransition moveTitle = translateY(adminLabel, -120, 1, Interpolator.EASE_BOTH);

        TranslateTransition moveLine = translateY(line, -125, 1, Interpolator.EASE_BOTH);
        FadeTransition fadeLine = fadeIn(line, 1, 1, Interpolator.EASE_IN);

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

        FadeTransition fadeCredentialsLabel = fadeIn(checkLoginLabel, 1, 1, Interpolator.EASE_IN);
        FadeTransition fadeTextField = fadeIn(adminUsername, 1, 1, Interpolator.EASE_IN);
        FadeTransition fadePasswordField = fadeIn(adminPassword, 1, 1, Interpolator.EASE_IN);
        FadeTransition fadeButton = fadeIn(loginButton, 1, 1, Interpolator.EASE_IN);

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

    //    ANIMATION UTILITY METHODS
    public FadeTransition fadeIn(Node node, double changeByValue, double seconds, Interpolator interpolator) {
        FadeTransition fade = new FadeTransition(Duration.seconds(seconds), node);
        fade.setByValue(changeByValue);
        fade.setInterpolator(interpolator);
        return fade;
    }

    public ScaleTransition scale(Node node, double changeX, double changeY, double seconds, Interpolator interpolator) {
        ScaleTransition scale = new ScaleTransition(Duration.seconds(seconds), node);
        scale.setByX(changeX);
        scale.setByY(changeY);
        scale.setInterpolator(interpolator);
        return scale;
    }

    public TranslateTransition translateY(Node node, double changeY, double seconds, Interpolator interpolator) {
        TranslateTransition translateY = new TranslateTransition(Duration.seconds(seconds), node);
        translateY.setByY(changeY);
        translateY.setInterpolator(interpolator);
        return translateY;
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
