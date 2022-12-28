package com.example.javaweatherapp.model.client;

import com.example.javaweatherapp.model.Weather;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;


public class ExampleWeatherClient implements WeatherClient{

    private static final String API_KEY = "d6d4d66e455fb01b0b1b210628a1dd91";

    @Override
    public Weather getWeather(String cityName) throws IOException {
        //MIEJSCE NA PRAWDZIWĄ IMPLEMENTACJĘ

        // Make the HTTP request to the API
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=" + API_KEY);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Read the response from the API
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        System.out.println(response);

        String result = response.toString();

        // Parse the JSON string using the javax.json API
        JsonReader jsonReader = Json.createReader(new StringReader(result));
        JsonObject json = jsonReader.readObject();
        jsonReader.close();

        // Get the main object from the JSON object
        JsonObject main = json.getJsonObject("main");

        // Get the temperature from the main object
        double temp = main.getJsonNumber("temp").doubleValue() -  273.15;
        
        return new Weather(cityName, temp, LocalDate.now());
    }


}
