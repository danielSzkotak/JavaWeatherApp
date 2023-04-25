package com.example.javaweatherapp.model.client;

import com.example.javaweatherapp.model.SingleDayWeather;
import com.example.javaweatherapp.model.WeatherForecast;
import com.example.javaweatherapp.Config;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;


public class OpenWeatherMapClient implements WeatherClient {

    private JsonManager jsonManager;
    private String cityName;
    private List<SingleDayWeather> weathers = new ArrayList<>();
    Config config = new Config();
    private final String API_KEY = config.getAPI_KEY();


    @Override
    public WeatherForecast getWeather(String cityCoordinates) throws IOException {

        URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?" + cityCoordinates +
                "&appid=" + API_KEY + "&lang=pl");

        try {
            JsonObject json = getJsonFromAPI(url).readObject();
            this.jsonManager = new JsonManager(json);
            this.cityName = cityCoordinates;
            return populateWeathers();

        } catch (ConnectException e) {
            throw new IOException("Brak połączenia z internetem", e);

        } catch (IOException e) {
            throw new IOException("Nie można pobrać pogody dla wskazanej lokalizacji z API OPenWeatherMap", e);
        } finally {
            getJsonFromAPI(url).close();
        }

    }

    @Override
    public Locations getLocations(String cityName) throws IOException {

        URL url = new URL("http://api.openweathermap.org/geo/1.0/direct?q=" + cityName + "&limit=5&appid=" + API_KEY);

        try {
            JsonArray jsonArray = getJsonFromAPI(url).readArray();
            return populateLocationsFromJson(jsonArray);

        } catch (ConnectException e) {
            throw new IOException("Brak połączenia z internetem", e);

        } catch (IOException e) {
            throw new IOException("Nie można pobrać lokalizacji z API OpenWeatherMap", e);
        } finally {
            getJsonFromAPI(url).close();
        }

    }

    private Locations populateLocationsFromJson(JsonArray jsonArray){

        ArrayList<ArrayList<String>> loc = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject city = jsonArray.getJsonObject(i);
            loc.add(i, new ArrayList<String>(Arrays.asList(city.getJsonString("name").toString(), city.getJsonString("country").toString(), city.getJsonNumber("lat").toString(), city.getJsonNumber("lon").toString())));
        }
        Locations locations = new Locations();
        locations.setLocations(loc);

        return locations;
    }

    private JsonReader getJsonFromAPI(URL url) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            String result = response.toString();
            JsonReader jsonReader = Json.createReader(new StringReader(result));
            return jsonReader;

    }

    private WeatherForecast populateWeathers(){

        weathers.clear();
        int oneDaySeparationStepInAPIJson=0; // EVERY 8 STEP READING FROM API IS DAY PERIOD
        LocalDate date = LocalDate.parse("2018-11-13");
        date = date.now();
        
        while (oneDaySeparationStepInAPIJson<=40) {

            SingleDayWeather singleDayWeather = new SingleDayWeather(cityName, getTemperature(oneDaySeparationStepInAPIJson), date, getFeelsLikeTemperature(oneDaySeparationStepInAPIJson), getWeatherIconId(oneDaySeparationStepInAPIJson), getWeatherPressure(oneDaySeparationStepInAPIJson), getRain(oneDaySeparationStepInAPIJson), getSnow(oneDaySeparationStepInAPIJson), getWind(oneDaySeparationStepInAPIJson), getHumidity(oneDaySeparationStepInAPIJson), getDescription(oneDaySeparationStepInAPIJson), getMinTemperature(oneDaySeparationStepInAPIJson), getMaxTemperature(oneDaySeparationStepInAPIJson), getUnixTimeStamp(oneDaySeparationStepInAPIJson));
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

    String getWeatherIconId(int forecastDayNumber) {
        return jsonManager.extractWeatherIconId(forecastDayNumber);
    }

    String getWeatherPressure(int forecastDayNumber) {
        return jsonManager.extractWeatherPressure(forecastDayNumber);
    }

    String getHumidity(int forecastDayNumber) {
        return jsonManager.extractHumidity(forecastDayNumber);
    }

    String getRain(int forecastDayNumber) {
        return jsonManager.extractWeatherRain(forecastDayNumber);
    }

    String getSnow(int forecastDayNumber) { return jsonManager.extractWeatherSnow(forecastDayNumber); }

    String getWind(int forecastDayNumber) { return jsonManager.extractWeatherWind(forecastDayNumber); }

    String getDescription(int forecastDayNumber) {
        return jsonManager.extractWeatherDescription(forecastDayNumber);
    }

    long getUnixTimeStamp(int forecastDayNumber) {
        return jsonManager.extractUnixTimeStamp(forecastDayNumber);
    }

}
