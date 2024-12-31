package server.projectfinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the Dashboard FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/server/projectfinal/Views/login-view.fxml"));
            Parent root = loader.load();

            // Set the title of the window
            primaryStage.setTitle("Admin Dashboard");

            // Set the scene with the loaded FXML
            primaryStage.setScene(new Scene(root, 400, 500));

            // Show the primary stage
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Optionally, you can show an alert to the user
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
