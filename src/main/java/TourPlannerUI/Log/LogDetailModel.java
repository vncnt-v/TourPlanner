package TourPlannerUI.Log;

import TourPlannerUI.businesslayer.AppManager;
import TourPlannerUI.businesslayer.AppManagerFactory;
import TourPlannerUI.model.ErrorMessage;
import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;

public class LogDetailModel {

    private AppManager manager;
    private TourLog tourLog;
    private TourItem tourItem;

    @Getter @Setter public ObjectProperty<LocalDate> dateField = new SimpleObjectProperty<>(LocalDate.now());
    @Getter @Setter public StringProperty reportArea = new SimpleStringProperty("");
    @Getter @Setter public StringProperty distanceField = new SimpleStringProperty("0");
    @Getter @Setter public StringProperty startTimeField = new SimpleStringProperty("00:00");
    @Getter @Setter public StringProperty totalTimeField = new SimpleStringProperty("00:00");
    @Getter @Setter public StringProperty ratingField = new SimpleStringProperty("-");
    @Getter @Setter public StringProperty exhaustingField = new SimpleStringProperty("-");
    @Getter @Setter public StringProperty averageSpeedField = new SimpleStringProperty("0");
    @Getter @Setter public StringProperty caloriesField = new SimpleStringProperty("0");
    @Getter @Setter public StringProperty breaksField = new SimpleStringProperty("-");
    @Getter @Setter public StringProperty weatherField = new SimpleStringProperty("-");

    @Getter private final ObservableList<String> ratingChoices = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5");
    @Getter private final ObservableList<String> exhaustingChoices = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5");
    @Getter private final ObservableList<String> breaksChoices = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "5+");
    @Getter private final ObservableList<String> weatherChoices = FXCollections.observableArrayList("Sunny", "Cloudy", "Snowy", "Windy", "Rainy", "Foggy");

    public ErrorMessage validateData() {
        if(dateField.getValue() == null) {
            return new ErrorMessage("Missing Data, please enter the date");
        }
        if(startTimeField.get().isEmpty()) {
            return new ErrorMessage("Missing Data, please enter the start time");
        }
        if(!startTimeField.get().matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$")) {
            return new ErrorMessage("Start time incorrect. ^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$");
        }
        if(distanceField.get().isEmpty() || distanceField.get().equals("0")) {
            return new ErrorMessage("Missing Data, please enter the distance");
        }
        if(!distanceField.get().replaceAll("\\.",",").replaceAll(" ","").matches("([0-9]*[,])?[0-9]+")) {
            return new ErrorMessage("Distance incorrect. ([0-9]*[,])?[0-9]+");
        }
        if(totalTimeField.get().isEmpty() || totalTimeField.get().equals("00:00")) {
            return new ErrorMessage("Missing Data, please enter the total time");
        }
        if(!totalTimeField.get().matches("[0-9]+:[0-5][0-9]")) {
            return new ErrorMessage("Total time incorrect. [0-9]+:[0-5][0-9]");
        }
        if(averageSpeedField.get().isEmpty() || averageSpeedField.get().equals("0")) {
            return new ErrorMessage("Missing Data, please enter the average speed");
        }
        if(!averageSpeedField.get().replaceAll("\\.",",").replaceAll(" ","").matches("([0-9]*[,])?[0-9]+")) {
            return new ErrorMessage("Average speed incorrect. ([0-9]*[,])?[0-9]+");
        }
        if(caloriesField.get().isEmpty() || caloriesField.get().equals("0")) {
            return new ErrorMessage("Missing Data, please enter the calories");
        }
        if(!caloriesField.get().replaceAll("\\.",",").replaceAll(" ","").matches("([0-9]*[,])?[0-9]+")) {
            return new ErrorMessage("Calories incorrect. ([0-9]*[,])?[0-9]+");
        }
        if(!String.valueOf(caloriesField.get()).replaceAll("\\.",",").replaceAll(" ","").matches("([0-9]*[,])?[0-9]+")) {
            return new ErrorMessage("Calories incorrect. ([0-9]*[,])?[0-9]+");
        }
        if(!ratingField.get().matches("[0-5]") || ratingField.get().isEmpty() || Integer.parseInt(ratingField.get()) < 0 || 5 < Integer.parseInt(ratingField.get())) {
            return new ErrorMessage("Missing Data, please enter the rating");
        }
        if(!exhaustingField.get().matches("[0-5]") || exhaustingField.get().isEmpty() || Integer.parseInt(exhaustingField.get()) < 0 || 5 < Integer.parseInt(exhaustingField.get())) {
            return new ErrorMessage("Missing Data, please enter the exhausting");
        }
        if(Integer.parseInt(exhaustingField.get()) < 0 || 5 < Integer.parseInt(exhaustingField.get())) {
            return new ErrorMessage("Missing Data, please enter the exhausting");
        }
        return null;
    }

    public void Init(TourItem tourItem, TourLog tourLog) {
        manager = AppManagerFactory.GetManager();
        this.tourItem = tourItem;
        if (tourLog != null) {
            this.tourLog = tourLog;
            dateField.setValue(tourLog.getDate());
            reportArea.setValue(tourLog.getReport());
            distanceField.setValue(Float.toString(tourLog.getDistance()));
            startTimeField.setValue(tourLog.getStartTime());
            totalTimeField.setValue(tourLog.getTotalTime());
            ratingField.setValue(String.valueOf(tourLog.getRating()));
            exhaustingField.setValue(String.valueOf(tourLog.getExhausting()));
            averageSpeedField.setValue(Float.toString(tourLog.getAverageSpeed()));
            caloriesField.setValue(Float.toString(tourLog.getCalories()));
            breaksField.setValue(tourLog.getBreaks());
            weatherField.setValue(tourLog.getWeather());
        }
    }

    public TourLog createTourLog() throws IOException, SQLException, ParseException {
        return manager.CreateTourLog(new TourLog(dateField.get(), reportArea.get(), Float.parseFloat(distanceField.get()), startTimeField.get(), totalTimeField.get(), Integer.parseInt(ratingField.get()), Integer.parseInt(exhaustingField.get()), Float.parseFloat(averageSpeedField.get()), Float.parseFloat(caloriesField.get()), breaksField.get(), weatherField.get(), tourItem));
    }

    public boolean updateTourLog() throws SQLException {
        tourLog.setDate(dateField.get());
        tourLog.setReport(reportArea.get());
        tourLog.setDistance(Float.parseFloat(distanceField.get()));
        tourLog.setStartTime(startTimeField.get());
        tourLog.setTotalTime(totalTimeField.get());
        tourLog.setRating(Integer.parseInt(ratingField.get()));
        tourLog.setExhausting(Integer.parseInt(exhaustingField.get()));
        tourLog.setAverageSpeed(Float.parseFloat(averageSpeedField.get()));
        tourLog.setCalories(Float.parseFloat(caloriesField.get()));
        tourLog.setBreaks(breaksField.get());
        tourLog.setWeather(weatherField.get());

        return manager.UpdateTourLog(tourLog);
    }
}
