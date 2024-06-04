package com.example.hospitalmanagementsystem;


import com.example.hospitalmanagementsystem.config.ScreenLoader;
import com.example.hospitalmanagementsystem.entity.Doctor;
import com.example.hospitalmanagementsystem.entity.MedicalHistory;
import com.example.hospitalmanagementsystem.entity.Patient;
import com.example.hospitalmanagementsystem.repository.DoctorRepository;
import com.example.hospitalmanagementsystem.repository.MedicalHistoryRepository;
import com.example.hospitalmanagementsystem.service.DoctorService;
import com.example.hospitalmanagementsystem.service.MedicalHistoryService;
import com.example.hospitalmanagementsystem.service.PatientService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class HistoryController {
    @FXML
    private TextArea complaintsField;

    @FXML
    private TextArea diagnosisField;

    private Long patientInn;
    private Long doctorInn;

    @Autowired
    private MedicalHistoryService medicalHistoryService;

    @Autowired
    private ScreenLoader screenLoader;


    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    public void setPatientInn(Long patientInn) {
        this.patientInn = patientInn;
        System.out.println("Setting patient INN for medical history: " + patientInn);
    }

    @FXML
    private void handleConfirmButton() {
        String complaint = complaintsField.getText();
        String diagnosis = diagnosisField.getText();
        if(complaint.isBlank() || diagnosis.isBlank()){
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Заполните все поля");
            return;
        }

        Doctor doctor = doctorService.getDoctorByInn(doctorInn).orElseThrow(() -> new RuntimeException("Доктор не найден"));
        Patient patient = patientService.getPatientByInn(patientInn).orElseThrow(() -> new RuntimeException("Пациент не найден"));

        MedicalHistory medicalHistory = MedicalHistory.builder()
                .patient(patient)
                .doctor(doctor)
                .complaint(complaint)
                .diagnosis(diagnosis)
                .data(LocalDateTime.now())
                .build();

        medicalHistoryService.createMedicalHistory(medicalHistory);
        screenLoader.loadPatientInfoScreen(patientInn, doctorInn);
    }

    public void setPatientAndDoctorInn(Long patientInn, Long doctorInn) {
        this.patientInn = patientInn;
        this.doctorInn = doctorInn;
    }

    public void onButtonBack(Event event){
        screenLoader.loadPatientInfoScreen(patientInn, doctorInn);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
