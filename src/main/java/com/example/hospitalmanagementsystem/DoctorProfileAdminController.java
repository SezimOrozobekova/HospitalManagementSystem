package com.example.hospitalmanagementsystem;

import com.example.hospitalmanagementsystem.config.ScreenLoader;
import com.example.hospitalmanagementsystem.entity.Doctor;
import com.example.hospitalmanagementsystem.entity.Specialty;
import com.example.hospitalmanagementsystem.service.DoctorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Controller
public class DoctorProfileAdminController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField middleNameField;

    @FXML
    private DatePicker birthdatePicker;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField emergencyTelField;

    @FXML
    private ImageView doctorImage;

    @FXML
    private TextField emailField;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ScreenLoader screenLoader;

    @Autowired
    private Validator validator;

    private Long doctorInn;

    @FXML
    private void handleSignOutButtonAction() {
        screenLoader.loadMainScreen();
    }

    @FXML
    private void handleUpdateButtonAction() {
        Optional<Doctor> doctorOptional = doctorService.getDoctorByInn(doctorInn);
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            doctor.setFirstName(firstNameField.getText());
            doctor.setLastName(lastNameField.getText());
            doctor.setSecondName(middleNameField.getText());
            doctor.setDateBirthday(birthdatePicker.getValue().atStartOfDay());
            doctor.setAddress(addressField.getText());
            doctor.setPhoneNumber(phoneNumberField.getText());
            doctor.setEmergencyContact(emergencyTelField.getText());
            doctor.setEmail(emailField.getText());

            Set<ConstraintViolation<Doctor>> violations = validator.validate(doctor);

            if (!violations.isEmpty()) {
                StringBuilder errorMessage = new StringBuilder();
                for (ConstraintViolation<Doctor> violation : violations) {
                    errorMessage.append(violation.getMessage()).append("\n");
                }
                showAlert("Validation Errors", errorMessage.toString());
            } else {
                doctorService.updateDoctor(doctor);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Update Information");
                alert.setHeaderText(null);
                alert.setContentText("Информация о докторе успешно сохранена");
                alert.showAndWait();
            }
        } else {
            showAlert("Update Failed", "Доктор с ИНН: " + doctorInn + " не найден в базе");
        }
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void onButtonBack(ActionEvent event) throws IOException {
        Optional<Doctor> doctorOptional = doctorService.getDoctorByInn(doctorInn);
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            Specialty specialty = doctor.getSpecialty();
            screenLoader.loadDoctorBySpecialty(specialty);

        }

    }
    @FXML
    private void onDeleteButton(ActionEvent event){
        Optional<Doctor> doctorOptional = doctorService.getDoctorByInn(doctorInn);
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            doctor.setActive(Boolean.FALSE);
            doctorService.updateDoctor(doctor);
            showAlert("Doctor info deleted", "Успешно удалено");
            screenLoader.loadAdministratorScreen();
        }

    }
    private void customInitialize(Long doctorInn) {
        this.doctorInn = doctorInn;
        try {
            Optional<Doctor> doctorOptional = doctorService.getDoctorByInn(doctorInn);
            if (doctorOptional.isPresent()) {
                Doctor doctor = doctorOptional.get();
                String imagePath = doctor.getPhotoUrl();

                Image image = loadImage(imagePath);
                doctorImage.setImage(image);
            } else {
                showAlert("Error", "Доктор с ИНН: " + doctorInn + " не найден в базе данных");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Image Load Error", "Failed to load the doctor's image.");
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





    public void setDoctorInn(Long doctorInn) {
        this.doctorInn = doctorInn;
        Optional<Doctor> doctorOptional = doctorService.getDoctorByInn(doctorInn);

        customInitialize(doctorInn);
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            firstNameField.setText(doctor.getFirstName());
            lastNameField.setText(doctor.getLastName());
            middleNameField.setText(doctor.getSecondName());
            birthdatePicker.setValue(doctor.getDateBirthday().toLocalDate());
            addressField.setText(doctor.getAddress());
            phoneNumberField.setText(doctor.getPhoneNumber());
            emergencyTelField.setText(doctor.getEmergencyContact());
            emailField.setText(doctor.getEmail());

        } else {
        }
    }
}
