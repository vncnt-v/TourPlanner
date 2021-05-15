package TourPlannerUI.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TourLogEntry {

    private final StringProperty name;
    private final StringProperty date;
    private final StringProperty time;
    private final StringProperty distance;
    private final StringProperty rating;


    public TourLogEntry(String name, String date, String time, String distance, String rating){
        this.name = new SimpleStringProperty(name);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.distance = new SimpleStringProperty(distance);
        this.rating = new SimpleStringProperty(rating);
    }

    public TourLogEntry(StringProperty name, StringProperty date, StringProperty time, StringProperty distance, StringProperty rating) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.distance = distance;
        this.rating = rating;
    }

    // Name
    public String getName() {
        return name.get();
    }

    public void setName(String name){
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    // Date
    public String getDate() {
        return date.get();
    }

    public void setDate(String date){
        this.date.set(date);
    }

    public StringProperty dateProperty() {
        return date;
    }

    // Time
    public String getTime() {
        return time.get();
    }

    public void setTime(String time){
        this.time.set(time);
    }

    public StringProperty timeProperty() {
        return time;
    }

    // Distance
    public String getDistance() {
        return distance.get();
    }

    public void setDistance(String distance){
        this.distance.set(distance);
    }

    public StringProperty distanceProperty() {
        return distance;
    }

    // Rating
    public String getRating() {
        return rating.get();
    }

    public void setRating(String rating){
        this.rating.set(rating);
    }

    public StringProperty ratingProperty() {
        return rating;
    }

}
