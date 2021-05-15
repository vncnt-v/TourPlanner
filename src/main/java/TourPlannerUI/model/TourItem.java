package TourPlannerUI.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;

public class TourItem {

    @Getter @Setter public Integer Id;
    @Getter @Setter public String Name;
    @Getter @Setter public String Description;

    public TourItem(Integer id, String name, String description) {
        Id = id;
        Name = name;
        Description = description;
    }
}