package com.example.javaweatherapp.controller;

import com.example.javaweatherapp.model.*;
import com.example.javaweatherapp.model.client.Locations;
import com.example.javaweatherapp.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class MainWindowController extends BaseController implements Initializable {


    @FXML
    private Label cityLbl;

    @FXML
    private Label cityLbl2;

    @FXML
    private Label currentDateLbl;

    @FXML
    private Label weatherDescriptionLbl;

    @FXML
    private Label weatherDescriptionLbl2;

    @FXML
    private Label humidityLbl;

    @FXML
    private Label humidityLbl2;

    @FXML
    private Label dateLbl_1;

    @FXML
    private Label dateLbl_2;

    @FXML
    private Label dateLbl_3;

    @FXML
    private Label dateLbl_4;

    @FXML
    private Label dateLbl_5;

    @FXML
    private Label dateLbl_21;

    @FXML
    private Label dateLbl_22;

    @FXML
    private Label dateLbl_23;

    @FXML
    private Label dateLbl_24;

    @FXML
    private Label dateLbl_25;

    @FXML
    private Label dayNameLbl_1;

    @FXML
    private Label dayNameLbl_2;

    @FXML
    private Label dayNameLbl_3;

    @FXML
    private Label dayNameLbl_4;

    @FXML
    private Label dayNameLbl_5;

    @FXML
    private Label dayNameLbl_21;

    @FXML
    private Label dayNameLbl_22;

    @FXML
    private Label dayNameLbl_23;

    @FXML
    private Label dayNameLbl_24;

    @FXML
    private Label dayNameLbl_25;

    @FXML
    private Label windLbl;

    @FXML
    private Label windLbl2;

    @FXML
    private Label pressureLbl;

    @FXML
    private Label pressureLbl2;

    @FXML
    private Pane leftPane;

    @FXML
    private Button getWeatherBtn;

    @FXML
    private Button getWeatherBtn2;

    @FXML
    private ImageView searchIcon;

    @FXML
    private ImageView searchIcon2;

    @FXML
    private Button closeBtn;

    @FXML
    private Button minimizeBtn;

    @FXML
    private Label errorlbl;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private ComboBox<String> comboBox2;

    @FXML
    private ImageView weatherIconImageView_1;

    @FXML
    private ImageView weatherIconImageView_2;

    @FXML
    private ImageView weatherIconImageView_3;

    @FXML
    private ImageView weatherIconImageView_4;

    @FXML
    private ImageView weatherIconImageView_5;

    @FXML
    private ImageView weatherIconImageView_21;

    @FXML
    private ImageView weatherIconImageView_22;

    @FXML
    private ImageView weatherIconImageView_23;

    @FXML
    private ImageView weatherIconImageView_24;

    @FXML
    private ImageView weatherIconImageView_25;

    @FXML
    private Label firstCityLabel;

    @FXML
    private Label temperatureLbl;

    @FXML
    private Label temperatureLbl2;

    @FXML
    private Label forecastTemp_1;

    @FXML
    private Label forecastTemp_2;

    @FXML
    private Label forecastTemp_3;

    @FXML
    private Label forecastTemp_4;

    @FXML
    private Label forecastTemp_5;

    @FXML
    private Label forecastTemp_21;

    @FXML
    private Label forecastTemp_22;

    @FXML
    private Label forecastTemp_23;

    @FXML
    private Label forecastTemp_24;

    @FXML
    private Label forecastTemp_25;

    @FXML
    private ImageView loadingImage1;

    @FXML
    private ImageView loadingImage2;

    @FXML
    private Label rainLbl1;

    @FXML
    private Label rainLbl2;

    @FXML
    private ImageView weatherIconImageView;

    @FXML
    private ImageView weatherIconImageView2;

    @FXML
    private Label feelsLikeTemperatureLbl;

    @FXML
    private Label feelsLikeTemperatureLbl2;

    @FXML
    private VBox leftWeatherVbox;

    @FXML
    private VBox rightWeatherVbox;

    @FXML
    private Label rightWelcomeLbl;

    @FXML
    private Label leftWelcomeLbl;

    private WeatherService weatherService;
    private LocationService locationService;
    private String cityName;


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

        if (event.getCode() == KeyCode.ENTER) {
            if (comboBox.isFocused()) {
                showWeatherActionBtn();
            }
            if (comboBox2.isFocused()) {
                showWeatherActionBtn2();
            }

        }
    }


    @FXML
    void showWeatherActionBtn() {

        this.cityName = comboBox.getEditor().getText();
        if ((cityName != null) && (!cityName.isEmpty())) {

            locationService.setCityName(cityName);
            getLocationOnSucceed();
            getLocationOnFailed();
            locationService.restart();
            getWeatherOnSucceeded();
            getWeatherOnFailed();

            weatherService.setOnRunning(workerStateEvent -> {
                leftWelcomeLbl.setVisible(false);
                loadingImage1.setVisible(true);
            });

        } else {
            errorlbl.setText("Wprowadź poprawną nazwę miasta");
            errorlbl.setVisible(true);
        }
    }

    private void getLocationOnSucceed() {
        locationService.setOnSucceeded(workerStateEvent -> {
            Locations locations = locationService.getValue();
            populateInputBoxWithLocations(locations.getLocations());
            getWeatherForSelectedLocations(locations.getLocations());
            if (locations.getLocations().isEmpty()) {
                errorlbl.setText("Wprowadź poprawną nazwę miasta");
                comboBox.hide();
                errorlbl.setVisible(true);
            }
        });
    }

    private void getLocationOnFailed() {
        locationService.setOnFailed(workerStateEvent -> {
            Exception ex = (Exception) locationService.getException();
            System.out.println(ex.getMessage());
        });
    }

    private void getWeatherOnSucceeded() {
        weatherService.setOnSucceeded(workerStateEvent -> {
            errorlbl.setVisible(false);
            loadingImage1.setVisible(false);
            leftWelcomeLbl.setVisible(false);
            leftWeatherVbox.setVisible(true);
            WeatherForecast weatherForecast = weatherService.getValue();
            displayWeather1(weatherForecast.getWeathers());
            comboBox.getItems().clear();
        });
    }

    private void getWeatherOnFailed() {
        weatherService.setOnFailed(workerStateEvent -> {
            Exception ex = (Exception) weatherService.getException();
            System.out.println(ex.getMessage());
            loadingImage1.setVisible(false);
        });
    }

    @FXML
    void showWeatherActionBtn2() {
        this.cityName = comboBox2.getEditor().getText();
        if ((cityName != null) && (!cityName.isEmpty())) {
            locationService.setCityName(cityName);

            getLocationOnSucceeded2();
            getLocationOnFailed2();
            locationService.restart();
            getWeatherOnSucceeded2();
            getWeatherOnFailed2();

            weatherService.setOnRunning(workerStateEvent -> {
                rightWelcomeLbl.setVisible(false);
                loadingImage2.setVisible(true);

            });
        } else {
            errorlbl.setText("Wprowadź poprawną nazwe miasta");
            errorlbl.setVisible(true);
        }

    }

    private void getLocationOnSucceeded2() {
        locationService.setOnSucceeded(workerStateEvent -> {

            Locations locations = locationService.getValue();
            populateInputBox2WithLocations(locations.getLocations());
            getWeatherForSelectedLocations2(locations.getLocations());

            if (locations.getLocations().isEmpty()) {
                errorlbl.setText("Wprowadź poprawną nazwę miasta");
                comboBox2.hide();
                errorlbl.setVisible(true);
            }
        });
    }

    private void getLocationOnFailed2() {
        locationService.setOnFailed(workerStateEvent -> {
            Exception ex = (Exception) locationService.getException();
            System.out.println(ex.getMessage());
        });
    }

    private void getWeatherOnSucceeded2() {
        weatherService.setOnSucceeded(workerStateEvent -> {
            errorlbl.setVisible(false);
            loadingImage2.setVisible(false);
            rightWelcomeLbl.setVisible(false);
            rightWeatherVbox.setVisible(true);
            WeatherForecast weatherForecast = weatherService.getValue();
            displayWeather2(weatherForecast.getWeathers());
            comboBox2.getItems().clear();
        });
    }

    private void getWeatherOnFailed2() {
        weatherService.setOnFailed(workerStateEvent -> {
            Exception ex = (Exception) weatherService.getException();
            System.out.println(ex.getMessage());
            loadingImage2.setVisible(false);
        });
    }

    private void populateInputBoxWithLocations(ArrayList<ArrayList<String>> locations) {
        comboBox.getItems().clear();
        for (int i = 0; i < locations.size(); i++) {
            comboBox.getItems().add(locations.get(i).get(0) + " " + locations.get(i).get(1));
        }
        comboBox.show();
    }

    private void populateInputBox2WithLocations(ArrayList<ArrayList<String>> locations) {
        comboBox2.getItems().clear();
        for (int i = 0; i < locations.size(); i++) {
            comboBox2.getItems().add(locations.get(i).get(0) + " " + locations.get(i).get(1));
        }
        comboBox2.show();
    }

    private void getWeatherForSelectedLocations(ArrayList<ArrayList<String>> locations) {

        comboBox.setOnAction(actionEvent -> {
            int cityIndexFromComboBox = comboBox.getSelectionModel().getSelectedIndex();
            if (cityIndexFromComboBox >= 0) {
                weatherService.setCityCoordinates("lat=" + locations.get(cityIndexFromComboBox).get(2).toString() + "&lon=" + locations.get(cityIndexFromComboBox).get(3).toString());
                weatherService.restart();
            }
        });
    }

    private void getWeatherForSelectedLocations2(ArrayList<ArrayList<String>> locations) {

        comboBox2.setOnAction(actionEvent -> {

            int cityIndexFromComboBox = comboBox2.getSelectionModel().getSelectedIndex();
            if (cityIndexFromComboBox >= 0) {
                weatherService.setCityCoordinates("lat=" + locations.get(cityIndexFromComboBox).get(2).toString() + "&lon=" + locations.get(cityIndexFromComboBox).get(3).toString());
                weatherService.restart();
            }
        });
    }

    private void displayWeather1(List<SingleDayWeather> weathers) {

        temperatureLbl.setText(weathers.get(0).getTempInCelsius());

        feelsLikeTemperatureLbl.setText("Odczuwalna: " + weathers.get(0).getFeelsLikeTemperature());
        weatherDescriptionLbl.setText(weathers.get(0).getDescription());
        pressureLbl.setText("Ciśnienie: " + weathers.get(0).getPressure() + "hPa");
        humidityLbl.setText("Wilgotność: " + weathers.get(0).getHumidity() + "%");
        windLbl.setText("Wiatr: " + weathers.get(0).getWind());
        rainLbl1.setText("Deszcz: " + weathers.get(0).getRain());

        DateTimeFormatter forecastDayNameFormatter = DateTimeFormatter.ofPattern("E", new Locale("pl"));
        dayNameLbl_1.setText(makeFirstLetterCapital(weathers.get(1).getDate().format(forecastDayNameFormatter)));
        dayNameLbl_2.setText(makeFirstLetterCapital(weathers.get(2).getDate().format(forecastDayNameFormatter)));
        dayNameLbl_3.setText(makeFirstLetterCapital(weathers.get(3).getDate().format(forecastDayNameFormatter)));
        dayNameLbl_4.setText(makeFirstLetterCapital(weathers.get(4).getDate().format(forecastDayNameFormatter)));
        dayNameLbl_5.setText(makeFirstLetterCapital(weathers.get(5).getDate().format(forecastDayNameFormatter)));

        DateTimeFormatter forecastFormatter = DateTimeFormatter.ofPattern("d MMM", new Locale("pl"));
        dateLbl_1.setText(weathers.get(1).getDate().format(forecastFormatter));
        dateLbl_2.setText(weathers.get(2).getDate().format(forecastFormatter));
        dateLbl_3.setText(weathers.get(3).getDate().format(forecastFormatter));
        dateLbl_4.setText(weathers.get(4).getDate().format(forecastFormatter));
        dateLbl_5.setText(weathers.get(5).getDate().format(forecastFormatter));

        forecastTemp_1.setText(weathers.get(1).getTempInCelsius() + "/" + weathers.get(1).getFeelsLikeTemperature());
        forecastTemp_2.setText(weathers.get(2).getTempInCelsius() + "/" + weathers.get(1).getFeelsLikeTemperature());
        forecastTemp_3.setText(weathers.get(3).getTempInCelsius() + "/" + weathers.get(1).getFeelsLikeTemperature());
        forecastTemp_4.setText(weathers.get(4).getTempInCelsius() + "/" + weathers.get(1).getFeelsLikeTemperature());
        forecastTemp_5.setText(weathers.get(5).getTempInCelsius() + "/" + weathers.get(1).getFeelsLikeTemperature());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE d MMMM", new Locale("pl"));
        String dayAndMonth = weathers.get(0).getDate().format(formatter);

        cityLbl.setText(makeFirstLetterCapital(cityName) + ", " + dayAndMonth);

        try {
            weatherIconImageView.setImage(new Image(getClass().getResourceAsStream("/icons/iconList/" + weathers.get(0).getIcon() + ".png")));
            weatherIconImageView_1.setImage(new Image(getClass().getResourceAsStream("/icons/iconList/" + weathers.get(1).getIcon() + ".png")));
            weatherIconImageView_2.setImage(new Image(getClass().getResourceAsStream("/icons/iconList/" + weathers.get(2).getIcon() + ".png")));
            weatherIconImageView_3.setImage(new Image(getClass().getResourceAsStream("/icons/iconList/" + weathers.get(3).getIcon() + ".png")));
            weatherIconImageView_4.setImage(new Image(getClass().getResourceAsStream("/icons/iconList/" + weathers.get(4).getIcon() + ".png")));
            weatherIconImageView_5.setImage(new Image(getClass().getResourceAsStream("/icons/iconList/" + weathers.get(5).getIcon() + ".png")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void displayWeather2(List<SingleDayWeather> weathers) {

        temperatureLbl2.setText(weathers.get(0).getTempInCelsius());

        feelsLikeTemperatureLbl2.setText("Odczuwalna: " + weathers.get(0).getFeelsLikeTemperature());
        weatherDescriptionLbl2.setText(weathers.get(0).getDescription());
        pressureLbl2.setText("Ciśnienie: " + weathers.get(0).getPressure() + "hPa");
        humidityLbl2.setText("Wilgotność: " + weathers.get(0).getHumidity() + "%");
        windLbl2.setText("Wiatr: " + weathers.get(0).getWind());
        rainLbl2.setText("Deszcz: " + weathers.get(0).getRain());

        DateTimeFormatter forecastDayNameFormatter = DateTimeFormatter.ofPattern("E", new Locale("pl"));
        dayNameLbl_21.setText(makeFirstLetterCapital(weathers.get(1).getDate().format(forecastDayNameFormatter)));
        dayNameLbl_22.setText(makeFirstLetterCapital(weathers.get(2).getDate().format(forecastDayNameFormatter)));
        dayNameLbl_23.setText(makeFirstLetterCapital(weathers.get(3).getDate().format(forecastDayNameFormatter)));
        dayNameLbl_24.setText(makeFirstLetterCapital(weathers.get(4).getDate().format(forecastDayNameFormatter)));
        dayNameLbl_25.setText(makeFirstLetterCapital(weathers.get(5).getDate().format(forecastDayNameFormatter)));

        DateTimeFormatter forecastFormatter = DateTimeFormatter.ofPattern("d MMM", new Locale("pl"));
        dateLbl_21.setText(weathers.get(1).getDate().format(forecastFormatter));
        dateLbl_22.setText(weathers.get(2).getDate().format(forecastFormatter));
        dateLbl_23.setText(weathers.get(3).getDate().format(forecastFormatter));
        dateLbl_24.setText(weathers.get(4).getDate().format(forecastFormatter));
        dateLbl_25.setText(weathers.get(5).getDate().format(forecastFormatter));

        forecastTemp_21.setText(weathers.get(1).getTempInCelsius() + "/" + weathers.get(1).getFeelsLikeTemperature());
        forecastTemp_22.setText(weathers.get(2).getTempInCelsius() + "/" + weathers.get(1).getFeelsLikeTemperature());
        forecastTemp_23.setText(weathers.get(3).getTempInCelsius() + "/" + weathers.get(1).getFeelsLikeTemperature());
        forecastTemp_24.setText(weathers.get(4).getTempInCelsius() + "/" + weathers.get(1).getFeelsLikeTemperature());
        forecastTemp_25.setText(weathers.get(5).getTempInCelsius() + "/" + weathers.get(1).getFeelsLikeTemperature());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE d MMMM", new Locale("pl"));
        String dayAndMonth = weathers.get(0).getDate().format(formatter);

        cityLbl2.setText(makeFirstLetterCapital(cityName) + ", " + dayAndMonth);

        try {
            weatherIconImageView2.setImage(new Image(getClass().getResourceAsStream("/icons/iconList/" + weathers.get(0).getIcon() + ".png")));
            weatherIconImageView_21.setImage(new Image(getClass().getResourceAsStream("/icons/iconList/" + weathers.get(1).getIcon() + ".png")));
            weatherIconImageView_22.setImage(new Image(getClass().getResourceAsStream("/icons/iconList/" + weathers.get(2).getIcon() + ".png")));
            weatherIconImageView_23.setImage(new Image(getClass().getResourceAsStream("/icons/iconList/" + weathers.get(3).getIcon() + ".png")));
            weatherIconImageView_24.setImage(new Image(getClass().getResourceAsStream("/icons/iconList/" + weathers.get(4).getIcon() + ".png")));
            weatherIconImageView_25.setImage(new Image(getClass().getResourceAsStream("/icons/iconList/" + weathers.get(5).getIcon() + ".png")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    String makeFirstLetterCapital(String input) {
        String modifiedString = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
        return modifiedString;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        locationService = WeatherServiceFactory.createLocationsService();
        weatherService = WeatherServiceFactory.createWeatherService();
        searchIcon.setImage(new Image(getClass().getResourceAsStream("/icons/search.jpg")));
        searchIcon2.setImage(new Image(getClass().getResourceAsStream("/icons/search.jpg")));
        comboBox.setPromptText("Wprowadź nazwę miejscowości");
        comboBox2.setPromptText("Wprowadź nazwę miejscowości");
        leftWeatherVbox.setVisible(false);
        rightWeatherVbox.setVisible(false);
        leftWelcomeLbl.setVisible(true);
        rightWelcomeLbl.setVisible(true);
        loadingImage1.setVisible(false);
        loadingImage2.setVisible(false);
        errorlbl.setVisible(false);
        loadingImage1.setImage(new Image(getClass().getResourceAsStream("/icons/loader.gif")));
        loadingImage2.setImage(new Image(getClass().getResourceAsStream("/icons/loader.gif")));
    }

}
