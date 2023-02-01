package com.example.javaweatherapp.model;

import org.javatuples.Quartet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WeatherForecast {

    private final String cityName;
    private final List<SingleDayWeather> weathers;

    public WeatherForecast(String cityName, List<SingleDayWeather> weathers) {
        this.cityName = cityName;
        this.weathers = weathers;
    }

    public String getCityName() {
        return cityName;
    }

    public List<SingleDayWeather> getWeathers() {
        return weathers;
    }

    @Override
    public String toString() {
        return "" +
                weathers ;

    }
}
