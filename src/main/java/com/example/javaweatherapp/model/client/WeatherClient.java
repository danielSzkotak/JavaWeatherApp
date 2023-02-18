package com.example.javaweatherapp.model.client;

import com.example.javaweatherapp.model.SingleDayWeather;
import com.example.javaweatherapp.model.WeatherForecast;

import java.io.IOException;
import java.util.ArrayList;

public interface WeatherClient {
    WeatherForecast getWeather(String cityName) throws IOException;
    ArrayList<ArrayList<String>> getLocations(String cityName) throws Exception;
}


