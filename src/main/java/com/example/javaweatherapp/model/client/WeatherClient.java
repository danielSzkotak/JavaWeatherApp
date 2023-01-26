package com.example.javaweatherapp.model.client;

import com.example.javaweatherapp.model.SingleDayWeather;
import com.example.javaweatherapp.model.WeatherForecast;

import java.io.IOException;

public interface WeatherClient {
    WeatherForecast getWeather(String cityName) throws IOException;
}
