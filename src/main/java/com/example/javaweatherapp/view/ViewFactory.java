package com.example.javaweatherapp.view;

import com.example.javaweatherapp.controller.BaseController;
import com.example.javaweatherapp.controller.MainWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ViewFactory {

    private double xOffset = 0;
    private double yOffset = 0;

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
        stage.initStyle(StageStyle.UNDECORATED);

        parent.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        parent.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });


        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();

    }

    public void closeStage(Stage stageToCLose){
        stageToCLose.close();

    }

    public void minimizeStage(Stage stageToCLose){
        stageToCLose.setIconified(true);

    }
}
