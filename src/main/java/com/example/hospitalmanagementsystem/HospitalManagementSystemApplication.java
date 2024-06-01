package com.example.hospitalmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class HospitalManagementSystemApplication extends Application {

    private static ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        Application.launch(HospitalManagementSystemApplication.class, args);
    }

    @Override
    public void init() {
        springContext = SpringApplication.run(HospitalManagementSystemApplication.class);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("JavaFX and Spring Boot");

        // Load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/hospitalmanagementsystem/doctor.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);
        Parent root = fxmlLoader.load();

        // Set the scene
        primaryStage.setScene(new Scene(root, 400, 200));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        springContext.close();
    }
}
