package com.example.hospitalmanagementsystem.repository;

import com.example.hospitalmanagementsystem.entity.Doctor;
import com.example.hospitalmanagementsystem.entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByInn(Long inn);
    List<Doctor> findAllBySpecialty(Specialty specialty);

    Optional<Doctor> findByInnAndPassword(Long inn, String password);



}
