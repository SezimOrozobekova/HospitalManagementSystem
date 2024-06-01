package com.example.hospitalmanagementsystem;

import com.example.hospitalmanagementsystem.entity.Patient;
import com.example.hospitalmanagementsystem.service.PatientService;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class PatientInfoForDocController {

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

    public void initialize() {

        firstNameField.setEditable(false);
        lastNameField.setEditable(false);
        middleNameField.setEditable(false);
        birthdatePicker.setEditable(false); // Необходимо установить false, если хотите, чтобы DatePicker был неизменяемым
        addressField.setEditable(false);
        phoneNumberField.setEditable(false);
        emergencyTelField.setEditable(false);
        insuranceNumberField.setEditable(false);
    }

    private Long patientInn;

    @Autowired
    private PatientService patientService;

    public void setPatientInn(Long inn) {
        this.patientInn = inn;
        System.out.println("Setting INN: " + inn);
        Optional<Patient> patientOptional = patientService.getPatientByInn(patientInn);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            firstNameField.setText(patient.getFirstName());
            lastNameField.setText(patient.getLastName());
            middleNameField.setText(patient.getSecondName());
            birthdatePicker.setValue(LocalDate.from(patient.getDateBirthday()));
            addressField.setText(patient.getAddress());
            phoneNumberField.setText(patient.getPhoneNumber());
            emergencyTelField.setText(patient.getEmergencyContact());
            insuranceNumberField.setText(patient.getInsuranceNumber());
        } else {
            System.out.println("Patient not found for INN: " + patientInn);
        }
    }



    // You can add more methods here to interact with the UI elements
}
