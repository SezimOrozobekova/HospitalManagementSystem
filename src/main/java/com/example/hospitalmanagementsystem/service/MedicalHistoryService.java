package com.example.hospitalmanagementsystem.service;

import com.example.hospitalmanagementsystem.entity.MedicalHistory;
import com.example.hospitalmanagementsystem.exception.ResourceNotFoundException;
import com.example.hospitalmanagementsystem.repository.MedicalHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MedicalHistoryService {

    private final MedicalHistoryRepository medicalHistoryRepository;

    @Autowired
    public MedicalHistoryService(MedicalHistoryRepository medicalHistoryRepository) {
        this.medicalHistoryRepository = medicalHistoryRepository;
    }

    public List<MedicalHistory> getAllMedicalHistories() {
        try {
            return medicalHistoryRepository.findAll();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to retrieve medical histories", ex);
        }
    }

    public Optional<MedicalHistory> getMedicalHistoryById(Long id) {
        return Optional.ofNullable(medicalHistoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medical history with ID " + id + " not found")));
    }

    public List<MedicalHistory> getMedicalHistoriesByPatientId(Long patientId) {
        try {
            return medicalHistoryRepository.findByPatientId(patientId);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to retrieve medical histories for patient with ID " + patientId, ex);
        }
    }

    public List<MedicalHistory> getMedicalHistoriesByDoctorId(Long doctorId) {
        try {
            return medicalHistoryRepository.findByDoctorId(doctorId);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to retrieve medical histories for doctor with ID " + doctorId, ex);
        }
    }

    public MedicalHistory createMedicalHistory(MedicalHistory medicalHistory) {
        try {
            return medicalHistoryRepository.save(medicalHistory);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to create medical history", ex);
        }
    }

    public MedicalHistory updateMedicalHistory(Long id, MedicalHistory updatedMedicalHistory) {
        return medicalHistoryRepository.findById(id)
                .map(existingMedicalHistory -> {
                    updatedMedicalHistory.setId(existingMedicalHistory.getId());
                    return medicalHistoryRepository.save(updatedMedicalHistory);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Medical history with ID " + id + " not found"));
    }

    public void deleteMedicalHistory(Long id) {
        medicalHistoryRepository.findById(id).ifPresentOrElse(
                medicalHistoryRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("Medical history with ID " + id + " not found");
                });
    }
}
