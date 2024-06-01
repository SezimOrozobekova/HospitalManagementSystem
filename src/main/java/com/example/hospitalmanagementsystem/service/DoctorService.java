package com.example.hospitalmanagementsystem.service;

import com.example.hospitalmanagementsystem.entity.Doctor;
import com.example.hospitalmanagementsystem.entity.Specialty;
import com.example.hospitalmanagementsystem.exception.ResourceNotFoundException;
import com.example.hospitalmanagementsystem.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Optional<Doctor> getDoctorByInn(Long inn) {
        return Optional.ofNullable(doctorRepository.findByInn(inn)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with INN " + inn + " not found")));
    }

    public Doctor createDoctor(Doctor doctor) {
        try {
            return doctorRepository.save(doctor);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to create doctor", ex);
        }
    }

    public List<Doctor> getDoctorsBySpecialty(Specialty specialty) {
        return doctorRepository.findAllBySpecialty(specialty);
    }

    public Doctor updateDoctor(Long inn, Doctor updatedDoctor) {
        return doctorRepository.findByInn(inn)
                .map(existingDoctor -> {
                    updatedDoctor.setId(existingDoctor.getId());
                    return doctorRepository.save(updatedDoctor);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with INN " + inn + " not found"));
    }

    public void deleteDoctor(Long inn) {
        doctorRepository.findByInn(inn).ifPresentOrElse(
                doctorRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("Doctor with INN " + inn + " not found");
                });
    }
}
