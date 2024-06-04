package com.example.hospitalmanagementsystem.bootstrap;

import com.example.hospitalmanagementsystem.entity.*;
import com.example.hospitalmanagementsystem.repository.AdministratorRepository;
import com.example.hospitalmanagementsystem.repository.DoctorRepository;
import com.example.hospitalmanagementsystem.repository.MedicalHistoryRepository;
import com.example.hospitalmanagementsystem.repository.PatientRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class BootstrapData implements ApplicationRunner {
    private final AdministratorRepository administratorRepository;

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final MedicalHistoryRepository medicalHistoryRepository;

    private final Random random = new Random();

    public BootstrapData(DoctorRepository doctorRepository, PatientRepository patientRepository,
                         MedicalHistoryRepository medicalHistoryRepository, AdministratorRepository administratorRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.medicalHistoryRepository = medicalHistoryRepository;
        this.administratorRepository = administratorRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        addDoctorsWithSpecialties();
        loadPatients();
        writeMedicalHistory();
        loadAdministrators();
    }

    private void loadPatients() {
        if (patientRepository.count() == 0) {
            List<Patient> patients = Arrays.asList(
                    Patient.builder()
                            .inn(1111111111L)
                            .lastName("Мамбетов")
                            .firstName("Асыл")
                            .secondName("Акылбекович")
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
                            .secondName("Салимовна")
                            .gender("Female")
                            .dateBirthday(LocalDateTime.of(1985, 10, 10, 0, 0))
                            .address("321 Ахунбаева, Бишкек")
                            .phoneNumber("555-2345")
                            .emergencyContact("555-8765")
                            .insuranceNumber("ABC456")
                            .build(),
                    Patient.builder()
                            .inn(2222222223L)
                            .lastName("Асылжанов")
                            .firstName("Алишер")
                            .secondName("Каикович")
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
    public void addDoctorsWithSpecialties() {
        if(doctorRepository.count() == 0){
            Set<Integer> usedInns = new HashSet<>();
            List<Specialty> specialties = List.of(Specialty.values());

            List<Doctor> doctors = Arrays.asList(
                    Doctor.builder().inn(1234567890L).password("password").lastName("Абдымажитов").firstName("Асылбек").gender("Male").dateBirthday(LocalDateTime.of(1980, 5, 15, 0, 0)).address("123 Осмонова, Бишкек").phoneNumber("555-1234").emergencyContact("555-5678").specialty(Specialty.КАРДИОЛОГ).email("asyzbek.abdymazhitov@example.com").employmentStartDate(LocalDateTime.now().minusYears(5)).experience("10 лет").medicalDegree("MD").education("КГМА им. И. К. Ахунбаева").photoUrl("doctor (3).png").salary(100000.0).build(),
                    Doctor.builder().inn(1L).password("1").lastName("Орозобекова").firstName("Сезим").gender("Female").dateBirthday(LocalDateTime.of(2000, 5, 15, 0, 0)).address("123 Осмонова, Бишкек").phoneNumber("555-1234").emergencyContact("555-5678").specialty(Specialty.КАРДИОЛОГ).email("sezim.orozobekova@example.com").employmentStartDate(LocalDateTime.now().minusYears(5)).experience("10 лет").medicalDegree("MD").education("КГМА им. И. К. Ахунбаева").photoUrl("doctor (1).png").salary(100000.0).build(),
                    Doctor.builder().inn(9876543210L).password("password").lastName("Турсунбекова").firstName("Айжан").gender("Female").dateBirthday(LocalDateTime.of(1975, 8, 20, 0, 0)).address("456 Исанова, Бишкек").phoneNumber("555-5678").emergencyContact("555-1234").specialty(Specialty.КАРДИОЛОГ).email("aijan.tursunbekova@example.com").employmentStartDate(LocalDateTime.now().minusYears(3)).experience("7 лет").medicalDegree("MD").education("КГМА им. И. К. Ахунбаева").photoUrl("doctor (2).png").salary(90000.0).build(),
                    Doctor.builder().inn(1357924680L).password("password").lastName("Кадырова").firstName("Жазгуль").gender("Female").dateBirthday(LocalDateTime.of(1980, 3, 12, 0, 0)).address("789 Исанова, Бишкек").phoneNumber("555-2345").emergencyContact("555-8765").specialty(Specialty.КАРДИОЛОГ).email("zhazgul.kadyrova@example.com").employmentStartDate(LocalDateTime.now().minusYears(8)).experience("15 лет").medicalDegree("MD").education("КГМА им. И. К. Ахунбаева").photoUrl("doctor (5).png").salary(95000.0).build(),
                    Doctor.builder().inn(2468135790L).password("password").lastName("Мамбетов").firstName("Акылбек").gender("Female").dateBirthday(LocalDateTime.of(1975, 10, 5, 0, 0)).address("321 Ахунбаева, Бишкек").phoneNumber("555-8765").emergencyContact("555-5432").specialty(Specialty.КАРДИОЛОГ).email("asyl.mambetov@example.com").employmentStartDate(LocalDateTime.now().minusYears(6)).experience("12 лет").medicalDegree("MD").education("КГМА им. И. К. Ахунбаева").photoUrl("doctor (3).png").salary(105000.0).build(),
                    Doctor.builder().inn(3692581470L).password("password").lastName("Исаева").firstName("Лейла").gender("Female").dateBirthday(LocalDateTime.of(1982, 7, 18, 0, 0)).address("987 Чуй, Бишкек").phoneNumber("555-4321").emergencyContact("555-9876").specialty(Specialty.КАРДИОЛОГ).email("leila.isayeva@example.com").employmentStartDate(LocalDateTime.now().minusYears(4)).experience("9 лет").medicalDegree("MD").education("КГМА им. И. К. Ахунбаева").salary(98000.0).photoUrl("doctor (2).png").build(),
                    Doctor.builder().inn(1592637480L).password("password").lastName("Абдуллаев").firstName("Руслан").gender("Male").dateBirthday(LocalDateTime.of(1985, 12, 30, 0, 0)).address("654 Орозбекова, Бишкек").phoneNumber("555-6789").emergencyContact("555-3210").specialty(Specialty.КАРДИОЛОГ).email("ruslan.abdullaev@example.com").photoUrl("doctor (6).png").employmentStartDate(LocalDateTime.now().minusYears(2)).experience("5 лет").medicalDegree("MD").education("КГМА им. И. К. Ахунбаева").salary(92000.0).build(),
                    Doctor.builder().inn(7539514680L).password("password").lastName("Темиров").firstName("Алишер").gender("Male").photoUrl("doctor (4).png").dateBirthday(LocalDateTime.of(1987, 6, 22, 0, 0)).address("456 Жапарова, Бишкек").phoneNumber("555-7890").emergencyContact("555-4567").specialty(Specialty.КАРДИОЛОГ).email("alisher.temirov@example.com").employmentStartDate(LocalDateTime.now().minusYears(7)).experience("14 лет").medicalDegree("MD").education("КГМА им. И. К. Ахунбаева").salary(97000.0).build(),
                    Doctor.builder().inn(4561237890L).password("password").lastName("Сыдыкова").firstName("Айжан").gender("Female").dateBirthday(LocalDateTime.of(1978, 9, 14, 0, 0)).address("789 Орозбекова, Бишкек").phoneNumber("555-8901").emergencyContact("555-6543").specialty(Specialty.КАРДИОЛОГ).email("aijan.sydykova@example.com").employmentStartDate(LocalDateTime.now().minusYears(3)).experience("8 лет").medicalDegree("MD").education("КГМА им. И. К. Ахунбаева").salary(94000.0).photoUrl("doctor (1).png").build()
            );

            for (Specialty specialty : specialties) {
                List<Doctor> specialtyDoctors = doctors.stream()
                        .map(doctor -> {
                            int newInn = generateUniqueInn(usedInns);
                            return Doctor.builder()
                                    .inn((long) newInn)
                                    .password(doctor.getPassword())
                                    .lastName(doctor.getLastName())
                                    .firstName(doctor.getFirstName())
                                    .gender(doctor.getGender())
                                    .dateBirthday(doctor.getDateBirthday())
                                    .address(doctor.getAddress())
                                    .phoneNumber(doctor.getPhoneNumber())
                                    .emergencyContact(doctor.getEmergencyContact())
                                    .specialty(specialty)
                                    .email(doctor.getEmail())
                                    .employmentStartDate(doctor.getEmploymentStartDate())
                                    .experience(doctor.getExperience())
                                    .medicalDegree(doctor.getMedicalDegree())
                                    .education(doctor.getEducation())
                                    .photoUrl(doctor.getPhotoUrl())
                                    .salary(doctor.getSalary())
                                    .build();
                        })
                        .collect(Collectors .toList());
                doctorRepository.saveAll(specialtyDoctors);
            }
        }

    }

    private int generateUniqueInn(Set<Integer> usedInns) {
        int newInn;
        do {
            newInn = 100000 + random.nextInt(900000); // Генерация случайного inn от 100000 до 999999
        } while (!usedInns.add(newInn)); // Проверка на уникальность и добавление в коллекцию
        return newInn;
    }

    private void loadAdministrators() {
        if (administratorRepository.count() == 0) {
            List<Administrator> administrators = Arrays.asList(
                    Administrator.builder()
                            .id(1L)
                            .username("admin1")
                            .password("password1")
                            .build(),
                    Administrator.builder()
                            .id(2L)
                            .username("admin2")
                            .password("password2")
                            .build()
            );
            administratorRepository.saveAll(administrators);
        }
    }


    private void writeMedicalHistory() {
        Doctor doctor = doctorRepository.findAll().get(0);
        Patient patient = patientRepository.findAll().get(0);


        MedicalHistory medicalHistory = MedicalHistory.builder()
                .doctor(doctor)
                .patient(patient)
                .complaint("Headache and fever")
                .diagnosis("Migraine")
                .data(LocalDateTime.now())
                .build();

        medicalHistoryRepository.save(medicalHistory);
    }
}
