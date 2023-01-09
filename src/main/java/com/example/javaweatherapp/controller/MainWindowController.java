package com.example.javaweatherapp.controller;

import com.example.javaweatherapp.model.Weather;
import com.example.javaweatherapp.model.WeatherService;
import com.example.javaweatherapp.model.WeatherServiceFactory;
import com.example.javaweatherapp.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    @FXML
    private TextField city1TextField;

    @FXML
    private Label firstCityLabel;

    @FXML
    private Label temperature;

    @FXML
    private ImageView loadingImage;

    @FXML
    private ImageView weatherIconImageView;

    private WeatherService weatherService;


    public MainWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @FXML
    void showWeatherActionBtn() throws IOException {
        //GET DATA INPUT FROM INTERFACE
        String cityName = city1TextField.getText();
        weatherService.setCityName(cityName);

        //INVOKE BUISNESS LOGIC / MODEL
        weatherService.setOnSucceeded(workerStateEvent -> {
            loadingImage.setVisible(false);
            Weather weather = weatherService.getValue();
            displayWeather(weather);
        });
        weatherService.setOnRunning(workerStateEvent -> {

            loadingImage.setVisible(true);
            temperature.setVisible(false);
        });

        weatherService.restart();

    }

    private void displayWeather(Weather weather){
        temperature.setText(weather.getTempInCelsius());
        temperature.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        weatherService = WeatherServiceFactory.createWeatherService();
        temperature.setVisible(false);
        loadingImage.setVisible(false);
        loadingImage.setImage(new Image(getClass().getResourceAsStream("/icons/loader.gif")));
        try {
            weatherIconImageView.setFitHeight(120);
            weatherIconImageView.setFitWidth(120);
            weatherIconImageView.setImage(new Image("https://openweathermap.org/img/wn/09d@2x.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
