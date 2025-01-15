package server.projectfinal.Controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Aicontroller {

    @FXML
    private TextField promptField;

    @FXML
    private TextArea responseArea;

    @FXML
    private Button sendButton;

    @FXML
    void sendPrompt() {
        String prompt = promptField.getText();

        if (prompt.isBlank()) {
            showAlert("Error", "Prompt field cannot be empty.");
            return;
        }

        try {
            // Create HTTP client
            HttpClient client = HttpClient.newHttpClient();

            // Create JSON payload
            JsonObject json = new JsonObject();
            json.addProperty("prompt", prompt);
            String requestBody = new Gson().toJson(json);

            // Map selected role to its corresponding endpoint
            String endpointUrl = "http://127.0.0.1:5000/professor";

            // Create POST request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpointUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // Send request and get response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Parse the JSON response
                JsonObject jsonResponse = new Gson().fromJson(response.body(), JsonObject.class);
                String generatedResponse = jsonResponse.get("response").getAsString();

                // Update the response area with the generated response
                responseArea.setText(generatedResponse);
            } else {
                showAlert("Error", "Server responded with status code: " + response.statusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred: " + e.getMessage());
        }

    }



    @FXML
    public void initialize() {
        sendButton.setOnAction(event -> sendPrompt());
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
