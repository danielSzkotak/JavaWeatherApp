package com.example.javaweatherapp.model.client;

import com.example.javaweatherapp.model.Weather;

import java.time.LocalDate;

public class ExampleWeatherClient implements WeatherClient{
    @Override
    public Weather getWeather(String cityName) {
        return new Weather(cityName, 22.3, LocalDate.now());
        //MIEJSCE NA PRAWDZIWĄ IMPLEMENTACJĘ
    }
}
