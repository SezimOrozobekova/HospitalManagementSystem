package com.example.hospitalmanagementsystem.repository;


import com.example.hospitalmanagementsystem.entity.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory,Long> {

    List<MedicalHistory> findByPatientId(Long patientId);
    List<MedicalHistory> findByDoctorId(Long doctorId);

}
