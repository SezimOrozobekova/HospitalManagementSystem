package com.example.hospitalmanagementsystem;

import com.example.hospitalmanagementsystem.entity.Doctor;
import com.example.hospitalmanagementsystem.service.DoctorService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class DoctorViewController implements Initializable {

    @FXML
    private GridPane doctorGridPane;

    @Autowired
    private DoctorService doctorService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDoctors();
    }

    private void loadDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();

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
        imageView.setImage(new Image(doctor.getPhotoUrl()));
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

        return anchorPane;
    }

}
