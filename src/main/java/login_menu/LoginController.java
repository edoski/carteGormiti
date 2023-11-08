package login_menu;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.*;
import com.azure.core.credential.KeyCredential;
import com.azure.core.models.ResponseError;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    TextField loginTextField;
    @FXML
    Button loginBtn;
    @FXML
    Label aiLabel;
    @FXML
    ImageView IV;

    OpenAIClient client;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = new OpenAIClientBuilder()
                .credential(new KeyCredential("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"))
                .buildClient();
    }

    public void addPlayer() throws IOException {
        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage(
                ChatRole.SYSTEM,
                "You are a helpful assistant. Your answers are to be succinct, with no additional text except for a brief description of the prompt I entered for the painting I'm generating."
        ));
        chatMessages.add(new ChatMessage(ChatRole.USER, loginTextField.getText()));

        ChatCompletions chatCompletions = client.getChatCompletions("gpt-3.5-turbo-1106",
                new ChatCompletionsOptions(chatMessages));

        for (ChatChoice choice : chatCompletions.getChoices()) {
            ChatMessage message = choice.getMessage();
            aiLabel.setText(message.getContent());
        }

        ImageGenerationOptions imageGenerationOptions = new ImageGenerationOptions(loginTextField.getText());
        ImageResponse images = client.getImages(imageGenerationOptions);


        for (ImageLocation imageLocation : images.getData()) {
            ResponseError error = imageLocation.getError();
            if (error != null) {
                System.out.printf("Image generation operation failed. Error code: %s, error message: %s.%n",
                        error.getCode(), error.getMessage());
            } else {
                String url = imageLocation.getUrl();

                // Download image bytes
                URL imageUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                byte[] imageBytes = conn.getInputStream().readAllBytes();

                // Save to temp file
                File tempFile = File.createTempFile("image", ".png");
                Files.write(tempFile.toPath(), imageBytes);

                // Load Image
                Image image = new Image(tempFile.toURI().toString());
                IV.setImage(image);
            }
        }

    }
}
