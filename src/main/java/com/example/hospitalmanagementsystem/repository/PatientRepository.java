package com.example.hospitalmanagementsystem.repository;

import com.example.hospitalmanagementsystem.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByInn(Long inn);

    Optional<Patient> findByInnAndActiveTrue(Long inn); // Only active patients
    List<Patient> findAllByActiveTrue();
}
