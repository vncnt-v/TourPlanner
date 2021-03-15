package TourPlannerUI.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TourDetailEntry {

    private final StringProperty name;
    private final StringProperty time;
    private final StringProperty distance;
    private final StringProperty date;

    public TourDetailEntry(String name, String time, String distance, String date){
        this.name = new SimpleStringProperty(name);
        this.time = new SimpleStringProperty(time);
        this.distance = new SimpleStringProperty(distance);
        this.date = new SimpleStringProperty(date);
    }

    public TourDetailEntry(StringProperty name, StringProperty time, StringProperty distance, StringProperty date) {
        this.name = name;
        this.time = time;
        this.distance = distance;
        this.date = date;
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

    // Name
    public String getDate() {
        return date.get();
    }

    public void setDate(String date){
        this.date.set(date);
    }

    public StringProperty dateProperty() {
        return date;
    }

}
