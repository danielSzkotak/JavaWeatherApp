package com.example.javaweatherapp.model.client;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.text.DecimalFormat;

public class JsonManager {

    private JsonObject jsonObject;

    public JsonManager(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    String extractTemperature(int forecastDayNumber){

        final JsonArray list = jsonObject.getJsonArray("list");
        final JsonObject forecast = list.getJsonObject(forecastDayNumber);
        final JsonObject main = forecast.getJsonObject("main");
        double temp = main.getJsonNumber("temp").doubleValue() - 273.15;
        temp = Math.round(temp);
        DecimalFormat df = new DecimalFormat("#");
        String formattedValue = df.format(temp);
        return formattedValue + "°C";
    }

    String extractFeelsLikeTemperature(int forecastDayNumber){

        final JsonArray list = jsonObject.getJsonArray("list");
        final JsonObject forecast = list.getJsonObject(0);
        final JsonObject main = forecast.getJsonObject("main");
        double temp = main.getJsonNumber("feels_like").doubleValue() - 273.15;
        temp = Math.round(temp);
        DecimalFormat df = new DecimalFormat("#");
        String formattedValue = df.format(temp);
        return formattedValue + "°C";
    }

    int extractWeatherIconId(int forecastDayNumber){

        final JsonArray list = jsonObject.getJsonArray("list");
        final JsonObject forecast = list.getJsonObject(0);
        final JsonArray weather = forecast.getJsonArray("weather");
        final JsonObject weatherObj = weather.getJsonObject(0);
        final int iconId = weatherObj.getJsonNumber("id").intValue();
        return iconId;
    }

    String extractWeatherPressure(int forecastDayNumber){

        final JsonArray list = jsonObject.getJsonArray("list");
        final JsonObject forecast = list.getJsonObject(0);
        final JsonObject main = forecast.getJsonObject("main");
        final int pressure = main.getJsonNumber("pressure").intValue();
        String result = String.valueOf(pressure);
        return result;
    }
}
