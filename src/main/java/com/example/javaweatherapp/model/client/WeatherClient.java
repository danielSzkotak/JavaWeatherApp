package com.example.javaweatherapp.model.client;

import com.example.javaweatherapp.model.Weather;

public interface WeatherClient {
    Weather getWeather(String cityName);
}
