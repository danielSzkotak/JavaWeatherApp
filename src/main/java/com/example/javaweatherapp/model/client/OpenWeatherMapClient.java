package com.example.javaweatherapp.model.client;

import com.example.javaweatherapp.model.SingleDayWeather;
import com.example.javaweatherapp.model.WeatherForecast;
import org.javatuples.Quartet;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
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
    private ArrayList<ArrayList<String>> locations;
    private final String API_KEY = "d6d4d66e455fb01b0b1b210628a1dd91";


    @Override
    public WeatherForecast getWeather(String cityCoordinates) throws IOException {

        APIClientService apiClientService = new APIClientService();
        URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?" + cityCoordinates +
                "&appid=" + apiClientService.getAPI_KEY() + "&lang=pl");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            String result = response.toString();
            JsonReader jsonReader = Json.createReader(new StringReader(result));

            JsonObject json = jsonReader.readObject();
            jsonReader.close();

            this.jsonManager = new JsonManager(json);
            this.cityName = cityCoordinates;

            return populateWeathers();

        } catch (IOException e) {
            throw new IOException("Nie można pobrać pogody dla wskazanej lokalizacji z API OPenWeatherMap", e);
        }

    }

    @Override
    public ArrayList<ArrayList<String>> getLocations(String cityName) throws IOException, ConnectException {

        URL url = new URL("http://api.openweathermap.org/geo/1.0/direct?q=" + cityName + "&limit=5&appid=" + API_KEY);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            String result = response.toString();
            JsonReader jsonReader = Json.createReader(new StringReader(result));


            JsonArray json = jsonReader.readArray();
            jsonReader.close();

            //TRY TO ITERATE THRU JSON ARRAY TO GET QUARTETS
            ArrayList<ArrayList<String>> locations = new ArrayList<ArrayList<String>>();
            for (int i = 0; i < json.size(); i++) {
                JsonObject city = json.getJsonObject(i);
                locations.add(i, new ArrayList<String>(Arrays.asList(city.getJsonString("name").toString(), city.getJsonString("country").toString(), city.getJsonNumber("lat").toString(), city.getJsonNumber("lon").toString())));
            }
            return locations;

        } catch (ConnectException e) {
            throw new IOException("Brak połączenia  z internetem", e);

        } catch (IOException e) {
            throw new IOException("Nie można pobrać lokalizacji z API OpenWeatherMap", e);
        }

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

    String getWeatherIconId(int forecastDayNumber) {
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
