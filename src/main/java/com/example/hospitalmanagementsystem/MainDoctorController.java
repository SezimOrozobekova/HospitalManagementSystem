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
import org.springframework.stereotype.Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
                showAlert(Alert.AlertType.ERROR, "Пациент не найден. ", "Пациент с ИНН " + inn + " не найден.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid INN", "Введите числовое значение.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Ошибка при поиске пациента: " + e.getMessage());
        }
    }






    private void customInitialize(Long doctorInn) {
        this.doctorInn = doctorInn;
        doctorImage.setOnMouseClicked(event -> handleDoctorImageClick());
        try {
            Optional<Doctor> doctorOptional = doctorService.getDoctorByInn(doctorInn);
            if (doctorOptional.isPresent()) {
                Doctor doctor = doctorOptional.get();
                Image image = loadImage(doctor.getPhotoUrl());
                doctorImage.setImage(image);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Доктор с: " + doctorInn + "не найден");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Image Load Error", "Фото не загрузилось.");
        }
    }
    private Image loadImage(String imagePath) throws IOException {
        String defaultImage = "/photo.jpg";
        InputStream inputStream = getClass().getResourceAsStream("/" + imagePath);
        if (inputStream == null) {
            inputStream = getClass().getResourceAsStream(defaultImage);
            if (inputStream == null) {
                throw new FileNotFoundException("Default image file not found: " + defaultImage);
            }
        }
        return new Image(inputStream);
    }
    private void handleDoctorImageClick() {
        screenLoader.loadDoctorInfoScreen(doctorInn);
    }

    private void showAlert(Alert.AlertType error, String title, String message) {
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
