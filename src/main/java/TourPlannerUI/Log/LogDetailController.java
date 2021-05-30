package TourPlannerUI.Log;

import TourPlannerUI.model.TourLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.NumberStringConverter;

public class LogDetailController {

    @FXML
    public DatePicker dateField;
    @FXML
    public TextField startTimeField;
    @FXML
    public TextField distanceField;
    @FXML
    public TextField totalTimeField;
    @FXML
    public ChoiceBox ratingField;
    static ObservableList<Integer> ratingChoices = FXCollections.observableArrayList(
            0,
            1,
            2,
            3,
            4,
            5
    );
    @FXML
    public ChoiceBox exhaustingField;
    static ObservableList<Integer> exhaustingChoices = FXCollections.observableArrayList(
            0,
            1,
            2,
            3,
            4,
            5
    );
    @FXML
    public TextField averageSpeedField;
    @FXML
    public TextField caloriesField;
    @FXML
    public ChoiceBox breaksField;
    static ObservableList<String> breaksChoices = FXCollections.observableArrayList(
            "0",
            "1",
            "2",
            "3",
            "4",
            "5",
            "5+"
    );
    @FXML
    public ChoiceBox weatherField;
    static ObservableList<String> weatherChoices = FXCollections.observableArrayList(
            "Sun",
            "Clouds",
            "Snow",
            "Wind",
            "Rain",
            "Fog"
    );
    @FXML
    public TextArea reportArea;

    private TourLog tourLog;

    public void setLog(TourLog tourLog){
        this.tourLog = tourLog;
        dateField.valueProperty().bindBidirectional(tourLog.getDateProperty());
        startTimeField.textProperty().bindBidirectional(tourLog.getStartTimeProperty());
        totalTimeField.textProperty().bindBidirectional(tourLog.getTotalTimeProperty());
        distanceField.textProperty().bindBidirectional(tourLog.getDistanceProperty(),new NumberStringConverter());

        ratingField.setItems(ratingChoices);
        ratingField.valueProperty().bindBidirectional(tourLog.getRatingProperty());
        exhaustingField.setItems(exhaustingChoices);
        exhaustingField.valueProperty().bindBidirectional(tourLog.getExhaustingProperty());
        averageSpeedField.textProperty().bindBidirectional(tourLog.getAverageSpeedProperty(),new NumberStringConverter());
        caloriesField.textProperty().bindBidirectional(tourLog.getCaloriesProperty(),new NumberStringConverter());
        breaksField.setItems(breaksChoices);
        breaksField.valueProperty().bindBidirectional(tourLog.getBreaksProperty());
        weatherField.setItems(weatherChoices);
        weatherField.valueProperty().bindBidirectional(tourLog.getWeatherProperty());
        reportArea.textProperty().bindBidirectional(tourLog.getReportProperty());

/*
        Button okButton = (Button)dialogPane.lookuButton(ButtonType.OK)
            okButton.addEventFilter(
                    ActionEvent.ACTION, event -> {
                        if (!validateFormData()){

                        }
                    }
            );*/
    }
}

