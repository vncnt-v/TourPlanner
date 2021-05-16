package TourPlannerUI.model;

import lombok.Getter;
import lombok.Setter;

public class TourItem {

    @Getter @Setter public Integer Id;
    @Getter @Setter public String Name;
    @Getter @Setter public String Description;
    @Getter @Setter public float Distance;

    public TourItem(Integer id, String name, String description, float distance) {
        Id = id;
        Name = name;
        Description = description;
        Distance = distance;
    }
}