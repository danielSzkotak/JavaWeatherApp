package com.example.javaweatherapp.model.client;

import com.example.javaweatherapp.model.SingleDayWeather;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import javax.json.JsonObject;
import javax.json.JsonReader;


public class OpenWeatherMapClient implements WeatherClient {

    private JsonManager jsonManager;


    @Override
    public SingleDayWeather getWeather(String cityName) throws IOException {

        try {
            APIClientService apiClientService = new APIClientService();
            URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?" + apiClientService.getCityCoordinates(cityName) +
                    "&appid=" + apiClientService.getAPI_KEY() + "&lang=pl");
            JsonReader jsonReader = apiClientService.getJsonFromAPI(url);
            JsonObject json = jsonReader.readObject();
            jsonReader.close();

            this.jsonManager = new JsonManager(json);

            return new SingleDayWeather(cityName, getTemperature(), LocalDate.now(), getFeelsLikeTemperature(),
                    getWeatherIconId(), getWeatherPressure(), getRain(), getDescription(), getMinTemperature(), getMaxTemperature());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getMaxTemperature() {
        return jsonManager.extractMaxTemperature(0);
    }

    private String getMinTemperature() {
        return jsonManager.extractMinTemperature(0);
    }

    String getTemperature() {
        return jsonManager.extractTemperature(0);
    }

    String getFeelsLikeTemperature() {
        return jsonManager.extractFeelsLikeTemperature(0);
    }

    int getWeatherIconId() {
        return jsonManager.extractWeatherIconId(0);
    }

    String getWeatherPressure() {
        return jsonManager.extractWeatherPressure(0);
    }

    String getRain() {
        return jsonManager.extractWeatherRain(0);
    }

    String getDescription() {
        return jsonManager.extractWeatherDescription(0);
    }

}
