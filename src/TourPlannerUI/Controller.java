package TourPlannerUI;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    //////////////////////
    //     View Model   //
    //////////////////////
    public TourPlannerModel viewModel = new TourPlannerModel();

    //////////////////////
    //       Views      //
    //////////////////////
    public AnchorPane tour_new_view;
    public AnchorPane tour_detail_view;
    public AnchorPane route_new_view;
    public AnchorPane route_detail_view;

    //////////////////////
    //    New Tour      //
    //////////////////////
    public TextField tour_new_name;
    public TextField tour_new_start;
    public TextField tour_new_end;
    public TextArea tour_new_description;

    /////////////////////////
    //    Detail Tour      //
    /////////////////////////
    public Label tour_detail_name;
    //public ImageView tour_detail_image;
    public Label tour_detail_description;
    public TableView tour_detail_table_view;


    ///////////////////////
    //     Controller    //
    ///////////////////////
    public Controller()
    {
        System.out.println("Controller created");
    }

    //////////////////////
    //   View Tour New  //
    //////////////////////
    public void open_tour_new(ActionEvent actionEvent){
        viewModel.setTourNewVisible(true);
    }
    public void cancel_tour_new(ActionEvent actionEvent){
        viewModel.setTourNewVisible(false);
    }
    public void save_tour_new(ActionEvent actionEvent){
        viewModel.setTourNewVisible(false);
    }

    //////////////////////
    // View Tour Detail //
    //////////////////////
    public void open_tour_detail(){
        viewModel.setTourDetailVisible(true);
    }
    public void close_tour_detail(ActionEvent actionEvent){
        viewModel.setTourDetailVisible(false);
    }
    public void delete_tour_detail(ActionEvent actionEvent){
        viewModel.setTourDetailVisible(false);
    }

    //////////////////////
    //  View Route New  //
    //////////////////////
    public void open_route_new(ActionEvent actionEvent){
        viewModel.setRouteNewVisible(true);
    }
    public void cancel_route_new(ActionEvent actionEvent){
        viewModel.setRouteNewVisible(false);
    }
    public void save_route_new(ActionEvent actionEvent){
        viewModel.setRouteNewVisible(false);
    }

    ///////////////////////
    // View Route Detail //
    ///////////////////////
    public void open_route_detail(ActionEvent actionEvent){
        viewModel.setRouteDetailVisible(true);
    }
    public void close_route_detail(ActionEvent actionEvent){
        viewModel.setRouteDetailVisible(false);
    }
    public void save_route_detail(ActionEvent actionEvent){
        viewModel.setRouteDetailVisible(false);
    }
    public void delete_route_detail(ActionEvent actionEvent){
        viewModel.setRouteDetailVisible(false);
    }


    ///////////////////////
    //     Bindings      //
    ///////////////////////
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Views
        tour_new_view.visibleProperty().bindBidirectional(viewModel.tour_new_ViewProperty());
        tour_detail_view.visibleProperty().bindBidirectional(viewModel.tour_detail_ViewProperty());
        route_new_view.visibleProperty().bindBidirectional(viewModel.route_new_ViewProperty());
        route_detail_view.visibleProperty().bindBidirectional(viewModel.route_detail_ViewProperty());
        // New Tour
        tour_new_name.textProperty().bindBidirectional(viewModel.tour_new_name_Property());
        tour_new_start.textProperty().bindBidirectional(viewModel.tour_new_start_Property());
        tour_new_end.textProperty().bindBidirectional(viewModel.tour_new_end_Property());
        tour_new_description.textProperty().bindBidirectional(viewModel.tour_new_description_Property());
        // Detail Tour
        tour_detail_name.textProperty().bindBidirectional(viewModel.tour_detail_name_Property());
        //tour_detail_image.textProperty().bindBidirectional(viewModel.tour_detail_ViewProperty());
        tour_detail_description.textProperty().bindBidirectional(viewModel.tour_detail_description_Property());
        tour_detail_table_view.setItems(viewModel.tour_detail_table_view_Property());
        //tour_detail_table_view.textProperty().bindBidirectional(viewModel.route_detail_ViewProperty());
    }
}