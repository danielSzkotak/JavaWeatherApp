module com.example.javaweatherapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javaweatherapp to javafx.fxml;
    exports com.example.javaweatherapp;
}