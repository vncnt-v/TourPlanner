package TourPlannerUI.Log;

import TourPlannerUI.model.TourLog;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class LogDetailModel {
    @Getter @Setter public ObjectProperty<LocalDate> dateField = new SimpleObjectProperty<>(LocalDate.now());
    @Getter @Setter public StringProperty reportArea = new SimpleStringProperty("");
    @Getter @Setter public FloatProperty distanceField = new SimpleFloatProperty(0);
    @Getter @Setter public StringProperty startTimeField = new SimpleStringProperty("");
    @Getter @Setter public StringProperty totalTimeField = new SimpleStringProperty("");
    @Getter @Setter public StringProperty ratingField = new SimpleStringProperty("0");
    @Getter @Setter public StringProperty exhaustingField = new SimpleStringProperty("0");
    @Getter @Setter public FloatProperty averageSpeedField = new SimpleFloatProperty(0);
    @Getter @Setter public FloatProperty caloriesField = new SimpleFloatProperty(0);
    @Getter @Setter public StringProperty breaksField = new SimpleStringProperty("0");
    @Getter @Setter public StringProperty weatherField = new SimpleStringProperty("-");

    @Getter private final ObservableList<String> ratingChoices = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5");
    @Getter private final ObservableList<String> exhaustingChoices = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5");
    @Getter private final ObservableList<String> breaksChoices = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "5+");
    @Getter private final ObservableList<String> weatherChoices = FXCollections.observableArrayList("Sunny", "Cloudy", "Snowy", "Windy", "Rainy", "Foggy");

    public boolean validateData() {
        if(dateField.getValue() == null) {
            new Alert(Alert.AlertType.ERROR,"Missing Data, please enter the date").show();
            return false;
        }
        if(startTimeField.get().isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"Missing Data, please enter the start time").show();
            return false;
        }
        if(!startTimeField.get().matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")) {
            new Alert(Alert.AlertType.ERROR,"Start time incorrect. ^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$").show();
            return false;
        }
        if(distanceField.get() == 0) {
            new Alert(Alert.AlertType.ERROR,"Missing Data, please enter the distance").show();
            return false;
        }
        if(!String.valueOf(distanceField.get()).replaceAll("\\.",",").replaceAll(" ","").matches("([0-9]*[,])?[0-9]+")) {
            new Alert(Alert.AlertType.ERROR,"Distance incorrect. ([0-9]*[,])?[0-9]+").show();
            return false;
        }
        if(totalTimeField.get().isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"Missing Data, please enter the total time").show();
            return false;
        }
        if(!totalTimeField.get().matches("[0-9]+:[0-5][0-9]")) {
            new Alert(Alert.AlertType.ERROR,"Total time incorrect. [0-9]+:[0-5][0-9]").show();
            return false;
        }
        if(averageSpeedField.get() == 0) {
            new Alert(Alert.AlertType.ERROR,"Missing Data, please enter the average speed").show();
            return false;
        }
        if(!String.valueOf(averageSpeedField.get()).replaceAll("\\.",",").replaceAll(" ","").matches("([0-9]*[,])?[0-9]+")) {
            new Alert(Alert.AlertType.ERROR,"Average speed incorrect. ([0-9]*[,])?[0-9]+").show();
            return false;
        }
        if(caloriesField.get() == 0) {
            new Alert(Alert.AlertType.ERROR,"Missing Data, please enter the calories").show();
            return false;
        }
        if(!String.valueOf(caloriesField.get()).replaceAll("\\.",",").replaceAll(" ","").matches("([0-9]*[,])?[0-9]+")) {
            new Alert(Alert.AlertType.ERROR,"Calories incorrect. ([0-9]*[,])?[0-9]+").show();
            return false;
        }
        if(breaksField.getValue().equals("")) {
            new Alert(Alert.AlertType.ERROR,"Missing Data, please select the break count").show();
            return false;
        }
        if(weatherField.getValue().equals("")) {
            new Alert(Alert.AlertType.ERROR,"Missing Data, please select the weather").show();
            return false;
        }
        return true;
    }

    public void showTourLog(TourLog tourLog) {
        dateField.setValue(tourLog.getDate());
        reportArea.setValue(tourLog.getReport());
        distanceField.setValue(tourLog.getDistance());
        startTimeField.setValue(tourLog.getStartTime());
        totalTimeField.setValue(tourLog.getTotalTime());
        ratingField.setValue(String.valueOf(tourLog.getRating()));
        exhaustingField.setValue(String.valueOf(tourLog.getExhausting()));
        averageSpeedField.setValue(tourLog.getAverageSpeed());
        caloriesField.setValue(tourLog.getCalories());
        breaksField.setValue(tourLog.getBreaks());
        weatherField.setValue(tourLog.getWeather());
    }

    public void setTourLog(TourLog tourLog) {
        tourLog.setDate(dateField.get());
        tourLog.setReport(reportArea.get());
        tourLog.setDistance(distanceField.get());
        tourLog.setStartTime(startTimeField.get());
        tourLog.setTotalTime(totalTimeField.get());
        tourLog.setRating(Integer.parseInt(ratingField.get()));
        tourLog.setExhausting(Integer.parseInt(exhaustingField.get()));
        tourLog.setAverageSpeed(averageSpeedField.get());
        tourLog.setCalories(caloriesField.get());
        tourLog.setBreaks(breaksField.get());
        tourLog.setWeather(weatherField.get());
    }
}
