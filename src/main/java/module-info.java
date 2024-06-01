module com.example.hospitalmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hospitalmanagementsystem to javafx.fxml;
    exports com.example.hospitalmanagementsystem;
}