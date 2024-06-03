package com.example.hospitalmanagementsystem.repository;

import com.example.hospitalmanagementsystem.entity.Doctor;
import com.example.hospitalmanagementsystem.entity.Patient;
import com.example.hospitalmanagementsystem.entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByInnAndActiveTrue(Long inn);
    List<Doctor> findAllBySpecialtyAndActiveTrue(Specialty specialty);

    List<Doctor> findAllByActiveTrue();

    Optional<Doctor> findByInnAndPasswordAndActiveTrue(Long inn, String password);

    List<Doctor> findByActiveTrue();




}
