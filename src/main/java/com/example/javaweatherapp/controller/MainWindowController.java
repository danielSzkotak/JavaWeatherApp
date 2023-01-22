package com.example.javaweatherapp.controller;

import com.example.javaweatherapp.model.SingleDayWeather;
import com.example.javaweatherapp.model.WeatherForecast;
import com.example.javaweatherapp.model.WeatherService;
import com.example.javaweatherapp.model.WeatherServiceFactory;
import com.example.javaweatherapp.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainWindowController extends BaseController implements Initializable {

    @FXML
    private TextField city1TextField;

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

            WeatherForecast weatherForecast = weatherService.getValue();

            for (int i=0; i<weatherForecast.getWeathers().size(); i++){
                System.out.println(weatherForecast.getWeathers().get(i).getDate());
                System.out.println("City: " + city1TextField.getText());
                System.out.println("Temperature: " + weatherForecast.getWeathers().get(i).getTempInCelsius());
                //System.out.println("Icon id: " +  weatherForecast.getWeathers().get(i).getIcon());
                System.out.println("Pressure: " +  weatherForecast.getWeathers().get(i).getPressure());
                System.out.println("Rain: " +  weatherForecast.getWeathers().get(i).getRain());
                System.out.println("Snow: " + weatherForecast.getWeathers().get(i).getSnow());
                System.out.println("Temp min: " + weatherForecast.getWeathers().get(i).getTemp_min());
                System.out.println("Temp max: " +  weatherForecast.getWeathers().get(i).getTemp_max());
                System.out.println("Description: " +  weatherForecast.getWeathers().get(i).getDescription());
                System.out.println("-----------------------------------------");
            }


        });
        weatherService.setOnRunning(workerStateEvent -> {

            loadingImage.setVisible(true);
            temperatureLbl.setVisible(false);
        });

        weatherService.restart();

    }

    private void displayWeather(List<SingleDayWeather> weathers){
        temperatureLbl.setText(weathers.get(0).getTempInCelsius());
        temperatureLbl.setVisible(true);
        feelsLikeTemperatureLbl.setText("Odczuwalna: " + weathers.get(0).getFeelsLikeTemperature());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        weatherService = WeatherServiceFactory.createWeatherService();
        temperatureLbl.setVisible(false);
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
