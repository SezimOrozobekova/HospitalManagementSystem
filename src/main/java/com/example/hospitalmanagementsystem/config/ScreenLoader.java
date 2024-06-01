package com.example.hospitalmanagementsystem.config;

import com.example.hospitalmanagementsystem.PatientInfoForDocController;
import com.example.hospitalmanagementsystem.service.AdministratorService;
import com.example.hospitalmanagementsystem.service.DoctorService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ScreenLoader {

    private Stage stage;
    private final ApplicationContext applicationContext;
    private final DoctorService doctorService;
    private final AdministratorService administratorService;

    @Autowired
    public ScreenLoader(DoctorService doctorService, AdministratorService administratorService, ApplicationContext applicationContext) {
        this.doctorService = doctorService;
        this.administratorService = administratorService;
        this.applicationContext = applicationContext;
    }



    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void loadMainScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hospitalmanagementsystem/main.fxml"));
            loader.setControllerFactory(applicationContext::getBean);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Hospital Management System");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPatientInfoScreen(Long inn) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hospitalmanagementsystem/patientinfo.fxml"));
            loader.setControllerFactory(applicationContext::getBean);
            Parent root = loader.load();

            // Access the controller and set the INN
            PatientInfoForDocController controller = loader.getController();
            controller.setPatientInn(inn);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Hospital Management System - Patient Information");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadDoctorMainScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hospitalmanagementsystem/maindoctor.fxml"));
            loader.setControllerFactory(applicationContext::getBean);
            Parent root = loader.load();
            Stage patientStage = new Stage();
            patientStage.setScene(new Scene(root));
            patientStage.setTitle("Patient Dashboard");
            patientStage.show();

            // Close the current login window (optional, depending on your requirements)
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadAdministratorScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hospitalmanagementsystem/doctorinfo.fxml"));
            loader.setControllerFactory(applicationContext::getBean);
            Parent root = loader.load();
            Stage adminStage = new Stage();
            adminStage.setScene(new Scene(root));
            adminStage.setTitle("Administrator Dashboard");
            adminStage.show();

            // Close the current login window (optional, depending on your requirements)
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}