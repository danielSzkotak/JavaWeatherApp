package com.example.javaweatherapp;

import com.example.javaweatherapp.view.ViewFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ViewFactory viewFactory = new ViewFactory();
        viewFactory.showMainWindow();
    }

    public static void main(String[] args) {
        launch();
    }
}