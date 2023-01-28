package com.example.javaweatherapp.model;

import org.javatuples.Quartet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WeatherForecast {

    private final String cityName;
    private final List<SingleDayWeather> weathers;
    private final ArrayList<Quartet> locations;

    public WeatherForecast(String cityName, List<SingleDayWeather> weathers, ArrayList<Quartet> locations) {
        this.cityName = cityName;
        this.weathers = weathers;
        this.locations = locations;
    }

    public String getCityName() {
        return cityName;
    }

    public ArrayList<Quartet> getLocations() { return locations; }

    public List<SingleDayWeather> getWeathers() {
        return weathers;
    }

    @Override
    public String toString() {
        return "" +
                weathers ;

    }
}
