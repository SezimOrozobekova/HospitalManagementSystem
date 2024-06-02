package com.example.hospitalmanagementsystem;

import com.example.hospitalmanagementsystem.config.ScreenLoader;
import com.example.hospitalmanagementsystem.entity.Doctor;
import com.example.hospitalmanagementsystem.entity.Patient;
import com.example.hospitalmanagementsystem.service.DoctorService;
import com.example.hospitalmanagementsystem.service.PatientService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Optional;

@Controller
public class MainDoctorController {

    @Autowired
    private PatientService patientService;

    @FXML
    private TextField innField;

    @FXML
    private ImageView doctorImage;


    @Autowired
    private ScreenLoader screenLoader;

    @Autowired
    private DoctorService doctorService;


    private Long doctorInn;

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
                screenLoader.loadPatientInfoScreen(inn, doctorInn);
            } else {
                showAlert("Patient Not Found", "Patient with INN " + inn + " not found.");
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid INN", "Please enter a valid number for INN.");
        } catch (Exception e) {
            showAlert("Error", "Failed to search for patient: " + e.getMessage());
        }
    }

    @FXML
    private void onButtonBack(ActionEvent event){
        screenLoader.loadDoctorMainScreen(doctorInn);
    }





    private void customInitialize(Long doctorInn) {
        this.doctorInn = doctorInn;
        try {
            doctorImage.setOnMouseClicked(event -> handleDoctorImageClick());

            Doctor doctor = doctorService.getDoctorByInn(doctorInn).orElseThrow(() -> new RuntimeException("Doctor not found"));

            Image image = new Image(new ClassPathResource(doctor.getPhotoUrl()).getInputStream());
            doctorImage.setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void handleDoctorImageClick() {
        screenLoader.loadDoctorInfoScreen(doctorInn);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setDoctorInn(Long doctorInn) {
        this.doctorInn = doctorInn;
        customInitialize(doctorInn);
    }
}
