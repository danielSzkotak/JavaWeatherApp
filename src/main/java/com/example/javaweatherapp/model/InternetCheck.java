package com.example.javaweatherapp.model;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class InternetCheck {

    public static boolean isInternetReachable() {
        try {
            // Make a URL to a known source
            URL url = new URL("http://www.google.com");

            // Open a connection to that source
            HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection();

            // Try to retrieve data from the source. If there
            // is no connection, this line will fail
            Object objData = urlConnect.getContent();

        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
