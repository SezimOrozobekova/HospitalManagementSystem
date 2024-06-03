package com.example.hospitalmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.*;



import java.time.LocalDateTime;
import java.util.List;

@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "INN cannot be null")
    private Long inn;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name must be less than or equal to 100 characters")
    private String lastName;

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name must be less than or equal to 100 characters")
    private String firstName;

    @Size(max = 100, message = "Second name must be less than or equal to 100 characters")
    private String secondName;

    @NotNull(message = "Date of birth cannot be null")
    @PastOrPresent
    private LocalDateTime dateBirthday;

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address must be less than or equal to 255 characters")
    private String address;

    private String phoneNumber;

    private String emergencyContact;

    @NotBlank(message = "Insurance number is required")
    private String insuranceNumber;

    @NotBlank(message = "Gender is required")
    private String gender;  // Assuming gender is a string like "Male" or "Female"

    @OneToMany(mappedBy = "patient")
    private List<MedicalHistory> medicalHistories;





}
