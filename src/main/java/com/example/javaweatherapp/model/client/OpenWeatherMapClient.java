package com.example.javaweatherapp.model.client;

import com.example.javaweatherapp.model.SingleDayWeather;


import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;


public class OpenWeatherMapClient implements WeatherClient{


    @Override
    public SingleDayWeather getWeather(String cityName) throws IOException {

        try {
            APIClientService apiClientService = new APIClientService();
            URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?" + apiClientService.getCityCoordinates(cityName) +
                    "&appid=" + apiClientService.getAPI_KEY());
            JsonReader jsonReader = apiClientService.getJsonFromAPI(url);
            JsonObject json = jsonReader.readObject();
            jsonReader.close();

            return new SingleDayWeather(cityName, getCurrentTemperature(json), LocalDate.now(), getCurrentFeelsLikeTemperature(json),
                    getCurrentWeatherIconId(json), getCurrentWeatherPressure(json));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    String getCurrentTemperature(JsonObject json){

        final JsonArray list = json.getJsonArray("list");
        final JsonObject forecast = list.getJsonObject(0);
        final JsonObject main = forecast.getJsonObject("main");
        final double temp = main.getJsonNumber("temp").doubleValue() - 273.15;
        DecimalFormat df = new DecimalFormat("#");
        String formattedValue = df.format(temp);
        return formattedValue + "°C";
    }

    String getCurrentFeelsLikeTemperature(JsonObject json){

        final JsonArray list = json.getJsonArray("list");
        final JsonObject forecast = list.getJsonObject(0);
        final JsonObject main = forecast.getJsonObject("main");
        final double temp = main.getJsonNumber("feels_like").doubleValue() - 273.15;
        DecimalFormat df = new DecimalFormat("#");
        String formattedValue = df.format(temp);
        return formattedValue + "°C";
    }

    int getCurrentWeatherIconId(JsonObject json){

        final JsonArray list = json.getJsonArray("list");
        final JsonObject forecast = list.getJsonObject(0);
        final JsonArray weather = forecast.getJsonArray("weather");
        final JsonObject weatherObj = weather.getJsonObject(0);
        final int iconId = weatherObj.getJsonNumber("id").intValue();
        //String result = icon.replaceAll("\"([^\"]+)\"", "$1");
        return iconId;
    }

    String getCurrentWeatherPressure(JsonObject json){

        final JsonArray list = json.getJsonArray("list");
        final JsonObject forecast = list.getJsonObject(0);
        final JsonObject main = forecast.getJsonObject("main");
        final int pressure = main.getJsonNumber("pressure").intValue();
        String result = String.valueOf(pressure);
        return result;
    }


}
