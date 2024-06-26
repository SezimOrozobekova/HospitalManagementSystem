package com.example.hospitalmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.lang.constant.Constable;
import java.time.LocalDateTime;

@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    private String complaint;

    private String diagnosis;

    private LocalDateTime data;


    public String getDoctorInn() {
        return doctor != null ? doctor.getInn() + " " : "";
    }


    public String getSpeciality() {
        return doctor != null ? "" + doctor.getSpecialty() : " ";
    }

}
