package com.example.javaweatherapp.model;

import com.example.javaweatherapp.model.client.WeatherClient;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

// THE MAIN CLASS OF THIS RPOJECT
public class WeatherService extends Service<Weather> {

    private final WeatherClient weatherClient;

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    private String cityName;

    //DEPENDENCY INVERSION - ACCEPTING WEATHERCLIENT IN CONSTRUCTOR
    public WeatherService(WeatherClient weatherClient) {

        this.weatherClient = weatherClient;
    }


    @Override
    protected Task<Weather> createTask() {
        return new Task<>() {
            @Override
            protected Weather call() throws Exception {

                return weatherClient.getWeather(cityName);
            }
        };
    }
}
