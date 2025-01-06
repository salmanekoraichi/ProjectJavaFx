package server.projectfinal.Utils;

/**
 * This code is written by Salmane Koraichi
 **/
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class PopupNotification {

    public static void showError(String message) {
        // Create a new Stage (popup window)
        Stage popupStage = new Stage();
        popupStage.initStyle(StageStyle.TRANSPARENT); // Make it look like a popup
        popupStage.initModality(Modality.APPLICATION_MODAL); // Block input to other windows
        popupStage.setAlwaysOnTop(true);

        // Create a label for the message
        Label label = new Label(message);
        label.setStyle("-fx-background-color: #ffcccc; -fx-text-fill: #b00000; -fx-padding: 10px; " +
                "-fx-border-color: #b00000; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        // Layout for the popup
        StackPane layout = new StackPane(label);
        layout.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8); -fx-background-radius: 10px;");
        layout.setAlignment(Pos.CENTER);

        // Set the scene
        Scene scene = new Scene(layout, 300, 100);
        scene.setFill(null); // Transparent background
        popupStage.setScene(scene);

        // Position the popup (center of the screen or parent window)
        popupStage.setX((javafx.stage.Screen.getPrimary().getBounds().getWidth() - 300) / 2);
        popupStage.setY((javafx.stage.Screen.getPrimary().getBounds().getHeight() - 100) / 2);

        // Show the popup
        popupStage.show();

        // Use a Timeline to close the popup after 5 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), event -> popupStage.close()));
        timeline.setCycleCount(1);
        timeline.play();
    }

    public static void showSuccess(String message) {
        // Create a new Stage (popup window)
        Stage popupStage = new Stage();
        popupStage.initStyle(StageStyle.TRANSPARENT); // Make it look like a popup
        popupStage.initModality(Modality.APPLICATION_MODAL); // Block input to other windows
        popupStage.setAlwaysOnTop(true);

        // Create a label for the message
        Label label = new Label(message);
        label.setStyle("-fx-background-color: #ccffcc; -fx-text-fill: #006600; -fx-padding: 10px; " +
                "-fx-border-color: #006600; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        // Layout for the popup
        StackPane layout = new StackPane(label);
        layout.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8); -fx-background-radius: 10px;");
        layout.setAlignment(Pos.CENTER);

        // Set the scene
        Scene scene = new Scene(layout, 300, 100);
        scene.setFill(null); // Transparent background
        popupStage.setScene(scene);

        // Position the popup (center of the screen or parent window)
        popupStage.setX((javafx.stage.Screen.getPrimary().getBounds().getWidth() - 300) / 2);
        popupStage.setY((javafx.stage.Screen.getPrimary().getBounds().getHeight() - 100) / 2);

        // Show the popup
        popupStage.show();

        // Use a Timeline to close the popup after 5 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), event -> popupStage.close()));
        timeline.setCycleCount(1);
        timeline.play();
    }

    public static void showErrorAlert(String title, String message) {
        // Create a new Stage (popup window)
        Stage popupStage = new Stage();
        popupStage.initStyle(StageStyle.TRANSPARENT); // Transparent window style
        popupStage.initModality(Modality.APPLICATION_MODAL); // Block input to other windows
        popupStage.setAlwaysOnTop(true);

        // Create a label for the message
        Label label = new Label(message);
        label.setStyle("-fx-background-color: #ffcccc; -fx-text-fill: #990000; -fx-padding: 10px; " +
                "-fx-border-color: #990000; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        // Add a title label
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #990000; -fx-padding: 5px;");

        // Layout for the popup
        StackPane layout = new StackPane();
        layout.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8); -fx-background-radius: 10px;");
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(titleLabel, label);

        // Stack children to ensure title and message are separated
        StackPane.setAlignment(titleLabel, Pos.TOP_CENTER);
        StackPane.setAlignment(label, Pos.CENTER);

        // Set the scene
        Scene scene = new Scene(layout, 300, 120);
        scene.setFill(null); // Transparent background
        popupStage.setScene(scene);

        // Position the popup (center of the screen or parent window)
        popupStage.setX((javafx.stage.Screen.getPrimary().getBounds().getWidth() - 300) / 2);
        popupStage.setY((javafx.stage.Screen.getPrimary().getBounds().getHeight() - 120) / 2);

        // Show the popup
        popupStage.show();

        // Use a Timeline to close the popup after 5 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), event -> popupStage.close()));
        timeline.setCycleCount(1);
        timeline.play();
    }

}

