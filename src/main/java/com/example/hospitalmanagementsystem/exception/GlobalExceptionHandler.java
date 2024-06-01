package com.example.hospitalmanagementsystem.exception;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.FileNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(javafx.fxml.LoadException.class)
    public void handleLoadException(javafx.fxml.LoadException ex, Stage stage) {
        showAlert("FXML Load Exception", ex.getMessage(), stage);
        ex.printStackTrace();
    }

    @ExceptionHandler(FileNotFoundException.class)
    public void handleFileNotFoundException(FileNotFoundException ex, Stage stage) {
        showAlert("File Not Found", ex.getMessage(), stage);
        ex.printStackTrace();
    }

    private void showAlert(String title, String message, Stage stage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(stage);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
