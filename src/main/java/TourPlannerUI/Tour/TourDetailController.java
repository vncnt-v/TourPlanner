package TourPlannerUI.Tour;

import TourPlannerUI.model.TourItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TourDetailController {

    @FXML
    public TextField nameField;
    @FXML
    public TextField startField;
    @FXML
    public TextField endField;
    @FXML
    public TextArea descriptionArea;

    public void setTour(TourItem tourItem){
        nameField.textProperty().bindBidirectional(tourItem.getNameProperty());
        startField.textProperty().bindBidirectional(tourItem.getStartProperty());
        endField.textProperty().bindBidirectional(tourItem.getEndProperty());
        descriptionArea.textProperty().bindBidirectional(tourItem.getDescriptionProperty());
/*
        Button okButton = (Button)dialogPane.lookuButton(ButtonType.OK)
            okButton.addEventFilter(
                    ActionEvent.ACTION, event -> {
                        if (!validateFormData()){

                        }
        });*/
    }
}

