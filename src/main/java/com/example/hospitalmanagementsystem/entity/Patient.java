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
    @PastOrPresent(message = "Date of birth cannot be in the future")
    private LocalDateTime dateBirthday;

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address must be less than or equal to 255 characters")
    private String address;

    @NotBlank(message = "Phone is required")
    private String phoneNumber;

    private String emergencyContact;

    @NotBlank(message = "Insurance number is required")
    private String insuranceNumber;

    @NotBlank(message = "Gender is required")
    private String gender;

    @OneToMany(mappedBy = "patient")
    private List<MedicalHistory> medicalHistories;

    @Builder.Default
    private Boolean active = Boolean.TRUE;

    public String getFio() {
        return firstName + " " + secondName + " " + lastName;
    }
}
