package com.example.hospitalmanagementsystem;

import com.example.hospitalmanagementsystem.config.ScreenLoader;
import com.example.hospitalmanagementsystem.entity.Doctor;
import com.example.hospitalmanagementsystem.entity.Patient;
import com.example.hospitalmanagementsystem.entity.Specialty;
import com.example.hospitalmanagementsystem.service.DoctorService;
import com.example.hospitalmanagementsystem.service.PatientService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
public class DoctorTabController {

    @FXML
    private GridPane doctorGridPane;

    @FXML
    private TextField innTextField;

    private Stage stage;
    private final ApplicationContext applicationContext;
    private final DoctorService doctorService;

    @Autowired
    private ScreenLoader screenLoader;

    @FXML
    private TableView<Patient> patientTableView;

    @FXML
    private TableColumn<Patient, Long> innColumn;

    @FXML
    private TableColumn<Patient, String> fioColumn;

    @FXML
    private TableColumn<Patient, String> emergencyPhoneColumn;


    private final PatientService patientService;

    @Autowired
    public DoctorTabController(DoctorService doctorService, PatientService patientService, ApplicationContext applicationContext) {
        this.doctorService = doctorService;
        this.applicationContext = applicationContext;
        this.patientService = patientService;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleAddDoctorButton(ActionEvent event) {
        screenLoader.loadAddDoctorScreen();
    }

    @FXML
    private void onSignOutButton(ActionEvent event){
        screenLoader.loadMainScreen();
    }

    @FXML
    private void handleAddPatientButton(ActionEvent event) {
        screenLoader.loadAddPatientScreen();
    }

    @FXML
    private void handleFindPatientButton(ActionEvent event) {

        try {
            long inn = Long.parseLong(innTextField.getText().trim());
            Optional<Patient> patientOptional = patientService.getPatientByInn(inn);
            if (patientOptional.isPresent()) {
                Patient pat = patientOptional.get();
                screenLoader.loadPatientInfoAdminScreen(inn);
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid INN", "Пожалуйста введите числовое значение.");
        } catch (Exception e) {
            showAlert("Error", "Пациент не найден: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadPatientData() {
        ObservableList<Patient> patientList = FXCollections.observableArrayList(patientService.getAllPatients());
        patientTableView.setItems(patientList);
    }

    @FXML
    public void initialize() {
        loadDoctorSpecialties();
        innTextField.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches("\\d*") ? change : null));

        loadPatientData();
        innColumn.setCellValueFactory(new PropertyValueFactory<>("inn"));
        fioColumn.setCellValueFactory(new PropertyValueFactory<>("fio"));
        emergencyPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("emergencyContact"));

    }

    private void loadDoctorSpecialties() {
        Map<Specialty, Long> doctorCounts = doctorService.getDoctorCountBySpecialty();
        int row = 0;
        int col = 0;
        int maxColumns = 3; // Maximum number of columns before wrapping

        for (Map.Entry<Specialty, Long> entry : doctorCounts.entrySet()) {
            Specialty specialty = entry.getKey(); // Capture the current specialty
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

            anchorPane.setPadding(new Insets(15));

            Label specialtyLabel = new Label("Специальность: " + specialty.name());
            specialtyLabel.setLayoutX(10);
            specialtyLabel.setLayoutY(10);

            Label quantityLabel = new Label("Всего: " + entry.getValue());
            quantityLabel.setLayoutX(10);
            quantityLabel.setLayoutY(30);

            anchorPane.getChildren().addAll(specialtyLabel, quantityLabel);
            doctorGridPane.add(anchorPane, col, row);

            // Add click event handler to the anchor pane
            anchorPane.setOnMouseClicked(event -> {
                try {
                    loadDoctorBySpecialtyView(event, specialty);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            col++;
            if (col == maxColumns) {
                col = 0;
                row++;
            }
        }
    }

    private void loadDoctorBySpecialtyView(javafx.scene.input.MouseEvent event, Specialty specialty) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hospitalmanagementsystem/doctorbyspec.fxml"));
        loader.setControllerFactory(applicationContext::getBean);
        Parent root = loader.load();
        DoctorViewController controller = loader.getController();
        controller.setSpecialty(specialty);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
