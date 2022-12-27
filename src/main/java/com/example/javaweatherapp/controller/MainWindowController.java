package com.example.javaweatherapp.controller;

import com.example.javaweatherapp.model.Weather;
import com.example.javaweatherapp.model.WeatherService;
import com.example.javaweatherapp.model.WeatherServiceFactory;
import com.example.javaweatherapp.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    @FXML
    private Label firstCityLabel;

    @FXML
    private Label temperature;

    @FXML
    private Label temperatureLabel;

    private WeatherService weatherService;

    public MainWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @FXML
    void showWeatherActionBtn() {
        //GET DATA INPUT FROM INTERFACE
        String cityName = "Kraków";

        //INVOKE BUISNESS LOGIC / MODEL
        Weather weather = weatherService.getWeather(cityName);

        //DISPLAY RESULT FROM BUSINESS LOGIC
        displayWeather(weather);

    }

    private void displayWeather(Weather weather){
        temperature.setText("25.0 oC");
        temperature.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        weatherService = WeatherServiceFactory.createWeatherService();
        temperature.setVisible(false);
    }
}
