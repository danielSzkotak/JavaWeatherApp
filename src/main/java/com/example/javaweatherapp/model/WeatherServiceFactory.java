package com.example.javaweatherapp.model;

import com.example.javaweatherapp.model.client.OpenWeatherMapClient;
import com.example.javaweatherapp.model.client.WeatherClient;

public class WeatherServiceFactory {

    //we use factory design pattern
    public static WeatherService createWeatherService(){

        return new WeatherService(createWeatherClient());
    }

    private static WeatherClient createWeatherClient(){

        return new OpenWeatherMapClient();
    }
}
