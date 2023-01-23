package com.example.javaweatherapp.model;

import java.time.LocalDate;

public class SingleDayWeather {

    private final String cityName;
    private final String tempInCelsius;
    private final String feelsLikeTemperature;
    private final LocalDate date;
    private final String iconId;
    private final String pressure;
    private final String description;
    private final String temp_min;
    private final String temp_max;
    private final String rain;
    private final String snow;
    private final long unix_time;




    public SingleDayWeather(String cityName, String tempInCelsius, LocalDate date, String feelsLikeTemperature, String iconId, String pressure, String rain, String snow,
                            String description, String temp_min, String temp_max, long unix_time) {
        this.cityName = cityName;
        this.tempInCelsius = tempInCelsius;
        this.date = date;
        this.feelsLikeTemperature = feelsLikeTemperature;
        this.iconId = iconId;
        this.pressure = pressure;
        this.rain = rain;
        this.snow = snow;
        this.description = description;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.unix_time = unix_time;
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

    public String getIcon() {
        return iconId;
    }

    public String getPressure() {
        return pressure;
    }

    public String getRain() {
        return rain;
    }

    public String getSnow() {
        return snow;
    }

    @Override
    public String toString() {
        return "SingleDayWeather{" +
                ", date=" + date +
                " cityName='" + cityName + '\'' +
                ", tempInCelsius='" + tempInCelsius + '\'' +
                ", feelsLikeTemperature='" + feelsLikeTemperature + '\'' +
                ", iconId=" + iconId +
                ", pressure='" + pressure + '\'' +
                ", description='" + description + '\'' +
                ", temp_min='" + temp_min + '\'' +
                ", temp_max='" + temp_max + '\'' +
                ", rain='" + rain + '\'' +
                ", unix_time=" + unix_time +
                '}' + "\r\n";
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

    public long getUnix_time() {
        return unix_time;
    }
}
