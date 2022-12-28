package com.example.javaweatherapp.model;

import com.example.javaweatherapp.model.client.WeatherClient;

import java.io.IOException;

// THE MAIN CLASS OF THIS RPOJECT
public class WeatherService {

    private final WeatherClient weatherClient;

    //DEPENDENCY INVERSION - ACCEPTING WEATHERCLIENT IN CONSTRUCTOR
    public WeatherService(WeatherClient weatherClient) {

        this.weatherClient = weatherClient;
    }

    public Weather getWeather(String cityName) throws IOException {

        return weatherClient.getWeather(cityName);
    }
}
