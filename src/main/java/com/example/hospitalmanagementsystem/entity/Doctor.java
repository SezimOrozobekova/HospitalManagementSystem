package com.example.hospitalmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "INN cannot be null")
    private Long inn;

    @NotBlank(message =  "Password is required")
    private String password;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name must be less than or equal to 100 characters")
    private String lastName;

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name must be less than or equal to 100 characters")
    private String firstName;

    @Size(max = 100, message = "Second name must be less than or equal to 100 characters")
    private String secondName;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotNull(message = "Date of birth cannot be null")
    @PastOrPresent
    private LocalDateTime dateBirthday;

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address must be less than or equal to 255 characters")
    private String address;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Emergency contact is required")
    private String emergencyContact;


    @Enumerated(EnumType.STRING)
    @NotNull(message = "Specialty is required")
    private Specialty specialty;

    private String photoUrl;

    @Email
    private String email;

    private LocalDateTime employmentStartDate;


    private String experience;

    @NotBlank(message = "Medical degree is required")
    private String medicalDegree;

    @NotBlank(message = "Education is required")
    private String education;

    private double salary;

    @Builder.Default
    private Boolean active = Boolean.TRUE;



    @OneToMany(mappedBy = "doctor")
    private List<MedicalHistory> medicalHistories;



}
