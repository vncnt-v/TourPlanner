package TourPlannerUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TourPlannerModel {
    // http://openbook.rheinwerk-verlag.de/javainsel/12_004.html
    private final StringProperty headline = new SimpleStringProperty("Bike");


    public StringProperty getHeadline(){
        return headline;
    }
}
