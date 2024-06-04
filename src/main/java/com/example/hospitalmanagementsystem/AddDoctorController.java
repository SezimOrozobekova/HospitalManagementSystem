package com.example.hospitalmanagementsystem;

import com.example.hospitalmanagementsystem.config.ScreenLoader;
import com.example.hospitalmanagementsystem.entity.Doctor;
import com.example.hospitalmanagementsystem.entity.Specialty;
import com.example.hospitalmanagementsystem.service.DoctorService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Set;

@Controller
public class AddDoctorController {

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private TextField secondName;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField emergencyContactField;

    @FXML
    private TextField insuranceNumberField;

    @FXML
    private MenuButton specialtyMenuButton;

    @FXML
    private TextField emailField;

    @FXML
    private TextField educationField;

    @FXML
    private TextField salaryField;

    @FXML
    private ImageView photoImageView;

    @FXML
    private Button chooseImageButton;

    @FXML
    private ComboBox<String> genderComboBox;

    @FXML
    private TextField medicalDegreeField;

    @FXML
    private TextField innField;

    @FXML
    private TextField passwordField;

    @Autowired
    private ScreenLoader screenLoader;

    private String photoUrl;

    private final DoctorService doctorService;
    private final Validator validator;

    public AddDoctorController(DoctorService doctorService, Validator validator) {
        this.doctorService = doctorService;
        this.validator = validator;
    }

    @FXML
    private void onButtonBack(ActionEvent event) {
        screenLoader.loadAdministratorScreen();
    }

    @FXML
    public void initialize() {
        for (Specialty specialty : Specialty.values()) {
            MenuItem menuItem = new MenuItem(specialty.name());
            menuItem.setOnAction(event -> specialtyMenuButton.setText(menuItem.getText()));
            specialtyMenuButton.getItems().add(menuItem);
        }
        datePicker.setValue(LocalDate.now());
        genderComboBox.getItems().addAll("Мужской", "Женский");
        addValidation();
        chooseImageButton.setOnAction(event -> chooseImage());
    }

    private void addValidation() {
        name.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().length() > 100 ? null : change));
        surname.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().length() > 100 ? null : change));
        secondName.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().length() > 100 ? null : change));
        addressField.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().length() > 255 ? null : change));
        phoneNumberField.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches("\\d*") ? change : null));
        emergencyContactField.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches("\\d*") ? change : null));
        emailField.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().length() > 100 ? null : change));
        phoneNumberField.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches("\\d*") ? change : null));
        salaryField.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches("\\d*(\\.\\d*)?") ? change : null));
        innField.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches("\\d*") ? change : null));

    }

    @FXML
    private void handleSaveButton() {
        if (photoUrl == null) {
            showAlert(Alert.AlertType.ERROR, "Image Required", "Выберите фото перед сохранением");
            return;
        }

        String specialtyText = specialtyMenuButton.getText();
        Specialty specialty = null;
        try {
            specialty = Specialty.valueOf(specialtyText);
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Специальность не может быть пустым");
            return;
        }

        String genderText = genderComboBox.getValue();
        if (genderText == null || genderText.isBlank()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Гендер не может быть пустым");
            return;
        }
        if(innField.getText().isBlank()){
            showAlert(Alert.AlertType.ERROR, "Validation Error", "ИНН не может быть пустым");
            return;
        }
        String salaryText = salaryField.getText();
        if (salaryText == null || salaryText.isBlank()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Зарплата не может быть пустым");
            return;
        }

        double salary = Double.parseDouble(salaryText);
        Long inn = Long.valueOf(innField.getText());

        Doctor doctor = new Doctor();
        doctor.setFirstName(name.getText());
        doctor.setSecondName(secondName.getText());
        doctor.setLastName(surname.getText());
        doctor.setDateBirthday(datePicker.getValue().atStartOfDay());
        doctor.setAddress(addressField.getText());
        doctor.setPhoneNumber(phoneNumberField.getText());
        doctor.setEmergencyContact(emergencyContactField.getText());
        doctor.setGender(genderText);
        doctor.setSpecialty(specialty);
        doctor.setEmail(emailField.getText());
        doctor.setEducation(educationField.getText());
        doctor.setSalary(salary);
        doctor.setPhotoUrl(photoUrl);
        doctor.setMedicalDegree(medicalDegreeField.getText());
        doctor.setInn(inn);
        doctor.setPassword(passwordField.getText());

        Set<ConstraintViolation<Doctor>> violations = validator.validate(doctor);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ConstraintViolation<Doctor> violation : violations) {
                errorMessage.append(violation.getMessage()).append("\n");
            }
            showAlert(Alert.AlertType.ERROR, "Validation Errors", errorMessage.toString());
        } else {
            doctorService.createDoctor(doctor);
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Доктор сохранен", "Доктор был успешно добавлен");
        }
    }


    @FXML
    private void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            String originalFileName = selectedFile.getName();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

            String newFileName = "doctor" + System.currentTimeMillis() + fileExtension;
            File destinationDir = new File("src/main/resources/");
            if (!destinationDir.exists()) {
                destinationDir.mkdirs();
            }
            File destinationFile = new File(destinationDir, newFileName);

            try {
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                photoUrl = newFileName;
                Image image = new Image(destinationFile.toURI().toString());
                photoImageView.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Ошибка при загрузке фото", "Фото не загрузилось.");
            }
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        name.clear();
        surname.clear();
        secondName.clear();
        datePicker.setValue(LocalDate.now());
        addressField.clear();
        phoneNumberField.clear();
        emergencyContactField.clear();
        insuranceNumberField.clear();
        specialtyMenuButton.setText("Выберите специальность");
        emailField.clear();
        educationField.clear();
        salaryField.clear();
        genderComboBox.getSelectionModel().clearSelection();
        medicalDegreeField.clear();
        innField.clear();
        photoImageView.setImage(null);
        photoUrl = null;
    }
}
