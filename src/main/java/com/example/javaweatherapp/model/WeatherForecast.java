package com.example.javaweatherapp.model;

import java.util.Collection;

public class WeatherForecast {

    private final String cityName;
    private final Collection<SingleDayWeather> weathers;

    public WeatherForecast(String cityName, Collection<SingleDayWeather> weathers) {
        this.cityName = cityName;
        this.weathers = weathers;
    }

    public String getCityName() {
        return cityName;
    }

    public Collection<SingleDayWeather> getWeathers() {
        return weathers;
    }


}
