package com.example.javaweatherapp.model.client;

import com.example.javaweatherapp.model.SingleDayWeather;
import com.example.javaweatherapp.model.WeatherForecast;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.json.JsonObject;
import javax.json.JsonReader;


public class OpenWeatherMapClient implements WeatherClient {

    private JsonManager jsonManager;
    private String cityName;
    private List<SingleDayWeather> weathers = new ArrayList<>();


    @Override
    public WeatherForecast getWeather(String cityName) throws IOException {

        try {
            APIClientService apiClientService = new APIClientService();
            URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?" + apiClientService.getCityCoordinates(cityName) +
                    "&appid=" + apiClientService.getAPI_KEY() + "&lang=pl");
            JsonReader jsonReader = apiClientService.getJsonFromAPI(url);
            JsonObject json = jsonReader.readObject();
            jsonReader.close();

            this.jsonManager = new JsonManager(json);
            this.cityName = cityName;

            return populateWeathers();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private WeatherForecast populateWeathers(){

        weathers.clear();
        int oneDaySeparationStepInAPIJson=0; // EVERY 8 STEP READING FROM API IS DAY PERIOD
        LocalDate date = LocalDate.parse("2018-11-13");
        date = date.now();
        
        while (oneDaySeparationStepInAPIJson<=40) {

            SingleDayWeather singleDayWeather = new SingleDayWeather(cityName, getTemperature(oneDaySeparationStepInAPIJson), date, getFeelsLikeTemperature(oneDaySeparationStepInAPIJson), getWeatherIconId(oneDaySeparationStepInAPIJson), getWeatherPressure(oneDaySeparationStepInAPIJson), getRain(oneDaySeparationStepInAPIJson),getSnow(oneDaySeparationStepInAPIJson), getDescription(oneDaySeparationStepInAPIJson), getMinTemperature(oneDaySeparationStepInAPIJson), getMaxTemperature(oneDaySeparationStepInAPIJson), getUnixTimeStamp(oneDaySeparationStepInAPIJson));
            weathers.add(singleDayWeather);
            oneDaySeparationStepInAPIJson=oneDaySeparationStepInAPIJson+8;
            date = date.now().plusDays(oneDaySeparationStepInAPIJson/8);
            if (oneDaySeparationStepInAPIJson==40) {
                oneDaySeparationStepInAPIJson = 39;
                date = date.now().plusDays(5);
            }
        }

        WeatherForecast weatherForecast = new WeatherForecast(cityName, weathers);
        return weatherForecast;
    }




    private String getMaxTemperature(int forecastDayNumber) {
        return jsonManager.extractMaxTemperature(forecastDayNumber);
    }

    private String getMinTemperature(int forecastDayNumber) {
        return jsonManager.extractMinTemperature(forecastDayNumber);
    }

    String getTemperature(int forecastDayNumber) {
        return jsonManager.extractTemperature(forecastDayNumber);
    }

    String getFeelsLikeTemperature(int forecastDayNumber) {
        return jsonManager.extractFeelsLikeTemperature(forecastDayNumber);
    }

    int getWeatherIconId(int forecastDayNumber) {
        return jsonManager.extractWeatherIconId(forecastDayNumber);
    }

    String getWeatherPressure(int forecastDayNumber) {
        return jsonManager.extractWeatherPressure(forecastDayNumber);
    }

    String getRain(int forecastDayNumber) {
        return jsonManager.extractWeatherRain(forecastDayNumber);
    }

    String getSnow(int forecastDayNumber) { return jsonManager.extractWeatherSnow(forecastDayNumber); }

    String getDescription(int forecastDayNumber) {
        return jsonManager.extractWeatherDescription(forecastDayNumber);
    }

    long getUnixTimeStamp(int forecastDayNumber) {
        return jsonManager.extractUnixTimeStamp(forecastDayNumber);
    }

}
