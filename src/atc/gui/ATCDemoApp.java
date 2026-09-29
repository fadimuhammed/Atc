package atc.gui;

import atc.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ATCDemoApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ATC Priority Landing Simulator - Demo");
        
        DashboardController controller = new DashboardController();
        VBox dashboard = controller.createDashboard();
        
        Scene scene = new Scene(dashboard, 1000, 800);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Pre-load sample flights for demo
        preloadSampleFlights(controller);
    }
    
    private void preloadSampleFlights(DashboardController controller) {
        // This would integrate with the controller's registry
        System.out.println("ATC Demo Ready - Sample flights can be loaded via UI");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
