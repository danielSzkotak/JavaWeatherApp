package com.example.javaweatherapp.model;

import com.example.javaweatherapp.model.client.WeatherClient;

// THE MAIN CLASS OF THIS RPOJECT
public class WeatherService {

    private final WeatherClient weatherClient;

    //DEPENDENCY INVERSION - ACCEPTING WEATHERCLIENT IN CONSTRUCTOR
    public WeatherService(WeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    public Weather getWeather(String cityName){
        return weatherClient.getWeather(cityName);
    }
}
