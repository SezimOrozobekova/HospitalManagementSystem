package com.example.hospitalmanagementsystem.bootstrap;

import com.example.hospitalmanagementsystem.entity.Doctor;
import com.example.hospitalmanagementsystem.entity.MedicalHistory;
import com.example.hospitalmanagementsystem.entity.Patient;
import com.example.hospitalmanagementsystem.entity.Specialty;
import com.example.hospitalmanagementsystem.repository.DoctorRepository;
import com.example.hospitalmanagementsystem.repository.MedicalHistoryRepository;
import com.example.hospitalmanagementsystem.repository.PatientRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class BootstrapData implements ApplicationRunner {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final MedicalHistoryRepository medicalHistoryRepository;

    public BootstrapData(DoctorRepository doctorRepository, PatientRepository patientRepository,
                         MedicalHistoryRepository medicalHistoryRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.medicalHistoryRepository = medicalHistoryRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        loadDoctorsAndPatients();
        writeMedicalHistory();
    }

    private void loadDoctorsAndPatients() {
        if (doctorRepository.count() == 0) {
            List<Doctor> doctors = Arrays.asList(
                    Doctor.builder()
                            .inn(1234567890L)
                            .lastName("Абдымажитов")
                            .firstName("Асылбек")
                            .gender("Male")
                            .dateBirthday(LocalDateTime.of(1980, 5, 15, 0, 0))
                            .address("123 Осмонова, Бишкек")
                            .phoneNumber("555-1234")
                            .emergencyContact("555-5678")
                            .specialty(Specialty.DENTIST)
                            .email("asyzbek.abdymazhitov@example.com")
                            .employmentStartDate(LocalDateTime.now().minusYears(5))
                            .experience("10 лет")
                            .medicalDegree("MD")
                            .education("КГМА им. И. К. Ахунбаева")
                            .salary(100000.0)
                            .build(),
                    Doctor.builder()
                            .inn(9876543210L)
                            .lastName("Турсунбекова")
                            .firstName("Айжан")
                            .gender("Female")
                            .dateBirthday(LocalDateTime.of(1975, 8, 20, 0, 0))
                            .address("456 Исанова, Бишкек")
                            .phoneNumber("555-5678")
                            .emergencyContact("555-1234")
                            .specialty(Specialty.SURGEON)
                            .email("aijan.tursunbekova@example.com")
                            .employmentStartDate(LocalDateTime.now().minusYears(3))
                            .experience("7 лет")
                            .medicalDegree("MD")
                            .education("КГМА им. И. К. Ахунбаева")
                            .salary(90000.0)
                            .build(),
                    Doctor.builder()
                            .inn(1357924680L)
                            .lastName("Кадырова")
                            .firstName("Жазгуль")
                            .gender("Female")
                            .dateBirthday(LocalDateTime.of(1980, 3, 12, 0, 0))
                            .address("789 Исанова, Бишкек")
                            .phoneNumber("555-2345")
                            .emergencyContact("555-8765")
                            .specialty(Specialty.PEDIATRICIAN)
                            .email("zhazgul.kadyrova@example.com")
                            .employmentStartDate(LocalDateTime.now().minusYears(8))
                            .experience("15 лет")
                            .medicalDegree("MD")
                            .education("КГМА им. И. К. Ахунбаева")
                            .salary(95000.0)
                            .build(),
                    Doctor.builder()
                            .inn(2468135790L)
                            .lastName("Мамбетов")
                            .firstName("Асыл")
                            .gender("Male")
                            .dateBirthday(LocalDateTime.of(1975, 10, 5, 0, 0))
                            .address("321 Ахунбаева, Бишкек")
                            .phoneNumber("555-8765")
                            .emergencyContact("555-5432")
                            .specialty(Specialty.NEUROLOGIST)
                            .email("asyl.mambetov@example.com")
                            .employmentStartDate(LocalDateTime.now().minusYears(6))
                            .experience("12 лет")
                            .medicalDegree("MD")
                            .education("КГМА им. И. К. Ахунбаева")
                            .salary(105000.0)
                            .build(),
                    Doctor.builder()
                            .inn(3692581470L)
                            .lastName("Исаева")
                            .firstName("Лейла")
                            .gender("Female")
                            .dateBirthday(LocalDateTime.of(1982, 7, 18, 0, 0))
                            .address("987 Чуй, Бишкек")
                            .phoneNumber("555-4321")
                            .emergencyContact("555-9876")
                            .specialty(Specialty.CARDIOLOGIST)
                            .email("leila.isayeva@example.com")
                            .employmentStartDate(LocalDateTime.now().minusYears(4))
                            .experience("9 лет")
                            .medicalDegree("MD")
                            .education("КГМА им. И. К. Ахунбаева")
                            .salary(98000.0)
                            .build(),
                    Doctor.builder()
                            .inn(1592637480L)
                            .lastName("Абдуллаев")
                            .firstName("Руслан")
                            .gender("Male")
                            .dateBirthday(LocalDateTime.of(1985, 12, 30, 0, 0))
                            .address("654 Орозбекова, Бишкек")
                            .phoneNumber("555-6789")
                            .emergencyContact("555-3210")
                            .specialty(Specialty.ONCOLOGIST)
                            .email("ruslan.abdullaev@example.com")
                            .employmentStartDate(LocalDateTime.now().minusYears(2))
                            .experience("5 лет")
                            .medicalDegree("MD")
                            .education("КГМА им. И. К. Ахунбаева")
                            .salary(92000.0)
                            .build(),
                    Doctor.builder()
                            .inn(7539514680L)
                            .lastName("Темиров")
                            .firstName("Алишер")
                            .gender("Male")
                            .dateBirthday(LocalDateTime.of(1987, 6, 22, 0, 0))
                            .address("456 Жапарова, Бишкек")
                            .phoneNumber("555-7890")
                            .emergencyContact("555-4567")
                            .specialty(Specialty.PSYCHIATRIST)
                            .email("alisher.temirov@example.com")
                            .employmentStartDate(LocalDateTime.now().minusYears(7))
                            .experience("14 лет")
                            .medicalDegree("MD")
                            .education("КГМА им. И. К. Ахунбаева")
                            .salary(97000.0)
                            .build(),
                    Doctor.builder()
                            .inn(4561237890L)
                            .lastName("Сыдыкова")
                            .firstName("Айжан")
                            .gender("Female")
                            .dateBirthday(LocalDateTime.of(1978, 9, 14, 0, 0))
                            .address("789 Орозбекова, Бишкек")
                            .phoneNumber("555-8901")
                            .emergencyContact("555-6543")
                            .specialty(Specialty.PEDIATRICIAN)
                            .email("aijan.sydykova@example.com")
                            .employmentStartDate(LocalDateTime.now().minusYears(3))
                            .experience("8 лет")
                            .medicalDegree("MD")
                            .education("КГМА им. И. К. Ахунбаева")
                            .salary(94000.0)
                            .build()
            );
            doctorRepository.saveAll(doctors);
        }

        if (patientRepository.count() == 0) {
            List<Patient> patients = Arrays.asList(
                    Patient.builder()
                            .inn(1111111111L)
                            .lastName("Мамбетов")
                            .firstName("Асыл")
                            .gender("Male")
                            .dateBirthday(LocalDateTime.of(1990, 3, 25, 0, 0))
                            .address("789 Чуй, Бишкек")
                            .phoneNumber("555-9876")
                            .emergencyContact("555-5432")
                            .insuranceNumber("XYZ123")
                            .build(),
                    Patient.builder()
                            .inn(2222222222L)
                            .lastName("Кадырова")
                            .firstName("Жазгуль")
                            .gender("Female")
                            .dateBirthday(LocalDateTime.of(1985, 10, 10, 0, 0))
                            .address("321 Ахунбаева, Бишкек")
                            .phoneNumber("555-2345")
                            .emergencyContact("555-8765")
                            .insuranceNumber("ABC456")
                            .build()
            );
            patientRepository.saveAll(patients);
        }
    }

    private void writeMedicalHistory() {
        // Get the first doctor and the first patient
        Doctor doctor = doctorRepository.findAll().get(0);
        Patient patient = patientRepository.findAll().get(0);

        // Create a medical history entry
        MedicalHistory medicalHistory = MedicalHistory.builder()
                .doctor(doctor)
                .patient(patient)
                .complaint("Headache and fever")
                .diagnosis("Migraine")
                .data(LocalDateTime.now())
                .build();

        // Save the medical history
        medicalHistoryRepository.save(medicalHistory);
    }
}
