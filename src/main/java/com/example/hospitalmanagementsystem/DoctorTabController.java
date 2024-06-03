package com.example.hospitalmanagementsystem;

import com.example.hospitalmanagementsystem.config.ScreenLoader;
import com.example.hospitalmanagementsystem.entity.Specialty;
import com.example.hospitalmanagementsystem.service.AdministratorService;
import com.example.hospitalmanagementsystem.service.DoctorService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Component
public class DoctorTabController {

    @FXML
    private GridPane doctorGridPane;

    @FXML
    private TextField innTextField;

    private Stage stage;
    private final ApplicationContext applicationContext;
    private final DoctorService doctorService;

    @Autowired
    private ScreenLoader screenLoader;

    @Autowired
    public DoctorTabController(DoctorService doctorService, ApplicationContext applicationContext) {
        this.doctorService = doctorService;
        this.applicationContext = applicationContext;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleAddDoctorButton(ActionEvent event) {
        screenLoader.loadAddDoctorScreen();
    }

    @FXML
    private void onSignOutButton(ActionEvent event){
        screenLoader.loadMainScreen();
    }

    @FXML
    private void handleAddPatientButton(ActionEvent event) {
        screenLoader.loadAddPatientScreen();
    }

    @FXML
    private void handleFindPatientButton(ActionEvent event) {
        try {
            long inn = Long.parseLong(innTextField.getText().trim());
            screenLoader.loadPatientInfoAdminScreen(inn);
        } catch (NumberFormatException e) {
            // Handle if input is not a valid number
            System.out.println("INN must be a valid number.");
            // Optionally, you can show an alert or message to the user
        }
    }

    @FXML
    public void initialize() {
        loadDoctorSpecialties();
        innTextField.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches("\\d*") ? change : null));

    }

    private void loadDoctorSpecialties() {
        Map<Specialty, Long> doctorCounts = doctorService.getDoctorCountBySpecialty();
        int row = 0;
        int col = 0;
        int maxColumns = 4; // Maximum number of columns before wrapping

        for (Map.Entry<Specialty, Long> entry : doctorCounts.entrySet()) {
            Specialty specialty = entry.getKey(); // Capture the current specialty
            AnchorPane anchorPane = new AnchorPane();

            anchorPane.setStyle("-fx-background-color: rgb(255,255,255); " +
                    "-fx-border-color: #ffffff; " +
                    "-fx-border-width: 1px; " +
                    "-fx-border-radius: 10px;");

            // Apply drop shadow effect
            DropShadow dropShadow = new DropShadow();
            dropShadow.setColor(Color.GRAY);
            dropShadow.setOffsetX(2);
            dropShadow.setOffsetY(2);
            anchorPane.setEffect(dropShadow);

            anchorPane.setPadding(new Insets(15));

            Label specialtyLabel = new Label("Specialty: " + specialty.name());
            specialtyLabel.setLayoutX(10);
            specialtyLabel.setLayoutY(10);

            Label quantityLabel = new Label("Quantity: " + entry.getValue());
            quantityLabel.setLayoutX(10);
            quantityLabel.setLayoutY(30);

            anchorPane.getChildren().addAll(specialtyLabel, quantityLabel);
            doctorGridPane.add(anchorPane, col, row);

            // Add click event handler to the anchor pane
            anchorPane.setOnMouseClicked(event -> {
                try {
                    loadDoctorBySpecialtyView(event, specialty);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            col++;
            if (col == maxColumns) {
                col = 0;
                row++;
            }
        }
    }

    private void loadDoctorBySpecialtyView(javafx.scene.input.MouseEvent event, Specialty specialty) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hospitalmanagementsystem/doctorbyspec.fxml"));
        loader.setControllerFactory(applicationContext::getBean);
        Parent root = loader.load();

        // Get the controller and pass the specialty
        DoctorViewController controller = loader.getController();
        controller.setSpecialty(specialty);

        // Get the current stage from the event source
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene to the current stage
        stage.setScene(new Scene(root));
        stage.show();
    }
}
