package com.example.javaweatherapp.model;

import java.time.LocalDate;

public class Weather {

    private final String cityName;
    private final String tempInCelsius;
    private final LocalDate date;

    public Weather(String cityName, String tempInCelsius, LocalDate date) {
        this.cityName = cityName;
        this.tempInCelsius = tempInCelsius;
        this.date = date;
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
}
