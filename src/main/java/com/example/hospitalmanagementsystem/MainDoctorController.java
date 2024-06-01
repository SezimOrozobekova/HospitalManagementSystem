package com.example.hospitalmanagementsystem;

import com.example.hospitalmanagementsystem.config.ScreenLoader;
import com.example.hospitalmanagementsystem.entity.Patient;
import com.example.hospitalmanagementsystem.service.PatientService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class MainDoctorController {

    @Autowired
    private PatientService patientService;

    @FXML
    private TextField innField;

    @Autowired
    private ScreenLoader screenLoader;

    private Stage stage;

    public void setPrimaryStage(Stage primaryStage, ScreenLoader screenLoader) {
        this.screenLoader = screenLoader;
        this.stage = primaryStage;
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        try {
            Long inn = Long.parseLong(innField.getText());
            Optional<Patient> patientOptional = patientService.getPatientByInn(inn);
            if (patientOptional.isPresent()) {
                // Load patient info screen and pass the INN
                screenLoader.loadPatientInfoScreen(inn);

            } else {
                showAlert("Patient Not Found", "Patient with INN " + inn + " not found.");
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid INN", "Please enter a valid number for INN.");
        } catch (Exception e) {
            showAlert("Error", "Failed to search for patient: " + e.getMessage());
        }
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
