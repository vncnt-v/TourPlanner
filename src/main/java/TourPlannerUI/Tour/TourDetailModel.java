package TourPlannerUI.Tour;

import TourPlannerUI.businesslayer.AppManagerFactory;
import TourPlannerUI.model.TourItem;
import javafx.beans.property.*;
import javafx.scene.control.Alert;
import lombok.Getter;
import lombok.Setter;

public class TourDetailModel {

    @Getter @Setter public StringProperty name = new SimpleStringProperty("");
    @Getter @Setter public StringProperty start = new SimpleStringProperty("");
    @Getter @Setter public StringProperty end = new SimpleStringProperty("");
    @Getter @Setter public StringProperty description = new SimpleStringProperty("");

    public boolean validateData() {
        if(name.get().isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"Missing Data, please enter the name").show();
            return false;
        }
        if(start.get().isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"Missing Data, please enter the start").show();
            return false;
        }
        if(end.get().isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"Missing Data, please enter the end").show();
            return false;
        }
        if(description.get().isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"Missing Data, please enter the description").show();
            return false;
        }
        if(!AppManagerFactory.GetManager().hasValidRoute(start.get(),end.get())) {
            new Alert(Alert.AlertType.ERROR,"No valid route, check start and end!").show();
            return false;
        }
        return true;
    }

    public void showTourItem(TourItem tourItem) {
        name.setValue(tourItem.getName());
        start.setValue(tourItem.getStart());
        end.setValue(tourItem.getEnd());
        description.setValue(tourItem.getDescription());
    }
    public void setTourItem(TourItem tourItem) {
        tourItem.setName(name.get());
        tourItem.setStart(start.get());
        tourItem.setEnd(end.get());
        tourItem.setDescription(description.get());
    }
}
