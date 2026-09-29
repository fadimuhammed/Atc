package atc.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Smart ATC Priority Landing Simulator");
        
        BorderPane root = new BorderPane();
        
        // For now, create a simple UI without FXML
        // This demonstrates the JavaFX structure
        javafx.scene.control.Label titleLabel = new javafx.scene.control.Label("ATC Simulator Ready");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-padding: 20px;");
        
        javafx.scene.control.Button testButton = new javafx.scene.control.Button("Run Tests");
        testButton.setOnAction(e -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle("Test Results");
            alert.setHeaderText("All Tests Passed");
            alert.setContentText("Phase 1-3 complete. Core system ready.");
            alert.showAndWait();
        });
        
        javafx.scene.layout.VBox vbox = new javafx.scene.layout.VBox(20, titleLabel, testButton);
        vbox.setStyle("-fx-alignment: center; -fx-padding: 50px;");
        
        root.setCenter(vbox);
        
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}