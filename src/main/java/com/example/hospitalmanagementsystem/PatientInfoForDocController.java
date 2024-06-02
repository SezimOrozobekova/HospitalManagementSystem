package com.example.hospitalmanagementsystem;

import com.example.hospitalmanagementsystem.config.ScreenLoader;
import com.example.hospitalmanagementsystem.entity.MedicalHistory;
import com.example.hospitalmanagementsystem.entity.Patient;
import com.example.hospitalmanagementsystem.service.MedicalHistoryService;
import com.example.hospitalmanagementsystem.service.PatientService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


import java.time.LocalDate;
import java.util.List;
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


    @FXML
    private TableView<MedicalHistory> medicalHistoryTable;

    @FXML
    private TableColumn<MedicalHistory, LocalDate> dateColumn;

    @FXML
    private TableColumn<MedicalHistory, String> doctorColumn;

    @FXML
    private TableColumn<MedicalHistory, Long> doctorInnColumn;

    @FXML
    private TableColumn<MedicalHistory, String> complaintColumn;

    @FXML
    private TableColumn<MedicalHistory, String> diagnosisColumn;


    @Autowired
    private MedicalHistoryService medicalHistoryService;


    public void initialize() {

        firstNameField.setEditable(false);
        lastNameField.setEditable(false);
        middleNameField.setEditable(false);
        birthdatePicker.setEditable(false); // Необходимо установить false, если хотите, чтобы DatePicker был неизменяемым
        addressField.setEditable(false);
        phoneNumberField.setEditable(false);
        emergencyTelField.setEditable(false);
        insuranceNumberField.setEditable(false);


        dateColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        doctorColumn.setCellValueFactory(new PropertyValueFactory<>("speciality"));
        doctorInnColumn.setCellValueFactory(new PropertyValueFactory<>("doctorInn"));
        complaintColumn.setCellValueFactory(new PropertyValueFactory<>("complaint"));
        diagnosisColumn.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
    }

    private Long patientInn;
    private Long doctorInn;

    @Autowired
    private PatientService patientService;

    @Autowired
    private ScreenLoader screenLoader;

    @FXML
    private void handleAddMedHistory(ActionEvent event) {
        screenLoader.loadMedHistoryScreen(patientInn, doctorInn);
    }

    @FXML
    private void onButtonBack(ActionEvent event){
        screenLoader.loadDoctorMainScreen(doctorInn);
    }

    public void setPatientAndDoctorInn(Long patientInn, Long doctorInn) {
        this.patientInn = patientInn;
        this.doctorInn = doctorInn;
        System.out.println("Setting Patient INN: " + patientInn + ", Doctor INN: " + doctorInn);
        Optional<Patient> patientOptional = patientService.getPatientByInn(patientInn);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            firstNameField.setText(patient.getFirstName());
            lastNameField.setText(patient.getLastName());
            middleNameField.setText(patient.getSecondName());
            addressField.setText(patient.getAddress());
            phoneNumberField.setText(patient.getPhoneNumber());
            emergencyTelField.setText(patient.getEmergencyContact());
            insuranceNumberField.setText(patient.getInsuranceNumber());

            List<MedicalHistory> medicalHistories = medicalHistoryService.getMedicalHistoriesByPatientId(patient.getId());
            ObservableList<MedicalHistory> medicalHistoryData = FXCollections.observableArrayList(medicalHistories);
            medicalHistoryTable.setItems(medicalHistoryData);
            System.out.println("Set items in medicalHistoryTable");
        } else {
            System.out.println("Patient not found for INN: " + patientInn);
        }
    }

    public void setDoctorInn(Long doctorInn) {
        this.doctorInn = doctorInn;
    }


}
