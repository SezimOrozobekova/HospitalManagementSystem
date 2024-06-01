package com.example.hospitalmanagementsystem.service;

import com.example.hospitalmanagementsystem.entity.Patient;
import com.example.hospitalmanagementsystem.exception.ResourceNotFoundException;
import com.example.hospitalmanagementsystem.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Optional<Patient> getPatientByInn(Long inn) {
        return Optional.ofNullable(patientRepository.findByInn(inn)
                .orElseThrow(() -> new ResourceNotFoundException("Patient with INN " + inn + " not found")));
    }

    public Patient createPatient(Patient patient) {
        try {
            return patientRepository.save(patient);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to create patient", ex);
        }
    }

    public List<Patient> getAllPatients() {
        try {
            return patientRepository.findAll();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to retrieve patients", ex);
        }
    }

    public Patient updatePatient(Long inn, Patient updatedPatient) {
        return patientRepository.findByInn(inn)
                .map(existingPatient -> {
                    updatedPatient.setId(existingPatient.getId());
                    return patientRepository.save(updatedPatient);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Patient with INN " + inn + " not found"));
    }

    public void deletePatient(Long inn) {
        patientRepository.findByInn(inn).ifPresentOrElse(
                patientRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("Patient with INN " + inn + " not found");
                });
    }
}
