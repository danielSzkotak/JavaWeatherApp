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

    private long getUnixTimeStampBeginningOfDay(int forecastDayNumber){

        Calendar date = new GregorianCalendar();
        date.add(Calendar.DAY_OF_MONTH, getDayCount(forecastDayNumber));
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);

        long start = date.getTimeInMillis() / 1000;
        return start;

    }

    private long getUnixTimeStampEndOfDay(int forecastDayNumber){

        Calendar date = new GregorianCalendar();
        date.add(Calendar.DAY_OF_MONTH, getDayCount(forecastDayNumber));
        date.set(Calendar.HOUR_OF_DAY, 23);
        date.set(Calendar.MINUTE, 59);

        long start = date.getTimeInMillis() / 1000;
        return start;

    }

    private double getMinTemperatureFromPeriod(int forecastDayNumber, long[] timeStamps, double[] temperatures){

        double minTemperature = 100;
        for (int i=0; i<40; i++){
            if (timeStamps[i] >= getUnixTimeStampBeginningOfDay(forecastDayNumber) && timeStamps[i] <= getUnixTimeStampEndOfDay(forecastDayNumber)){
                if (temperatures[i] < minTemperature) {
                    minTemperature = temperatures[i];
                }
            }
        }
        return minTemperature;
    }


    private double[] getTemperatures(){

        JsonArray APIListOfWeatherParameters = jsonObject.getJsonArray("list");
        double temperatures[] = new double[40];

        for (int i=0; i < APIListOfWeatherParameters.size(); i++){

            JsonObject forecast = APIListOfWeatherParameters.getJsonObject(i);
            JsonObject main = forecast.getJsonObject("main");
            temperatures[i] = Math.round(main.getJsonNumber("temp").doubleValue() - 273.15);
        }

        return temperatures;
    }

    private long[] getTimeStamps(){

        JsonArray APIListOfWeatherParameters = jsonObject.getJsonArray("list");
        long timeStamps[] = new long[40];

        for (int i=0; i < APIListOfWeatherParameters.size(); i++){

            JsonObject forecast = APIListOfWeatherParameters.getJsonObject(i);
            timeStamps[i] = forecast.getJsonNumber("dt").longValue();

        }

        return timeStamps;
    }

    String extractMinTemperature(int forecastDayNumber){

            return String.valueOf(getMinTemperatureFromPeriod(forecastDayNumber, getTimeStamps(), getTemperatures()));
    }

    private int getDayCount(int forecastDayNumber){
        int forecastDayCount;
        if (forecastDayNumber != 39) {
            forecastDayCount = forecastDayNumber / 8;
        } else {
            forecastDayCount = 5;
        }
        return forecastDayCount;
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
