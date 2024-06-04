package com.example.hospitalmanagementsystem.service;

import com.example.hospitalmanagementsystem.entity.MedicalHistory;
import com.example.hospitalmanagementsystem.repository.MedicalHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalHistoryService {

    private final MedicalHistoryRepository medicalHistoryRepository;

    @Autowired
    public MedicalHistoryService(MedicalHistoryRepository medicalHistoryRepository) {
        this.medicalHistoryRepository = medicalHistoryRepository;
    }

    public List<MedicalHistory> getMedicalHistoriesByPatientId(Long patientId) {
        try {
            return medicalHistoryRepository.findByPatientId(patientId);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to retrieve medical histories for patient with ID " + patientId, ex);
        }
    }

    public void createMedicalHistory(MedicalHistory medicalHistory) {
        try {
            medicalHistoryRepository.save(medicalHistory);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to create medical history", ex);
        }
    }

}
