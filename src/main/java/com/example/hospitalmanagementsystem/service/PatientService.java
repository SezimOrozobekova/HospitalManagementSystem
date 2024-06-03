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
        return Optional.ofNullable(patientRepository.findByInnAndActiveTrue(inn)
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
            return patientRepository.findAllByActiveTrue();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to retrieve patients", ex);
        }
    }
    public Patient updatePatient(Long inn, Patient updatedPatient) {
        return patientRepository.findByInnAndActiveTrue(inn)
                .map(existingPatient -> {
                    updatedPatient.setId(existingPatient.getId());
                    return patientRepository.save(updatedPatient);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Patient with INN " + inn + " not found"));
    }


    public void deletePatient(Long inn) {
        patientRepository.findByInnAndActiveTrue(inn).ifPresentOrElse(
                patient -> {
                    patient.setActive(Boolean.FALSE);
                    patientRepository.save(patient);
                },
                () -> {
                    throw new ResourceNotFoundException("Patient with INN " + inn + " not found");
                });
    }
}
