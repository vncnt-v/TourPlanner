package TourPlannerUI.Tour;

import TourPlannerUI.businesslayer.AppManagerFactory;
import TourPlannerUI.model.TourItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TourDetailController {

    @FXML
    public TextField nameField;
    @FXML
    public TextField startField;
    @FXML
    public TextField endField;
    @FXML
    public TextArea descriptionArea;

    public void setTour(TourItem tourItem, Dialog dialog){
        nameField.textProperty().bindBidirectional(tourItem.getNameProperty());
        startField.textProperty().bindBidirectional(tourItem.getStartProperty());
        endField.textProperty().bindBidirectional(tourItem.getEndProperty());
        descriptionArea.textProperty().bindBidirectional(tourItem.getDescriptionProperty());
        Button okButton = (Button)dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.addEventFilter(
            ActionEvent.ACTION, event -> {
                if (!validateFormData()){
                    event.consume();
                }
        });
    }

    public boolean validateFormData() {
        if(nameField.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Missing Data, please enter the name").show();
            nameField.requestFocus();
            return false;
        }
        if(startField.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Missing Data, please enter the start").show();
            startField.requestFocus();
            return false;
        }
        if(endField.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Missing Data, please enter the end").show();
            endField.requestFocus();
            return false;
        }
        if(descriptionArea.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Missing Data, please enter the description").show();
            descriptionArea.requestFocus();
            return false;
        }
        if(!AppManagerFactory.GetManager().hasValidRoute(startField.getText(),endField.getText())){
            new Alert(Alert.AlertType.ERROR,"No valid route, check start and end!").show();
            startField.requestFocus();
            return false;
        }
        return true;
    }
}

