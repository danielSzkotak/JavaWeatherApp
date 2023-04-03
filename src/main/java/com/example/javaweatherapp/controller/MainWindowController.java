package com.example.javaweatherapp.controller;

import com.example.javaweatherapp.model.*;
import com.example.javaweatherapp.model.client.Locations;
import com.example.javaweatherapp.view.ViewFactory;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class MainWindowController extends BaseController implements Initializable {


    @FXML
    private Label cityLbl;

    @FXML
    private Label currentDateLbl;

    @FXML
    private Label weatherDescriptionLbl;

    @FXML
    private Label humidityLbl;

    @FXML
    private Label windLbl;

    @FXML
    private Label pressureLbl;

    @FXML
    private Pane leftPane;

    @FXML
    private Button getWeatherBtn;

    @FXML
    private ImageView searchIcon;

    @FXML
    private Button closeBtn;

    @FXML
    private Button minimizeBtn;

    @FXML
    private Label errorlbl;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Label firstCityLabel;

    @FXML
    private Label temperatureLbl;

    @FXML
    private ImageView loadingImage;

    @FXML
    private Label rainLbl1;

    @FXML
    private ImageView weatherIconImageView;

    @FXML
    private Label feelsLikeTemperatureLbl;

    private WeatherService weatherService;
    private LocationService locationService;
    private String cityName;

    int cityIndexFromComboBox;


    public MainWindowController(ViewFactory viewFactory, String fxmlName) {

        super(viewFactory, fxmlName);
    }

    @FXML
    void closeActionBtn() {
        Stage stage = (Stage) cityLbl.getScene().getWindow();
        viewFactory.closeStage(stage);

    }

    @FXML
    void minimizeActionBtn() {
        Stage stage = (Stage) cityLbl.getScene().getWindow();
        viewFactory.minimizeStage(stage);
    }


    @FXML
    void getWeatherOnEnterPressed(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER){
            showWeatherActionBtn();
        }

    }

    @FXML
    void showWeatherActionBtn() {


        this.cityName = comboBox.getEditor().getText();
        if ((cityName != null) && (!cityName.isEmpty())){

            locationService.setCityName(cityName);
            locationService.setOnSucceeded(workerStateEvent -> {

                Locations locations = locationService.getValue();
                populateInputBoxWithLocations(locations.getLocations());
                getWeatherForSelectedLocations(locations.getLocations());

                if (locations.getLocations().isEmpty()) {
                    errorlbl.setText("Wprowadź poprawną nazwe miasta");
                    comboBox.hide();
                    errorlbl.setVisible(true);
                }
            });

            locationService.setOnFailed(workerStateEvent -> {
                Exception ex = (Exception) locationService.getException();
                System.out.println(ex.getMessage());
            });

            locationService.restart();
            weatherService.setOnSucceeded(workerStateEvent -> {

                errorlbl.setVisible(false);
                loadingImage.setVisible(false);
                WeatherForecast weatherForecast = weatherService.getValue();
                displayWeather(weatherForecast.getWeathers());
                comboBox.getItems().clear();
            });

            weatherService.setOnFailed(workerStateEvent -> {
                Exception ex = (Exception) weatherService.getException();
                System.out.println(ex.getMessage());
                loadingImage.setVisible(false);
            });

            weatherService.setOnRunning(workerStateEvent -> {
                loadingImage.setVisible(true);
                temperatureLbl.setVisible(false);

            });
        } else {
            errorlbl.setText("Wprowadź poprawną nazwe miasta");
            errorlbl.setVisible(true);
        }

    }


    private void populateInputBoxWithLocations(ArrayList<ArrayList<String>> locations){
        comboBox.getItems().clear();
        for (int i=0; i<locations.size(); i++){
            comboBox.getItems().add(locations.get(i).get(0) + " " + locations.get(i).get(1));
        }
        comboBox.show();
    }

    private void getWeatherForSelectedLocations(ArrayList<ArrayList<String>> locations){

        comboBox.setOnAction(actionEvent -> {

            int cityIndexFromComboBox = comboBox.getSelectionModel().getSelectedIndex();
            if (cityIndexFromComboBox >= 0) {
                weatherService.setCityCoordinates("lat=" + locations.get(cityIndexFromComboBox).get(2).toString() + "&lon=" + locations.get(cityIndexFromComboBox).get(3).toString());
                weatherService.restart();
            }
        });
    }

    private void displayWeather(List<SingleDayWeather> weathers){

        temperatureLbl.setText(weathers.get(0).getTempInCelsius());
        temperatureLbl.setVisible(true);
        feelsLikeTemperatureLbl.setText("Odczuwalna: " + weathers.get(0).getFeelsLikeTemperature());
        weatherDescriptionLbl.setText(weathers.get(0).getDescription());
        pressureLbl.setText("Ciśnienie: " + weathers.get(0).getPressure() + "hPa");
        humidityLbl.setText("Wilgotność: " + weathers.get(0).getHumidity() + "%");
        windLbl.setText("Wiatr: " + weathers.get(0).getWind());
        rainLbl1.setText("Deszcz: " + weathers.get(0).getRain());


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM", new Locale("pl"));
        String dayAndMonth = weathers.get(0).getDate().format(formatter);
        cityLbl.setText(makeFirstLetterCapital(cityName) + ", " + dayAndMonth);
        cityLbl.setVisible(true);


        try {
            weatherIconImageView.setImage(new Image(getClass().getResourceAsStream("/icons/iconList/" + weathers.get(0).getIcon() + ".png")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    String makeFirstLetterCapital(String input){
        String modifiedString = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
        return modifiedString;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            locationService = WeatherServiceFactory.createLocationsService();
            weatherService = WeatherServiceFactory.createWeatherService();

            searchIcon.setImage(new Image(getClass().getResourceAsStream("/icons/search.jpg")));
            comboBox.setPromptText("Wprowadź nazwę miejscowości");



            temperatureLbl.setVisible(false);
            loadingImage.setVisible(false);
            cityLbl.setVisible(false);
            errorlbl.setVisible(false);
            loadingImage.setImage(new Image(getClass().getResourceAsStream("/icons/loader.gif")));
    }

}
