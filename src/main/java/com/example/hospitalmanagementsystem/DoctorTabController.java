package com.example.hospitalmanagementsystem;

import com.example.hospitalmanagementsystem.entity.Specialty;
import com.example.hospitalmanagementsystem.service.DoctorService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DoctorTabController {

    @FXML
    private GridPane doctorGridPane;

    private final DoctorService doctorService;

    @Autowired
    public DoctorTabController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @FXML
    public void initialize() {
        loadDoctorSpecialties();
    }

    private void loadDoctorSpecialties() {
        Map<Specialty, Long> doctorCounts = doctorService.getDoctorCountBySpecialty();
        int row = 0;
        int col = 0;
        int maxColumns = 4; // Maximum number of columns before wrapping

        for (Map.Entry<Specialty, Long> entry : doctorCounts.entrySet()) {
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setStyle("-fx-background-color: #e6f7ff; -fx-padding: 10; -fx-border-color: #b3e0ff; -fx-border-width: 1;");

            Label specialtyLabel = new Label("Specialty: " + entry.getKey().name());
            specialtyLabel.setLayoutX(10);
            specialtyLabel.setLayoutY(10);

            Label quantityLabel = new Label("Quantity: " + entry.getValue());
            quantityLabel.setLayoutX(10);
            quantityLabel.setLayoutY(30);

            anchorPane.getChildren().addAll(specialtyLabel, quantityLabel);
            doctorGridPane.add(anchorPane, col, row);

            col++;
            if (col == maxColumns) {
                col = 0;
                row++;
            }
        }
    }
}
