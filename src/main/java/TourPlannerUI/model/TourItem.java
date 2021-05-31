package TourPlannerUI.model;

import javafx.beans.property.*;
import lombok.Getter;
import lombok.Setter;

public class TourItem {

    @Getter @Setter public int id;
    @Getter public StringProperty nameProperty;
    @Getter public StringProperty startProperty;
    @Getter public StringProperty endProperty;
    @Getter public StringProperty descriptionProperty;
    @Getter public FloatProperty distanceProperty;

    public TourItem() {
        this.nameProperty = new SimpleStringProperty("");
        this.startProperty = new SimpleStringProperty("");
        this.endProperty = new SimpleStringProperty("");
        this.descriptionProperty = new SimpleStringProperty("");
        this.distanceProperty = new SimpleFloatProperty(0);
    }

    public TourItem(Integer id, String name,String start,String end, String description, float distance) {
        this.id = id;
        this.nameProperty = new SimpleStringProperty(name);
        this.startProperty = new SimpleStringProperty(start);
        this.endProperty = new SimpleStringProperty(end);
        this.descriptionProperty = new SimpleStringProperty(description);
        this.distanceProperty = new SimpleFloatProperty(distance);
    }

    public String getName(){
        return nameProperty.get();
    }
    public String getStart(){
        return startProperty.get();
    }
    public String getEnd(){
        return endProperty.get();
    }
    public String getDescription(){
        return descriptionProperty.get();
    }
    public float getDistance(){
        return distanceProperty.get();
    }
    public void setDistance(float distance){
         distanceProperty = new SimpleFloatProperty(distance);
    }
}