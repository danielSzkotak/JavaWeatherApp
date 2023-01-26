package com.example.javaweatherapp.model.client;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIClientService {

    private final String API_KEY = "d6d4d66e455fb01b0b1b210628a1dd91";

    public String getAPI_KEY() {

        return API_KEY;
    }

    public String getCityCoordinates(String cityName){

        try {
            URL url = new URL("http://api.openweathermap.org/geo/1.0/direct?q=" + cityName + "&limit=5&appid=" + API_KEY);

            JsonReader jsonReader = getJsonFromAPI(url);
            JsonArray json = jsonReader.readArray();
            jsonReader.close();

            final JsonObject city1 = json.getJsonObject(0);
            final double lat = city1.getJsonNumber("lat").doubleValue();
            final double lon = city1.getJsonNumber("lon").doubleValue();

            String coordinates = "lat=" + String.valueOf(lat) + "&lon=" + String.valueOf(lon);
            return coordinates;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JsonReader getJsonFromAPI(URL url) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("ResonseCode " + responseCode);
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
            return jsonReader;

        }
    }
}
