package TourPlannerUI.Tour;

import TourPlannerUI.model.TourItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TourDetailController {

    public TourDetailModel viewModel = new TourDetailModel();

    @FXML
    public TextField nameField;
    @FXML
    public TextField startField;
    @FXML
    public TextField endField;
    @FXML
    public TextArea descriptionArea;

    public void Init(TourItem tourItem, Dialog<ButtonType> dialog){
        nameField.textProperty().bindBidirectional(viewModel.getName());
        startField.textProperty().bindBidirectional(viewModel.getStart());
        endField.textProperty().bindBidirectional(viewModel.getEnd());
        descriptionArea.textProperty().bindBidirectional(viewModel.getDescription());

        Button okButton = (Button)dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.addEventFilter(
            ActionEvent.ACTION, event -> {
                if (viewModel.validateData()){
                    viewModel.setTourItem(tourItem);
                } else {
                    event.consume();
                }
        });
    }
}

