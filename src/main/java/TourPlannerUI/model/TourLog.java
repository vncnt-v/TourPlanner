package TourPlannerUI.model;

import javafx.beans.property.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class TourLog {

    @Getter @Setter public int id;
    @Getter public ObjectProperty<LocalDate> dateProperty;
    @Getter public StringProperty reportProperty;
    @Getter public FloatProperty distanceProperty;
    @Getter public StringProperty startTimeProperty;
    @Getter public StringProperty totalTimeProperty;
    @Getter public IntegerProperty ratingProperty;
    @Getter public IntegerProperty exhaustingProperty;
    @Getter public FloatProperty averageSpeedProperty;
    @Getter public FloatProperty caloriesProperty;
    @Getter public StringProperty breaksProperty;
    @Getter public StringProperty weatherProperty;
    @Getter @Setter public TourItem LogTourItem;

    public TourLog(TourItem tourItem) {
        this.dateProperty = new SimpleObjectProperty<>(LocalDate.now());
        this.reportProperty = new SimpleStringProperty("");
        this.distanceProperty = new SimpleFloatProperty(0);
        this.startTimeProperty = new SimpleStringProperty("");
        this.totalTimeProperty = new SimpleStringProperty("");
        this.ratingProperty = new SimpleIntegerProperty(0);
        this.exhaustingProperty = new SimpleIntegerProperty(0);
        this.averageSpeedProperty = new SimpleFloatProperty(0);
        this.caloriesProperty = new SimpleFloatProperty(0);
        this.breaksProperty = new SimpleStringProperty("0");
        this.weatherProperty = new SimpleStringProperty("-");
        this.LogTourItem = tourItem;
    }

    public TourLog(Integer id, LocalDate date, String report, float distance, String startTime, String totalTime, int rating, int exhausting, float averageSpeed, float calories, String breaks, String weather, TourItem tourItem){
        this.id = id;
        this.dateProperty = new SimpleObjectProperty<>(date);
        this.reportProperty = new SimpleStringProperty(report);
        this.distanceProperty = new SimpleFloatProperty(distance);
        this.totalTimeProperty = new SimpleStringProperty(totalTime);
        this.startTimeProperty = new SimpleStringProperty(startTime);
        this.ratingProperty = new SimpleIntegerProperty(rating);
        this.exhaustingProperty = new SimpleIntegerProperty(exhausting);
        this.averageSpeedProperty = new SimpleFloatProperty(averageSpeed);
        this.caloriesProperty = new SimpleFloatProperty(calories);
        this.breaksProperty = new SimpleStringProperty(breaks);
        this.weatherProperty = new SimpleStringProperty(weather);
        this.LogTourItem = tourItem;
    }

    public LocalDate getDate() {
        return dateProperty.get();
    }
    public String getReport() {
        return reportProperty.get();
    }
    public float getDistance() {
        return distanceProperty.get();
    }
    public String getStartTime() {
        return startTimeProperty.get();
    }
    public String getTotalTime() {
        return totalTimeProperty.get();
    }
    public int getRating() {
        return ratingProperty.get();
    }
    public int getExhausting() {
        return exhaustingProperty.get();
    }
    public float getAverageSpeed() {
        return averageSpeedProperty.get();
    }
    public float getCalories() {
        return caloriesProperty.get();
    }
    public String getBreaks() {
        return breaksProperty.get();
    }
    public String getWeather() {
        return weatherProperty.get();
    }
}
