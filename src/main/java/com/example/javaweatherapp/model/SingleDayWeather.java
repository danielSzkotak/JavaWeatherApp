package com.example.javaweatherapp.model;

import java.time.LocalDate;

public class SingleDayWeather {

    private final String cityName;
    private final String tempInCelsius;
    private final String feelsLikeTemperature;
    private final LocalDate date;
    private final int iconId;
    private final String pressure;
    private final String description;
    private final String temp_min;
    private final String temp_max;
    private final String rain;


    public SingleDayWeather(String cityName, String tempInCelsius, LocalDate date, String feelsLikeTemperature, int iconId, String pressure, String rain, String description, String temp_min, String temp_max) {
        this.cityName = cityName;
        this.tempInCelsius = tempInCelsius;
        this.date = date;
        this.feelsLikeTemperature = feelsLikeTemperature;
        this.iconId = iconId;
        this.pressure = pressure;
        this.rain = rain;
        this.description = description;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
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

    public int getIcon() {
        return iconId;
    }

    public String getPressure() {
        return pressure;
    }

    public String getRain() {
        return rain;
    }

    public String getDescription() {
        return description;
    }

    public String getTemp_min() {
        return temp_min;
    }

    public String getTemp_max() {
        return temp_max;
    }
}
