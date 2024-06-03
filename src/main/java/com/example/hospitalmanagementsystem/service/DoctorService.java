package com.example.hospitalmanagementsystem.service;

import com.example.hospitalmanagementsystem.entity.Doctor;
import com.example.hospitalmanagementsystem.entity.Specialty;
import com.example.hospitalmanagementsystem.exception.ResourceNotFoundException;
import com.example.hospitalmanagementsystem.repository.DoctorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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

    public Optional<Doctor> getDoctorByInnAndPassword(Long inn, String password) {
        return doctorRepository.findByInnAndPassword(inn, password);


    }

    public List<Doctor> getDoctorsBySpecialty(Specialty specialty) {
        return doctorRepository.findAllBySpecialty(specialty);
    }

    public Doctor createDoctor(Doctor doctor) {
        try {
            return doctorRepository.save(doctor);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to create doctor", ex);
        }
    }



    public Map<Specialty, Long> getDoctorCountBySpecialty() {
        return doctorRepository.findAll().stream()
                .collect(Collectors.groupingBy(Doctor::getSpecialty, Collectors.counting()));
    }

    public Doctor updateDoctor(Doctor updatedDoctor) {
        return doctorRepository.findByInn(updatedDoctor.getInn())
                .map(existingDoctor -> {
                    updatedDoctor.setId(existingDoctor.getId());
                    return doctorRepository.save(updatedDoctor);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with INN " + updatedDoctor.getInn() + " not found"));
    }

    public void deleteDoctor(Long inn) {
        doctorRepository.findByInn(inn).ifPresentOrElse(
                doctorRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("Doctor with INN " + inn + " not found");
                });
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
}
