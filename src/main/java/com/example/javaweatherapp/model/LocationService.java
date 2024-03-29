package com.example.javaweatherapp.model;

import com.example.javaweatherapp.model.client.Locations;
import com.example.javaweatherapp.model.client.WeatherClient;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.net.UnknownHostException;
import java.util.ArrayList;

// THE MAIN CLASS OF THIS RPOJECT
public class LocationService extends Service<Locations> {

    private final WeatherClient weatherClient;
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    private String cityName;

    //DEPENDENCY INVERSION - ACCEPTING WEATHERCLIENT IN CONSTRUCTOR
    public LocationService(WeatherClient weatherClient) {

        this.weatherClient = weatherClient;
    }


    @Override
    protected Task<Locations> createTask() {

            return new Task<Locations>() {
                @Override
                protected Locations call() throws Exception {
                    return weatherClient.getLocations(cityName);
                }
            };
    }

}
