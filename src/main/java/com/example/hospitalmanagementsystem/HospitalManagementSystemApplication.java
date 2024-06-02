package com.example.hospitalmanagementsystem;

import com.example.hospitalmanagementsystem.config.ScreenLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class HospitalManagementSystemApplication extends Application {

    @Getter
    private static ConfigurableApplicationContext springContext;
    private ScreenLoader screenLoader;

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

        screenLoader = springContext.getBean(ScreenLoader.class);
        screenLoader.setStage(primaryStage);
        screenLoader.loadMainScreen();
    }

    @Override
    public void stop() throws Exception {
        springContext.close();
    }

}
