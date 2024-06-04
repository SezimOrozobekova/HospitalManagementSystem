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
                .orElseThrow(() -> new ResourceNotFoundException("Пациент с ИНН " + inn + " не найден")));
    }

    public void createPatient(Patient patient) {
        try {
            patientRepository.save(patient);
        } catch (Exception ex) {
            throw new RuntimeException("Пациент не создан", ex);
        }
    }

    public List<Patient> getAllPatients() {
        try {
            return patientRepository.findAllByActiveTrue();
        } catch (Exception ex) {
            throw new RuntimeException("Не удалось найти пациента", ex);
        }
    }
    public void updatePatient(Long inn, Patient updatedPatient) {
        patientRepository.findByInnAndActiveTrue(inn)
                .map(existingPatient -> {
                    updatedPatient.setId(existingPatient.getId());
                    return patientRepository.save(updatedPatient);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Пациент с ИНН " + inn + " не найден"));
    }


}
