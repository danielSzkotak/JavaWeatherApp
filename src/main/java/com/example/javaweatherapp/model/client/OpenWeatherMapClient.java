package com.example.javaweatherapp.model.client;

import com.example.javaweatherapp.model.SingleDayWeather;
import com.example.javaweatherapp.model.WeatherForecast;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
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

            /*return new SingleDayWeather(cityName, getTemperature(0), LocalDate.now(), getFeelsLikeTemperature(0),
                    getWeatherIconId(0), getWeatherPressure(0), getRain(0), getDescription(0), getMinTemperature(0), getMaxTemperature(0))*/;
            return populateWeathers();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private WeatherForecast populateWeathers(){

        weathers.clear();
        int i=0;
        while (i<=40) {

            SingleDayWeather singleDayWeather = new SingleDayWeather(cityName, getTemperature(i), LocalDate.now(), getFeelsLikeTemperature(i), getWeatherIconId(i), getWeatherPressure(i), getRain(i), getDescription(i), getMinTemperature(i), getMaxTemperature(i), getUnixTimeStamp(i));
            weathers.add(singleDayWeather);
            i=i+8;
            if (i==40) {
                i = 39;
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

    String getDescription(int forecastDayNumber) {
        return jsonManager.extractWeatherDescription(forecastDayNumber);
    }

    String getUnixTimeStamp(int forecastDayNumber) {
        return jsonManager.extractUnixTimeStamp(forecastDayNumber);
    }

}
