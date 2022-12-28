module com.example.javaweatherapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.json;

    opens com.example.javaweatherapp to javafx.fxml;
    exports com.example.javaweatherapp;
    opens com.example.javaweatherapp.controller to javafx.fxml;

}