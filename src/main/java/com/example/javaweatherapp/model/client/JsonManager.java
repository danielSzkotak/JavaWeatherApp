package com.example.javaweatherapp.model.client;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
        temp = Math.floor(temp);
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

        int forecastDayCount;
        if (forecastDayNumber != 39) {
             forecastDayCount = forecastDayNumber / 8;
        } else {
            forecastDayCount = 5;
        }


            Calendar date = new GregorianCalendar();
            date.add(Calendar.DAY_OF_MONTH, forecastDayCount);
            date.set(Calendar.HOUR_OF_DAY, 0);
            date.set(Calendar.MINUTE, 0);

            long start = date.getTimeInMillis() / 1000;

            date.set(Calendar.HOUR_OF_DAY, 23);
            date.set(Calendar.MINUTE, 59);

            long stop = date.getTimeInMillis() / 1000;

            //-------------------------------------------------------

            JsonArray list = jsonObject.getJsonArray("list");
            double temperatures[] = new double[40];
            long timeStamps[] = new long[40];

            for (int i=0; i < list.size(); i++){

                JsonObject forecast = list.getJsonObject(i);
                JsonObject main = forecast.getJsonObject("main");
                timeStamps[i] = forecast.getJsonNumber("dt").longValue();
                temperatures[i] = Math.round(main.getJsonNumber("temp").doubleValue() - 273.15);
                //System.out.println(forecastDayCount + " " + i + " Temperature: " + temperatures[i]);
            }
            double temp = 100;

            for (int i=0; i<40; i++){
                if (timeStamps[i] >= start && timeStamps[i] <= stop){
                    if (temperatures[i] < temp) {
                        temp = temperatures[i];
                    }
                }
            }
            return String.valueOf(temp);

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

    long extractUnixTimeStamp(int forecastDayNumber){

        final JsonArray list = jsonObject.getJsonArray("list");
        final JsonObject forecast = list.getJsonObject(forecastDayNumber);
        long unix_seconds = forecast.getJsonNumber("dt").longValue();
        return unix_seconds;
    }
}
