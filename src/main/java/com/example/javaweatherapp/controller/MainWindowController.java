package com.example.javaweatherapp.controller;

import com.example.javaweatherapp.model.*;
import com.example.javaweatherapp.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class MainWindowController extends BaseController implements Initializable {


    @FXML
    private Label cityLbl;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Label firstCityLabel;

    @FXML
    private Label temperatureLbl;

    @FXML
    private ImageView loadingImage;

    @FXML
    private ImageView weatherIconImageView;

    @FXML
    private Label feelsLikeTemperatureLbl;

    private WeatherService weatherService;
    private LocationService locationService;


    public MainWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @FXML
    void showWeatherActionBtn() throws IOException {

        //GET DATA INPUT FROM INTERFACE

        String cityName = comboBox.getValue().toString();

        locationService.setCityName(cityName);
        locationService.setOnSucceeded(workerStateEvent -> {

            ArrayList<ArrayList<String>> locations = locationService.getValue();

            comboBox.getItems().clear();

            for (int i=0; i<locations.size(); i++){
                comboBox.getItems().add(locations.get(i).get(0) + " " + locations.get(i).get(1));
            }
            comboBox.show();

            comboBox.setOnAction(actionEvent -> {
                int cityIndexFromComboBox = comboBox.getSelectionModel().getSelectedIndex();
                if (cityIndexFromComboBox >= 0) {
                    weatherService.setCityCoordinates("lat=" + locations.get(cityIndexFromComboBox).get(2).toString() + "&lon=" + locations.get(cityIndexFromComboBox).get(3).toString());
                    weatherService.restart();
                }
            });

        });



        weatherService.setOnSucceeded(workerStateEvent -> {

            loadingImage.setVisible(false);
            WeatherForecast weatherForecast = weatherService.getValue();

            displayWeather(weatherForecast.getWeathers());
            cityLbl.setText(cityName);
            comboBox.getItems().clear();

        });
        weatherService.setOnRunning(workerStateEvent -> {

            loadingImage.setVisible(true);
            temperatureLbl.setVisible(false);
        });

        locationService.restart();

    }

    private void displayWeather(List<SingleDayWeather> weathers){

        cityLbl.setVisible(true);

        temperatureLbl.setText(weathers.get(0).getTempInCelsius());
        temperatureLbl.setVisible(true);
        feelsLikeTemperatureLbl.setText("Odczuwalna: " + weathers.get(0).getFeelsLikeTemperature());

        System.out.println(weathers.get(0).getPressure());

        try {
            weatherIconImageView.setFitHeight(120);
            weatherIconImageView.setFitWidth(120);
            weatherIconImageView.setImage(new Image(getClass().getResourceAsStream("/icons/iconList/" + weathers.get(0).getIcon() + ".png")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        locationService = WeatherServiceFactory.createLocationsService();
        weatherService = WeatherServiceFactory.createWeatherService();
        temperatureLbl.setVisible(false);
        loadingImage.setVisible(false);
        cityLbl.setVisible(false);
        loadingImage.setImage(new Image(getClass().getResourceAsStream("/icons/loader.gif")));

    }

}
