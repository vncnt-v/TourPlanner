package TourPlannerUI.Tour;

import TourPlannerUI.businesslayer.AppManager;
import TourPlannerUI.businesslayer.AppManagerFactory;
import TourPlannerUI.model.TourItem;
import javafx.beans.property.*;
import javafx.scene.control.Alert;
import lombok.Getter;

import java.io.IOException;
import java.sql.SQLException;

public class TourDetailModel {

    private AppManager manager;
    private TourItem tourItem;

    @Getter public StringProperty name = new SimpleStringProperty("");
    @Getter public StringProperty start = new SimpleStringProperty("");
    @Getter public StringProperty end = new SimpleStringProperty("");
    @Getter public StringProperty description = new SimpleStringProperty("");

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
        if(!AppManagerFactory.GetManager().hasValidRoute(start.get(),end.get())) {
            new Alert(Alert.AlertType.ERROR,"No valid route, check start and end!").show();
            return false;
        }
        return true;
    }

    public void Init(TourItem tourItem) {
        manager = AppManagerFactory.GetManager();
        if (tourItem != null) {
            this.tourItem = tourItem;
            name.setValue(tourItem.getName());
            start.setValue(tourItem.getStart());
            end.setValue(tourItem.getEnd());
            description.setValue(tourItem.getDescription());
        }
    }
    public TourItem createTourItem() throws IOException, SQLException {
        return manager.CreateTourItem(new TourItem(name.get(), start.get(), end.get(), description.get()));
    }
    public boolean updateTourItem() throws IOException, SQLException {
        tourItem.setName(name.get());
        tourItem.setStart(start.get());
        tourItem.setEnd(end.get());
        tourItem.setDescription(description.get());
        return manager.UpdateTourItem(tourItem);
    }
}
