package com.example.hospitalmanagementsystem;

import com.example.hospitalmanagementsystem.config.ScreenLoader;
import com.example.hospitalmanagementsystem.entity.Patient;
import com.example.hospitalmanagementsystem.service.PatientService;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Controller
public class AddPatientController {

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

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private TextField innField;

    @Autowired
    private ScreenLoader screenLoader;

    private final PatientService patientService;

    private final Validator validator;

    @FXML
    public void initialize() {
        addValidation();
    }

    @Autowired
    public AddPatientController(PatientService patientService) {
        this.patientService = patientService;
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    private void addValidation() {
        firstNameField.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().length() > 100 ? null : change));
        lastNameField.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().length() > 100 ? null : change));
        middleNameField.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().length() > 100 ? null : change));
        addressField.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().length() > 255 ? null : change));
        phoneNumberField.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches("\\d*") ? change : null));
        emergencyTelField.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches("\\d*") ? change : null));
        innField.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches("\\d*") ? change : null));
    }

    @FXML
    public void handleAddPatient(ActionEvent event) {
        String innText = innField.getText();
        Long inn = null;
        if (!innText.isBlank()) {
            try {
                inn = Long.valueOf(innText);
            } catch (NumberFormatException e) {
                showAlert(AlertType.ERROR, "Validation Error", "INN must be a valid number.");
                return;
            }
        }
        Patient patient = new Patient();
        patient.setFirstName(firstNameField.getText());
        patient.setLastName(lastNameField.getText());
        patient.setSecondName(middleNameField.getText());
        patient.setAddress(addressField.getText());
        patient.setPhoneNumber(phoneNumberField.getText());
        patient.setEmergencyContact(emergencyTelField.getText());
        patient.setInsuranceNumber(insuranceNumberField.getText());
        patient.setGender(genderComboBox.getValue());
        patient.setInn(inn);

        LocalDate birthdate = birthdatePicker.getValue();
        if (birthdate != null) {
            patient.setDateBirthday(birthdate.atStartOfDay());
        }

        Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ConstraintViolation<Patient> violation : violations) {
                errorMessage.append(violation.getMessage()).append("\n");
            }
            showAlert(AlertType.ERROR, "Validation Error", errorMessage.toString());
        } else {
            patientService.createPatient(patient);
            clearFields();
            showAlert(AlertType.INFORMATION, "Patient Added", "Patient successfully added.");
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
        genderComboBox.getSelectionModel().clearSelection();
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void onBackButton(Event event) {
        screenLoader.loadAdministratorScreen();
    }
}
