package atc.gui;

import atc.model.*;
import atc.exceptions.DuplicateFlightIDException;
import atc.exceptions.HoldingPatternFullException;
import atc.util.FlightType;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardController {
    private FlightRegistry registry;
    private LandingHistory history;
    private PriorityCalculator calculator;
    
    private TableView<Aircraft> registryTable;
    private TableView<Aircraft> priorityTable;
    private TableView<Aircraft> holdingTable;
    private Label statusLabel;
    private Label statsLabel;
    
    private ObservableList<Aircraft> registryData;
    private ObservableList<Aircraft> priorityData;
    private ObservableList<Aircraft> holdingData;

    public DashboardController() {
        this.registry = new FlightRegistry();
        this.history = new LandingHistory();
        this.calculator = new PriorityCalculator();
        this.registryData = FXCollections.observableArrayList();
        this.priorityData = FXCollections.observableArrayList();
        this.holdingData = FXCollections.observableArrayList();
    }

    public VBox createDashboard() {
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20px; -fx-background-color: #f0f0f0;");
        
        Label title = new Label("ATC Priority Landing Simulator");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        statusLabel = new Label("System Ready");
        statsLabel = new Label("Registered: 0 | Queue: 0 | Holding: 0");
        
        // Create input form
        GridPane form = createFlightForm();
        
        // Create tables
        registryTable = createRegistryTable();
        priorityTable = createPriorityTable();
        holdingTable = createHoldingTable();
        
        // Create buttons
        Button processButton = new Button("Process Landing");
        processButton.setOnAction(e -> processLanding());
        
        Button reportButton = new Button("Generate Report");
        reportButton.setOnAction(e -> generateReport());
        
        root.getChildren().addAll(title, statusLabel, statsLabel, form, registryTable, 
                                  new Label("Priority Queue"), priorityTable,
                                  new Label("Holding Pattern"), holdingTable,
                                  processButton, reportButton);
        
        updateStats();
        return root;
    }

    private GridPane createFlightForm() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        
        TextField idField = new TextField();
        TextField originField = new TextField();
        TextField destField = new TextField();
        Spinner<Integer> fuelSpinner = new Spinner<>(0, 100, 50);
        ComboBox<FlightType> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll(FlightType.values());
        typeCombo.setValue(FlightType.COMMERCIAL);
        
        Button registerButton = new Button("Register Flight");
        registerButton.setOnAction(e -> {
            try {
                String id = idField.getText().toUpperCase();
                int fuel = fuelSpinner.getValue();
                FlightType type = typeCombo.getValue();
                
                Aircraft aircraft = null;
                switch (type) {
                    case COMMERCIAL:
                        aircraft = new CommercialFlight(id, originField.getText(), 
                                destField.getText(), LocalDateTime.now().plusHours(2), fuel, false);
                        break;
                    case CARGO:
                        aircraft = new CargoFlight(id, originField.getText(), 
                                destField.getText(), LocalDateTime.now().plusHours(2), fuel, false);
                        break;
                    case EMERGENCY:
                        aircraft = new EmergencyFlight(id, originField.getText(), 
                                destField.getText(), LocalDateTime.now().plusMinutes(30), fuel);
                        break;
                }
                
                registry.registerFlight(aircraft);
                statusLabel.setText("Flight " + id + " registered successfully");
                updateTables();
                idField.clear();
                originField.clear();
                destField.clear();
            } catch (DuplicateFlightIDException ex) {
                statusLabel.setText("Error: " + ex.getMessage());
            } catch (Exception ex) {
                statusLabel.setText("Error: " + ex.getMessage());
            }
        });
        
        grid.add(new Label("Flight ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Origin:"), 0, 1);
        grid.add(originField, 1, 1);
        grid.add(new Label("Destination:"), 0, 2);
        grid.add(destField, 1, 2);
        grid.add(new Label("Fuel %:"), 0, 3);
        grid.add(fuelSpinner, 1, 3);
        grid.add(new Label("Type:"), 0, 4);
        grid.add(typeCombo, 1, 4);
        grid.add(registerButton, 1, 5);
        
        return grid;
    }

    private TableView<Aircraft> createRegistryTable() {
        TableView<Aircraft> table = new TableView<>();
        
        TableColumn<Aircraft, String> idCol = new TableColumn<>("Flight ID");
        idCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFlightId()));
        
        TableColumn<Aircraft, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getType().toString()));
        
        TableColumn<Aircraft, String> fuelCol = new TableColumn<>("Fuel %");
        fuelCol.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getFuelLevel())));
        
        TableColumn<Aircraft, String> priorityCol = new TableColumn<>("Priority");
        priorityCol.setCellValueFactory(cell -> new SimpleStringProperty(
                String.valueOf(cell.getValue().getPriorityScore(calculator))));
        
        table.getColumns().addAll(idCol, typeCol, fuelCol, priorityCol);
        table.setItems(registryData);
        table.setPrefHeight(150);
        
        return table;
    }

    private TableView<Aircraft> createPriorityTable() {
        TableView<Aircraft> table = new TableView<>();
        
        TableColumn<Aircraft, String> idCol = new TableColumn<>("Flight ID");
        idCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFlightId()));
        
        TableColumn<Aircraft, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getType().toString()));
        
        TableColumn<Aircraft, String> priorityCol = new TableColumn<>("Priority");
        priorityCol.setCellValueFactory(cell -> new SimpleStringProperty(
                String.valueOf(cell.getValue().getPriorityScore(calculator))));
        
        table.getColumns().addAll(idCol, typeCol, priorityCol);
        table.setItems(priorityData);
        table.setPrefHeight(150);
        
        return table;
    }

    private TableView<Aircraft> createHoldingTable() {
        TableView<Aircraft> table = new TableView<>();
        
        TableColumn<Aircraft, String> idCol = new TableColumn<>("Flight ID");
        idCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFlightId()));
        
        TableColumn<Aircraft, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getType().toString()));
        
        TableColumn<Aircraft, String> fuelCol = new TableColumn<>("Fuel %");
        fuelCol.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getFuelLevel())));
        
        table.getColumns().addAll(idCol, typeCol, fuelCol);
        table.setItems(holdingData);
        table.setPrefHeight(150);
        
        return table;
    }

    private void processLanding() {
        Aircraft landed = registry.processLanding();
        if (landed != null) {
            history.recordLanding(landed);
            statusLabel.setText("Landed: " + landed.getFlightId());
        } else {
            statusLabel.setText("No aircraft ready for landing");
        }
        updateTables();
    }

    private void generateReport() {
        history.printReport();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Landing Report");
        alert.setHeaderText("Statistics Generated");
        alert.setContentText("Check console for detailed report");
        alert.show();
    }

    private void updateTables() {
        registryData.setAll(registry.getAllFlights());
        // Note: Actual priority queue state would require getter methods
        // This is simplified for demonstration
        updateStats();
    }

    private void updateStats() {
        statsLabel.setText(String.format("Registered: %d | Queue: %d | Holding: %d", 
                registry.getRegisteredCount(), 
                registry.getQueueCount(), 
                registry.getHoldingPatternCount()));
    }
}
