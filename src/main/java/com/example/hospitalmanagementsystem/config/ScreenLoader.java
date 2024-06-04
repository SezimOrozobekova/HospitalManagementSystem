package com.example.hospitalmanagementsystem.config;

import com.example.hospitalmanagementsystem.*;
import com.example.hospitalmanagementsystem.entity.Doctor;
import com.example.hospitalmanagementsystem.entity.Specialty;
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


    @Autowired
    public ScreenLoader(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void loadMainScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hospitalmanagementsystem/main" +
                    ".fxml"));
            loader.setControllerFactory(applicationContext::getBean);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Hospital Management System");
            newStage.show();
            stage.close();
            stage = newStage;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void loadDoctorInfoScreen(Long doctorInn) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hospitalmanagementsystem/doctorinfo.fxml"));
            loader.setControllerFactory(applicationContext::getBean);
            Parent root = loader.load();

            DoctorProfileController controller = loader.getController();
            controller.setDoctorInn(doctorInn);
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Hospital Management System - Doctor Information");
            newStage.show();
            stage.close();
            stage = newStage;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadDoctorInfoForAdminScreen(Doctor doctor) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hospitalmanagementsystem/doctorinfoadmin.fxml"));
            loader.setControllerFactory(applicationContext::getBean);
            Parent root = loader.load();

            DoctorProfileAdminController controller = loader.getController();
            controller.setDoctorInn(doctor.getInn());
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Hospital Management System - Doctor Information");
            newStage.show();
            stage.close();
            stage = newStage;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void  loadAddDoctorScreen(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hospitalmanagementsystem/adddoctor.fxml"));
            loader.setControllerFactory(applicationContext::getBean);
            Parent root = loader.load();
            Stage adminStage = new Stage();
            adminStage.setScene(new Scene(root));
            adminStage.setTitle("Administrator Dashboard");
            adminStage.show();

            stage.close();
            stage = adminStage;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void  loadAddPatientScreen(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hospitalmanagementsystem/addpatient.fxml"));
            loader.setControllerFactory(applicationContext::getBean);
            Parent root = loader.load();
            Stage adminStage = new Stage();
            adminStage.setScene(new Scene(root));
            adminStage.setTitle("Administrator Dashboard");
            adminStage.show();

            stage.close();
            stage = adminStage;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMedHistoryScreen(Long patientInn, Long doctorInn) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hospitalmanagementsystem/medhistory.fxml"));
            loader.setControllerFactory(applicationContext::getBean);
            Parent root = loader.load();

            HistoryController controller = loader.getController();
            controller.setPatientAndDoctorInn(patientInn, doctorInn);

            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Medical History");
            newStage.show();
            stage.close();
            stage = newStage;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadDoctorMainScreen(Long doctorInn) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hospitalmanagementsystem/maindoctor.fxml"));
            loader.setControllerFactory(applicationContext::getBean);
            Parent root = loader.load();

            MainDoctorController controller = loader.getController();
            controller.setDoctorInn(doctorInn);

            Stage doctorStage = new Stage();
            doctorStage.setScene(new Scene(root));
            doctorStage.setTitle("Doctor Dashboard");
            doctorStage.show();

            stage.close();
            stage = doctorStage;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadAdministratorScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hospitalmanagementsystem/mainadmin.fxml"));
            loader.setControllerFactory(applicationContext::getBean);
            Parent root = loader.load();
            Stage adminStage = new Stage();
            adminStage.setScene(new Scene(root));
            adminStage.setTitle("Administrator Dashboard");
            adminStage.show();

            stage.close();
            stage = adminStage;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPatientInfoScreen(Long patientInn, Long doctorInn) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hospitalmanagementsystem/patientinfo.fxml"));
            loader.setControllerFactory(applicationContext::getBean);
            Parent root = loader.load();

            PatientInfoForDocController controller = loader.getController();
            controller.setPatientAndDoctorInn(patientInn, doctorInn);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Hospital Management System - Patient Information");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPatientInfoAdminScreen(Long patientInn) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hospitalmanagementsystem/patientinfoadmin.fxml"));
            loader.setControllerFactory(applicationContext::getBean);
            Parent root = loader.load();

            PatientInfoForAdmin controller = loader.getController();
            controller.setPatientInn(patientInn);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Hospital Management System - Patient Information");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadDoctorBySpecialty(Specialty specialty) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hospitalmanagementsystem/doctorbyspec.fxml"));
        loader.setControllerFactory(applicationContext::getBean);
        Parent root = loader.load();

        // Get the controller and pass the specialty
        DoctorViewController controller = loader.getController();
        controller.setSpecialty(specialty);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Hospital Management System - Patient Information");
        stage.show();
    }
}
