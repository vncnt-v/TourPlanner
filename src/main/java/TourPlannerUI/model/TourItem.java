package TourPlannerUI.model;

import lombok.Getter;
import lombok.Setter;

public class TourItem {

    @Getter @Setter public int id;
    @Getter @Setter public String name;
    @Getter @Setter public String start;
    @Getter @Setter public String end;
    @Getter @Setter public String description;
    @Getter @Setter public float distance;

    public TourItem() {

    }

    public TourItem(Integer id, String name,String start,String end, String description, float distance) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
        this.description = description;
        this.distance = distance;
    }
}