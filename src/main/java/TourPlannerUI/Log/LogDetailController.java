package TourPlannerUI.Log;

import TourPlannerUI.ViewModel;
import TourPlannerUI.model.TourLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.NumberStringConverter;

public class LogDetailController {

    public LogDetailModel viewModel = new LogDetailModel();

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
    @FXML
    public ChoiceBox exhaustingField;
    @FXML
    public TextField averageSpeedField;
    @FXML
    public TextField caloriesField;
    @FXML
    public ChoiceBox breaksField;
    @FXML
    public ChoiceBox weatherField;
    @FXML
    public TextArea reportArea;

    private TourLog tourLog;

    public void setLog(TourLog tourLog, Dialog dialog){

        this.tourLog = tourLog;
        dateField.valueProperty().bindBidirectional(tourLog.getDateProperty());
        startTimeField.textProperty().bindBidirectional(tourLog.getStartTimeProperty());
        totalTimeField.textProperty().bindBidirectional(tourLog.getTotalTimeProperty());
        distanceField.textProperty().bindBidirectional(tourLog.getDistanceProperty(),new NumberStringConverter());

        ratingField.setItems(viewModel.getRatingChoices());
        ratingField.valueProperty().bindBidirectional(tourLog.getRatingProperty());
        exhaustingField.setItems(viewModel.getExhaustingChoices());
        exhaustingField.valueProperty().bindBidirectional(tourLog.getExhaustingProperty());
        averageSpeedField.textProperty().bindBidirectional(tourLog.getAverageSpeedProperty(),new NumberStringConverter());
        caloriesField.textProperty().bindBidirectional(tourLog.getCaloriesProperty(),new NumberStringConverter());
        breaksField.setItems(viewModel.getBreaksChoices());
        breaksField.valueProperty().bindBidirectional(tourLog.getBreaksProperty());
        weatherField.setItems(viewModel.getWeatherChoices());
        weatherField.valueProperty().bindBidirectional(tourLog.getWeatherProperty());
        reportArea.textProperty().bindBidirectional(tourLog.getReportProperty());
        Button okButton = (Button)dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.addEventFilter(
            ActionEvent.ACTION, event -> {
                if (!validateFormData()){
                    event.consume();
                }
        });
    }

    public boolean validateFormData() {
        if(dateField.getValue() == null){
            new Alert(Alert.AlertType.ERROR,"Missing Data, please enter the date").show();
            dateField.requestFocus();
            return false;
        }
        if(startTimeField.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Missing Data, please enter the start time").show();
            startTimeField.requestFocus();
            return false;
        }
        if(!startTimeField.getText().matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")){
            new Alert(Alert.AlertType.ERROR,"Start time incorrect. ^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$").show();
            startTimeField.requestFocus();
            return false;
        }
        if(distanceField.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Missing Data, please enter the distance").show();
            distanceField.requestFocus();
            return false;
        }
        if(!distanceField.getText().matches("[+-]?([0-9]*[,])?[0-9]+")){
            new Alert(Alert.AlertType.ERROR,"Distance incorrect. [+-]?([0-9]*[,])?[0-9]+").show();
            distanceField.requestFocus();
            return false;
        }
        if(totalTimeField.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Missing Data, please enter the total time").show();
            totalTimeField.requestFocus();
            return false;
        }
        if(!totalTimeField.getText().matches("[0-9]+:[0-5][0-9]")){
            new Alert(Alert.AlertType.ERROR,"Total time incorrect. [0-9]+:[0-5][0-9]").show();
            totalTimeField.requestFocus();
            return false;
        }
        if(averageSpeedField.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Missing Data, please enter the average speed").show();
            averageSpeedField.requestFocus();
            return false;
        }
        if(!averageSpeedField.getText().matches("[+-]?([0-9]*[,])?[0-9]+")){
            new Alert(Alert.AlertType.ERROR,"Average speed incorrect. [+-]?([0-9]*[,])?[0-9]+").show();
            averageSpeedField.requestFocus();
            return false;
        }
        if(caloriesField.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Missing Data, please enter the calories").show();
            caloriesField.requestFocus();
            return false;
        }
        if(!caloriesField.getText().matches("[+-]?([0-9]*[,])?[0-9]+")){
            new Alert(Alert.AlertType.ERROR,"Calories incorrect. [+-]?([0-9]*[,])?[0-9]+").show();
            caloriesField.requestFocus();
            return false;
        }
        if(weatherField.getValue().equals("-")){
            new Alert(Alert.AlertType.ERROR,"Missing Data, please select the weather").show();
            weatherField.requestFocus();
            return false;
        }
        if(reportArea.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Missing Data, please enter the report").show();
            reportArea.requestFocus();
            return false;
        }
        return true;
    }
}

