package com.example.hospitalmanagementsystem;

import com.example.hospitalmanagementsystem.entity.Doctor;
import com.example.hospitalmanagementsystem.service.DoctorService;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class HelloController {

    @FXML
    private TableView<Doctor> doctorTableView;

    @FXML
    private TableColumn<Doctor, String> nameColumn;

    @FXML
    private TableColumn<Doctor, String> specialtyColumn;

    private final DoctorService doctorService;

    @Autowired
    public HelloController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(cellData -> {
            Doctor doctor = cellData.getValue();
            return new ReadOnlyStringWrapper(doctor.getFirstName() + " " + doctor.getLastName());
        });

        specialtyColumn.setCellValueFactory(cellData -> {
            Doctor doctor = cellData.getValue();
            return new ReadOnlyStringWrapper(doctor.getSpecialty().name());
        });

        loadDoctorData();
    }

    private void loadDoctorData() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        ObservableList<Doctor> observableList = FXCollections.observableArrayList(doctors);
        doctorTableView.setItems(observableList);
    }
}
