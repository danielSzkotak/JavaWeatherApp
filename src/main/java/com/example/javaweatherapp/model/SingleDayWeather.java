package com.example.javaweatherapp.model;

import java.time.LocalDate;

public class SingleDayWeather {

    private final String cityName;
    private final String tempInCelsius;



    private final String feelsLikeTemperature;
    private final LocalDate date;

    public SingleDayWeather(String cityName, String tempInCelsius, LocalDate date, String feelsLikeTemperature) {
        this.cityName = cityName;
        this.tempInCelsius = tempInCelsius;
        this.date = date;
        this.feelsLikeTemperature = feelsLikeTemperature;
    }

    public String getCityName() {
        return cityName;
    }

    public String getTempInCelsius() {
        return tempInCelsius;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getFeelsLikeTemperature() {
        return feelsLikeTemperature;
    }
}
