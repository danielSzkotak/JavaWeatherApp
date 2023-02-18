package com.example.javaweatherapp.model.client;

import org.javatuples.Quartet;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

public class APIClientService {

    private final String API_KEY = "d6d4d66e455fb01b0b1b210628a1dd91";

    public String getAPI_KEY() {

        return API_KEY;
    }


    public ArrayList<ArrayList<String>> getLocationsFromApi(String cityName) throws IOException, UnknownHostException{

        try {
            URL url = new URL("http://api.openweathermap.org/geo/1.0/direct?q=" + cityName + "&limit=5&appid=" + API_KEY);

            JsonReader jsonReader = getJsonFromAPI(url);
            if (jsonReader != null) {

                JsonArray json = jsonReader.readArray();
                jsonReader.close();

                //TRY TO ITERATE THRU JSON ARRAY TO GET QUARTETS
                ArrayList<ArrayList<String>> locations = new ArrayList<ArrayList<String>>();
                for (int i = 0; i < json.size(); i++) {
                    JsonObject city = json.getJsonObject(i);
                    locations.add(i, new ArrayList<String>(Arrays.asList(city.getJsonString("name").toString(), city.getJsonString("country").toString(), city.getJsonNumber("lat").toString(), city.getJsonNumber("lon").toString())));
                }
                return locations;
            }

        } catch (Exception e) {

            //e.printStackTrace();

        }

        return null;

    }

    public JsonReader getJsonFromAPI(URL url) throws IOException, UnknownHostException {

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new IOException(String.valueOf(responseCode));

            }else {
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
        } catch (Exception e){
            System.out.println("ujujujuj");
            //e.printStackTrace();
        }

        return null;
    }
}
