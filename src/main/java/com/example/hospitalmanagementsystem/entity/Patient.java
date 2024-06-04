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

    @NotNull(message = "ИНН обязательно")
    private Long inn;

    @NotBlank(message = "Фамилия обязательно")
    @Size(max = 100, message = "Фамилия должна быть меньше 100 символов")
    private String lastName;

    @NotBlank(message = "Имя обязательно")
    @Size(max = 100, message = "Имя должно быть меньше 100 символов")
    private String firstName;

    @Size(max = 100, message = "Отчество должно быть меньше 100 символов")
    private String secondName;

    @NotBlank(message = "Гендер обязательно")
    private String gender;

    @NotNull(message = "Дата рождения обязательно")
    @PastOrPresent
    private LocalDateTime dateBirthday;

    @NotBlank(message = "Адрес обязательно")
    @Size(max = 255, message = "Адрес должен быть меньше 255 символов")
    private String address;

    @NotBlank(message = "Номер телефона обязательно")
    private String phoneNumber;

    @NotBlank(message = "Резервный контакт обязательно")
    private String emergencyContact;

    @NotBlank(message = "Номер страхование обязательно")
    private String insuranceNumber;


    @OneToMany(mappedBy = "patient")
    private List<MedicalHistory> medicalHistories;

    @Builder.Default
    private Boolean active = Boolean.TRUE;

    public String getFio() {
        return firstName + " " + secondName + " " + lastName;
    }
}
