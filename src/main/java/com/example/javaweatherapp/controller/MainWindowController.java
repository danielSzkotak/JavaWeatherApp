package com.example.javaweatherapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainWindowController {

    @FXML
    private Label firstCityLabel;

    @FXML
    void showWeatherActionBtn() {
        System.out.println("klikłem na pierwsze miasto");
    }
}
