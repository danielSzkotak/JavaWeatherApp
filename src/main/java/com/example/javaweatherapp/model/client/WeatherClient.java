package com.example.javaweatherapp.model.client;

import com.example.javaweatherapp.model.SingleDayWeather;

import java.io.IOException;

public interface WeatherClient {
    SingleDayWeather getWeather(String cityName) throws IOException;
}
