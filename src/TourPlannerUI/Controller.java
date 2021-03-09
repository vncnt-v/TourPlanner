package TourPlannerUI;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    // create custom viewmodel
    public TourPlannerModel viewModel = new TourPlannerModel();

    // add fx:id and use intelliJ to create field in controller
    public AnchorPane landing_view;
    public AnchorPane addTour_view;
    public AnchorPane detailTour_view;


    public Controller()
    {
        System.out.println("Controller created");
    }

    @FXML
    public void addTour(ActionEvent actionEvent) {

    }

    public void openDetailTourView(){
        detailTour_view.setVisible(true);
        BoxBlur boxblur = new BoxBlur();
        boxblur.setWidth(10.0f);
        boxblur.setHeight(10.0f);
        boxblur.setIterations(3);
        landing_view.setEffect(boxblur);
    }

    public void closeDetailTourView(){
        detailTour_view.setVisible(false);
        landing_view.setEffect(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Controller init");

        // OutputLabel.textProperty().bindBidirectional(viewModel.outputProperty());
        //Bindings.bindBidirectional(landing_view.visibleProperty(), viewModel.getHeadline());
        //Bindings.bindBidirectional(landing_view.effectProperty(), viewModel.getHeadline());
        //Bindings.bindBidirectional(addTour_view.visibleProperty(), viewModel.getHeadline());
        //Bindings.bindBidirectional(detailTour_view.visibleProperty(), viewModel.getHeadline());
    }
}