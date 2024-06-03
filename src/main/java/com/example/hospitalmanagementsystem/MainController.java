package com.example.hospitalmanagementsystem;

import com.example.hospitalmanagementsystem.config.ScreenLoader;
import com.example.hospitalmanagementsystem.entity.Administrator;
import com.example.hospitalmanagementsystem.entity.Doctor;
import com.example.hospitalmanagementsystem.entity.Patient;
import com.example.hospitalmanagementsystem.service.AdministratorService;
import com.example.hospitalmanagementsystem.service.DoctorService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Optional;

@Controller
public class MainController {

    @FXML
    private TextField innField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ImageView logoImage;

    @Autowired
    private final DoctorService doctorService;
    private final AdministratorService administratorService;

    private Stage primaryStage;

    @Autowired
    private ScreenLoader screenLoader;

    @Autowired
    public MainController(DoctorService doctorService, AdministratorService administratorService, ScreenLoader screenLoader) {
        this.doctorService = doctorService;
        this.administratorService = administratorService;
        this.screenLoader = screenLoader;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.screenLoader.setStage(primaryStage);
    }


    @FXML
    private void handleLoginButtonAction() {
        String username = innField.getText();
        String password = passwordField.getText();

        try {


            Optional<Administrator> adminOptional = administratorService.getAdministratorByUsernameAndPassword(username, password);
            if (adminOptional.isPresent()) {
                showAlert("Success", "Logged in as Administrator.");
                screenLoader.loadAdministratorScreen();
                return;
            }

            Optional<Doctor> doctorOptional = doctorService.getDoctorByInnAndPassword(Long.valueOf(username), password);
            if (doctorOptional.isPresent()) {
                Doctor doc = doctorOptional.get();
                showAlert("Success", "Logged in as Doctor.");
                System.out.println(username);
                screenLoader.loadDoctorMainScreen(Long.valueOf(username));

                return;
            }


            showAlert("Error", "Invalid username or password.");
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void initialize() {
        // Load the image and set it to the ImageView
        try {
            Image image = new Image(new ClassPathResource("signinphoto.png").getInputStream());
            logoImage.setImage(image);
        } catch (IOException e) {
            showAlert("Error", "Failed to load image: singinphoto.png");
            e.printStackTrace();
        }
    }
}
