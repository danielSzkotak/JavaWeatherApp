package com.example.javaweatherapp.model.client;

import com.example.javaweatherapp.model.Weather;

import java.io.IOException;
import java.net.MalformedURLException;

public interface WeatherClient {
    Weather getWeather(String cityName) throws IOException;
}
