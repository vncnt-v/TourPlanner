package TourPlannerUI.Tour;

import TourPlannerUI.businesslayer.ErrorMessage;
import TourPlannerUI.model.TourItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;

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
        viewModel.Init(tourItem);
        final Button btOk = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        btOk.addEventFilter(
            ActionEvent.ACTION,
            event -> {
                ErrorMessage error = viewModel.validateData();
                if (error != null){
                    new Alert(Alert.AlertType.ERROR,error.getMsg()).show();
                    event.consume();
                }
            }
        );
    }
    public boolean updateTourItem () throws IOException, SQLException {
        return viewModel.updateTourItem();
    }
    public TourItem createTourItem () throws IOException, SQLException {
        return viewModel.createTourItem();
    }
}

