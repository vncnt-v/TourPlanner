package TourPlannerUI;
import javafx.beans.property.*;
import lombok.Getter;

import java.beans.PropertyChangeSupport;

public class ViewModel {
    @Getter
    private StringProperty startField = new SimpleStringProperty("");
    @Getter
    private StringProperty endField = new SimpleStringProperty("");
    @Getter
    private StringProperty descriptionArea = new SimpleStringProperty("");
    @Getter
    private StringProperty tourNameLabel = new SimpleStringProperty("");
    @Getter
    private StringProperty tourDescriptionLabel = new SimpleStringProperty("");

    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void setTourName(String string){
        tourNameLabel.set(string);
    }
}
