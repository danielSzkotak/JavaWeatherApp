package com.example.javaweatherapp.model.client;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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
        final JsonObject forecast = list.getJsonObject(forecastDayNumber);
        final JsonObject main = forecast.getJsonObject("main");
        double temp = main.getJsonNumber("feels_like").doubleValue() - 273.15;
        temp = Math.round(temp);
        DecimalFormat df = new DecimalFormat("#");
        String formattedValue = df.format(temp);
        return formattedValue + "°C";
    }

    int extractWeatherIconId(int forecastDayNumber){

        final JsonArray list = jsonObject.getJsonArray("list");
        final JsonObject forecast = list.getJsonObject(forecastDayNumber);
        final JsonArray weather = forecast.getJsonArray("weather");
        final JsonObject weatherObj = weather.getJsonObject(0);
        final int iconId = weatherObj.getJsonNumber("id").intValue();
        return iconId;
    }

    String extractWeatherPressure(int forecastDayNumber){

        final JsonArray list = jsonObject.getJsonArray("list");
        final JsonObject forecast = list.getJsonObject(forecastDayNumber);
        final JsonObject main = forecast.getJsonObject("main");
        final int pressure = main.getJsonNumber("pressure").intValue();
        String result = String.valueOf(pressure);
        return result;
    }

    String extractWeatherRain(int forecastDayNumber){

        final JsonArray list = jsonObject.getJsonArray("list");
        final JsonObject forecast = list.getJsonObject(forecastDayNumber);
        final JsonObject rainObj = forecast.getJsonObject("rain");
        String result = "";
        if (rainObj == null) {
            result = "0.0 mm";
        } else {
            final double rain = rainObj.getJsonNumber("3h").doubleValue();
            result = String.valueOf(rain) + "mm";
        }
        return result;
    }

    String extractWeatherDescription(int forecastDayNumber){

        final JsonArray list = jsonObject.getJsonArray("list");
        final JsonObject forecast = list.getJsonObject(forecastDayNumber);
        final JsonArray weather = forecast.getJsonArray("weather");
        final JsonObject weatherObj = weather.getJsonObject(0);
        final JsonString description = weatherObj.getJsonString("description");
        final String result = String.valueOf(description);
        return result;
    }

    String extractMinTemperature(int forecastDayNumber){

        final JsonArray list = jsonObject.getJsonArray("list");
        final JsonObject forecast = list.getJsonObject(forecastDayNumber);
        final JsonObject main = forecast.getJsonObject("main");
        double temp = main.getJsonNumber("temp_min").doubleValue() - 273.15;
        temp = Math.round(temp);
        DecimalFormat df = new DecimalFormat("#");
        String formattedValue = df.format(temp);
        return formattedValue + "°C";
    }

    String extractMaxTemperature(int forecastDayNumber){

        final JsonArray list = jsonObject.getJsonArray("list");
        final JsonObject forecast = list.getJsonObject(forecastDayNumber);
        final JsonObject main = forecast.getJsonObject("main");
        double temp = main.getJsonNumber("temp_max").doubleValue() - 273.15;
        temp = Math.round(temp);
        DecimalFormat df = new DecimalFormat("#");
        String formattedValue = df.format(temp);
        return formattedValue + "°C";
    }

    String extractUnixTimeStamp(int forecastDayNumber){

        final JsonArray list = jsonObject.getJsonArray("list");
        final JsonObject forecast = list.getJsonObject(forecastDayNumber);
        //final JsonObject main = forecast.getJsonObject("dt");
        long unix_seconds = forecast.getJsonNumber("dt").longValue();
        Date date = new Date(unix_seconds*1000L);
        SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        jdf.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        String java_date = jdf.format(date);
        return java_date;
    }
}
