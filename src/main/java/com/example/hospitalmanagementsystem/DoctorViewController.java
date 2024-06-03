package com.example.hospitalmanagementsystem;

import com.example.hospitalmanagementsystem.config.ScreenLoader;
import com.example.hospitalmanagementsystem.entity.Doctor;
import com.example.hospitalmanagementsystem.entity.Specialty;
import com.example.hospitalmanagementsystem.service.DoctorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class DoctorViewController implements Initializable {

    @FXML
    private GridPane doctorGridPane;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ScreenLoader screenLoader;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void loadDoctors(Specialty specialty) {
        List<Doctor> doctors = doctorService.getDoctorsBySpecialty(specialty);

        int col = 0;
        int row = 0;
        int maxColumns = 3;
        int numRows = (int) Math.ceil((double) doctors.size() / maxColumns);

        for (Doctor doctor : doctors) {
            AnchorPane anchorPane = createDoctorAnchorPane(doctor);

            // Add the AnchorPane to the GridPane
            doctorGridPane.add(anchorPane, col, row);

            col++;
            if (col == maxColumns) {
                col = 0;
                row++;
            }
        }


    }

    @FXML
    private void onButtonBack(ActionEvent event){
        screenLoader.loadAdministratorScreen();
    }

    @FXML
    private void onButtonSignOut(ActionEvent event){
        screenLoader.loadMainScreen();
    }


    private AnchorPane createDoctorAnchorPane(Doctor doctor) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color: rgb(255,255,255); " +
                "-fx-border-color: #ffffff; " +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 10px;");

        // Apply drop shadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.GRAY);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        anchorPane.setEffect(dropShadow);


        ImageView imageView = new ImageView();
        Image image = null;
        InputStream inputStream = null;
        String defaultImage = "/photo.jpg";

        try {
            inputStream = getClass().getResourceAsStream("/" + doctor.getPhotoUrl());
            if (inputStream == null) {
                inputStream = getClass().getResourceAsStream(defaultImage);
                if (inputStream == null) {
                    throw new FileNotFoundException("Default image file not found: " + defaultImage);
                }
            }
            image = new Image(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Image Load Error", "Failed to load the doctor's image.");
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        imageView.setImage(image);
        imageView.setFitWidth(66);
        imageView.setFitHeight(70);
        AnchorPane.setTopAnchor(imageView, 5.0);
        AnchorPane.setLeftAnchor(imageView, 5.0);

        Label nameLabel = new Label(doctor.getFirstName());
        AnchorPane.setTopAnchor(nameLabel, 5.0);
        AnchorPane.setLeftAnchor(nameLabel, 100.0);
        nameLabel.setWrapText(true); // Enable text wrapping

        Label surnameLabel = new Label(doctor.getLastName());
        AnchorPane.setTopAnchor(surnameLabel, 30.0);
        AnchorPane.setLeftAnchor(surnameLabel, 100.0);
        surnameLabel.setWrapText(true); // Enable text wrapping

        Label innLabel = new Label("" + doctor.getInn());
        AnchorPane.setTopAnchor(innLabel, 55.0);
        AnchorPane.setLeftAnchor(innLabel, 100.0);

        Label telephoneLabel = new Label(doctor.getPhoneNumber());
        AnchorPane.setTopAnchor(telephoneLabel, 80.0);
        AnchorPane.setLeftAnchor(telephoneLabel, 100.0);
        telephoneLabel.setWrapText(true); // Enable text wrapping

        anchorPane.getChildren().addAll(imageView, nameLabel, surnameLabel, innLabel, telephoneLabel);
        anchorPane.setOnMouseClicked(event -> screenLoader.loadDoctorInfoForAdminScreen(doctor));
        return anchorPane;
    }

    private void showAlert(Alert.AlertType alertType, String imageLoadError, String s) {
    }

    private Specialty specialty;
    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
        loadDoctors(specialty);
    }
}
