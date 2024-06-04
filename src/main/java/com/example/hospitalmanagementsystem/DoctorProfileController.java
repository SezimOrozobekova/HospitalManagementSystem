package com.example.hospitalmanagementsystem;

import com.example.hospitalmanagementsystem.config.ScreenLoader;
import com.example.hospitalmanagementsystem.entity.Doctor;
import com.example.hospitalmanagementsystem.service.DoctorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.Set;

@Controller
public class DoctorProfileController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField middleNameField;

    @FXML
    private DatePicker birthdatePicker;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField emergencyTelField;

    @FXML
    private ImageView doctorImage;

    @FXML
    private TextField emailField;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ScreenLoader screenLoader;

    @Autowired
    private Validator validator;


    private Long doctorInn;

    private String photoUrl;

    @FXML
    private void handleSignOutButtonAction() {
        screenLoader.loadMainScreen();
    }

    @FXML
    private void handleUpdateButtonAction() {
        Optional<Doctor> doctorOptional = doctorService.getDoctorByInn(doctorInn);
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            doctor.setFirstName(firstNameField.getText());
            doctor.setLastName(lastNameField.getText());
            doctor.setSecondName(middleNameField.getText());
            doctor.setDateBirthday(birthdatePicker.getValue().atStartOfDay());
            doctor.setAddress(addressField.getText());
            doctor.setPhoneNumber(phoneNumberField.getText());
            doctor.setEmergencyContact(emergencyTelField.getText());
            doctor.setEmail(emailField.getText());

            Set<ConstraintViolation<Doctor>> violations = validator.validate(doctor);

            if (!violations.isEmpty()) {
                StringBuilder errorMessage = new StringBuilder();
                for (ConstraintViolation<Doctor> violation : violations) {
                    errorMessage.append(violation.getMessage()).append("\n");
                }
                showAlert(Alert.AlertType.ERROR, "Validation Errors", errorMessage.toString());
            } else {
                doctorService.updateDoctor(doctor);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Update Information");
                alert.setHeaderText(null);
                alert.setContentText("Информация о докторе успешно обновлена");
                alert.showAndWait();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Update Failed", "Doctor not found for INN: " + doctorInn);
        }
    }

    @FXML
    private void onChangeImageButton(){
        chooseImage();
        Optional<Doctor> doctorOptional = doctorService.getDoctorByInn(doctorInn);
        if(doctorOptional.isPresent()){
            Doctor doctor = doctorOptional.get();
            doctor.setPhotoUrl(photoUrl);
            doctorService.updateDoctor(doctor);
        }
    }

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
                doctorImage.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Ошибка при загрузке фото", "Фото не загрузилось.");
            }
        }
    }



    private void showAlert(Alert.AlertType error, String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void onButtonBack(ActionEvent event){
        screenLoader.loadDoctorMainScreen(doctorInn);
    }

    private void customInitialize(Long doctorInn) {
        this.doctorInn = doctorInn;
        try {
            Optional<Doctor> doctorOptional = doctorService.getDoctorByInn(doctorInn);
            if (doctorOptional.isPresent()) {
                Doctor doctor = doctorOptional.get();
                String imagePath = doctor.getPhotoUrl();
                Image image = loadImage(imagePath);
                doctorImage.setImage(image);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Doctor not found for INN: " + doctorInn);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Image Load Error", "Failed to load the doctor's image.");
        }

    }

    private Image loadImage(String imagePath) throws IOException {
        String defaultImage = "/photo.jpg";
        InputStream inputStream = getClass().getResourceAsStream("/" + imagePath);
        if (inputStream == null) {
            inputStream = getClass().getResourceAsStream(defaultImage);
            if (inputStream == null) {
                throw new FileNotFoundException("Default image file not found: " + defaultImage);
            }
        }
        return new Image(inputStream);
    }





    public void setDoctorInn(Long doctorInn) {
        this.doctorInn = doctorInn;
        Optional<Doctor> doctorOptional = doctorService.getDoctorByInn(doctorInn);

        customInitialize(doctorInn);
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            firstNameField.setText(doctor.getFirstName());
            lastNameField.setText(doctor.getLastName());
            middleNameField.setText(doctor.getSecondName());
            birthdatePicker.setValue(doctor.getDateBirthday().toLocalDate());
            addressField.setText(doctor.getAddress());
            phoneNumberField.setText(doctor.getPhoneNumber());
            emergencyTelField.setText(doctor.getEmergencyContact());
            emailField.setText(doctor.getEmail());

            System.out.println("Set items in medicalHistoryTable");
        } else {
            System.out.println("Patient not found for INN: " + doctorInn);
        }
    }


}
