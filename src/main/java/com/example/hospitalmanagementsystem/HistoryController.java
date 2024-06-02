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
import javafx.fxml.FXML;
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
    private DoctorRepository doctorRepository;


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
        Optional<Doctor> doctorOptional = doctorService.getDoctorByInn(doctorInn);


        Doctor doctor = doctorService.getDoctorByInn(doctorInn).orElseThrow(() -> new RuntimeException("Doc not found"));
        Patient patient = patientService.getPatientByInn(patientInn).orElseThrow(() -> new RuntimeException("Patient not found"));

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
}
