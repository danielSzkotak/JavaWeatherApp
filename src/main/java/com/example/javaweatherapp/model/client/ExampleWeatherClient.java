package com.example.javaweatherapp.model.client;

import com.example.javaweatherapp.model.Weather;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;


public class ExampleWeatherClient implements WeatherClient{

    private static final String API_KEY = "d6d4d66e455fb01b0b1b210628a1dd91";

    @Override
    public Weather getWeather(String cityName) throws IOException {
        //MIEJSCE NA PRAWDZIWĄ IMPLEMENTACJĘ

        // Make the HTTP request to the API
        try {
            CityApi cityApi = new CityApi();

            URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?" + cityApi.getCityCoorinates(cityName) + "&appid=" + API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw  new RuntimeException("ResonseCode " + responseCode);
            } else {
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

                return new Weather(cityName, getCurrentDateTemp(json), LocalDate.now());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    String getCurrentDateTemp (JsonObject json){

        final JsonArray list = json.getJsonArray("list");
        final JsonObject forecast = list.getJsonObject(0);
        final JsonObject main = forecast.getJsonObject("main");
        final double temp = main.getJsonNumber("temp").doubleValue() - 273.15;
        /*BigDecimal bd = new BigDecimal(temp);
        bd = bd.setScale(0, RoundingMode.HALF_UP);
        double result = bd.doubleValue();*/
        DecimalFormat df = new DecimalFormat("#");
        String formattedValue = df.format(temp);
        return formattedValue + "°C";
    }


}
