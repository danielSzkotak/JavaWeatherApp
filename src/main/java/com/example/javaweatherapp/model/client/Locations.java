package com.example.javaweatherapp.model.client;

import java.util.ArrayList;

public class Locations {

    public void setLocations(ArrayList<ArrayList<String>> locations) {
        this.locations = locations;
    }

    private ArrayList<ArrayList<String>> locations;

    public ArrayList<ArrayList<String>> getLocations() {
        return locations;
    }


}
