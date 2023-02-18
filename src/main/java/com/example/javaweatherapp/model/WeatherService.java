package com.example.javaweatherapp.model;

import com.example.javaweatherapp.model.client.WeatherClient;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// THE MAIN CLASS OF THIS RPOJECT
public class WeatherService extends Service<WeatherForecast> {

    private final WeatherClient weatherClient;
    public void setCityCoordinates(String cityCoordinates) {
        this.cityCoordinates = cityCoordinates;
    }
    private String cityCoordinates;

    //DEPENDENCY INVERSION - ACCEPTING WEATHERCLIENT IN CONSTRUCTOR
    public WeatherService(WeatherClient weatherClient) {

        this.weatherClient = weatherClient;
    }


    @Override
    protected Task<WeatherForecast> createTask() {
        return new Task() {
            @Override
            protected WeatherForecast call() throws Exception {

                return weatherClient.getWeather(cityCoordinates);
            }

        };
    }




}
