package com.example.javaweatherapp.view;

import com.example.javaweatherapp.controller.BaseController;
import com.example.javaweatherapp.controller.MainWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {

    public ViewFactory() {
    }

    public void showMainWindow(){
        BaseController controller = new MainWindowController(this, "MainWindow.fxml");
        initializeStage(controller,"JavaFX Email Client");

    }

    private void initializeStage(BaseController baseController, String title){
        FXMLLoader fxmlLoader  = new FXMLLoader(getClass().getResource(baseController.getFxmlName()));
        fxmlLoader.setController(baseController);

        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();

    }
}
