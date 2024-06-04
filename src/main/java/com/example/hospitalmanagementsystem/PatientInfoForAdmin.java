package com.example.hospitalmanagementsystem;

import com.example.hospitalmanagementsystem.config.ScreenLoader;
import com.example.hospitalmanagementsystem.entity.Doctor;
import com.example.hospitalmanagementsystem.entity.Patient;
import com.example.hospitalmanagementsystem.service.PatientService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Component
public class PatientInfoForAdmin {

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
    private TextField insuranceNumberField;

    private Stage stage;
    private Long patientInn;
    private final PatientService patientService;

    @Autowired
    private ScreenLoader screenLoader;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    public void initialize() {
        // Initialize genderComboBox with values
        genderComboBox.getItems().addAll("Мужской", "Женский");
    }

    @Autowired
    public PatientInfoForAdmin(PatientService patientService, Validator validator) {
        this.patientService = patientService;
        this.validator = validator;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setPatientInn(Long patientInn) {
        this.patientInn = patientInn;
        loadPatientDetails(patientInn);
    }

    private void loadPatientDetails(Long inn) {
        patientService.getPatientByInn(inn).ifPresent(patient -> {
            firstNameField.setText(patient.getFirstName());
            lastNameField.setText(patient.getLastName());
            middleNameField.setText(patient.getSecondName());
            birthdatePicker.setValue(patient.getDateBirthday().toLocalDate());
            addressField.setText(patient.getAddress());
            phoneNumberField.setText(patient.getPhoneNumber());
            emergencyTelField.setText(patient.getEmergencyContact());
            insuranceNumberField.setText(patient.getInsuranceNumber());
            genderComboBox.setValue(patient.getGender());

        });
    }

    @FXML
    private void onButtonBack(ActionEvent event) {
        screenLoader.loadAdministratorScreen();

    }

    private final Validator validator;


    @FXML
    private void handleUpdateButton(ActionEvent event) throws IOException, SAXException {
        if (patientInn != null) {
            Optional<Patient> patientOptional = patientService.getPatientByInn(patientInn);
            if (patientOptional.isPresent()) {
                Patient patient = patientOptional.get();
                patient.setFirstName(firstNameField.getText());
                patient.setLastName(lastNameField.getText());
                patient.setSecondName(middleNameField.getText());
                patient.setDateBirthday(LocalDateTime.of(birthdatePicker.getValue(), LocalDateTime.now().toLocalTime()));
                patient.setAddress(addressField.getText());
                patient.setPhoneNumber(phoneNumberField.getText());
                patient.setEmergencyContact(emergencyTelField.getText());
                patient.setInsuranceNumber(insuranceNumberField.getText());
//                patient.setInn(Long.parseLong(innField.getText()));
                patient.setGender(genderComboBox.getValue());

                Set<ConstraintViolation<Patient>> violations = validator.validate(patient);

                if (!violations.isEmpty()) {
                    StringBuilder errorMessage = new StringBuilder();
                    for (ConstraintViolation<Patient> violation : violations) {
                        errorMessage.append(violation.getMessage()).append("\n");
                    }
                    showAlert("Validation Errors", errorMessage.toString());
                } else {
                    patientService.updatePatient(patientInn, patient);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Update Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Информация о пациенте успешно обновлена!");
                    alert.showAndWait();
                }
            } else {
                showAlert("Update Failed", "Patient not found for INN: " + patientInn);
            }
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    private void handleDeleteButton(ActionEvent event) {
        Optional<Patient> patientOptional = patientService.getPatientByInn(patientInn);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            patient.setActive(Boolean.FALSE);
            patientService.updatePatient(patient.getInn(), patient);
            showAlert("Doctor info deleted", "Успешно удалено");
            screenLoader.loadAdministratorScreen();
        }

    }

    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        middleNameField.clear();
        birthdatePicker.setValue(null);
        addressField.clear();
        phoneNumberField.clear();
        emergencyTelField.clear();
        insuranceNumberField.clear();
    }
}
