package TourPlannerUI;

import TourPlannerUI.businesslayer.AppManager;
import TourPlannerUI.businesslayer.AppManagerFactory;
import TourPlannerUI.model.TourItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private AppManager manager;
    public TextField searchField;
    public ListView<TourItem> listTourItems;
    private ObservableList<TourItem> tourItems;

    private TourItem currentTourItem;

    /**     View Model   **/
    //public TourPlannerModel viewModel = new TourPlannerModel();

    /**       Tours      **/
    //public TextField tours_search_bar;
    //public VBox tours_container;

    /**   Tour Details   **/
    //public Label tour_detail_name;
    //public Label tour_detail_description;
    //public Image tour_detail_image;

    /**     Tour Logs    **/
    //public TableView tour_log_table;
    //public TableColumn nameColumn;
    //public TableColumn dateColumn;
    //public TableColumn timeColumn;
    //public TableColumn distanceColumn;
    //public TableColumn ratingColumn;

    /**    Controller
    //public Controller()
    {
        System.out.println("Controller created");
    } **/

    /**     Tour Btn     **/
    public void create_Tour(ActionEvent actionEvent){
        System.out.println("Controller - Create Tour");
        //viewModel .createTour();
    }
    public void delete_Tour(ActionEvent actionEvent){
        System.out.println("Controller - Delete Tour");
        //viewModel .deleteTour();
    }

    public void searchAction(ActionEvent actionEvent){
        tourItems.clear();
        List<TourItem> items = manager.Search(searchField.textProperty().getValue(),false);
        tourItems.addAll(items);
    }
    public void clearAction(ActionEvent actionEvent){
        tourItems.clear();
        searchField.textProperty().setValue("");
        List<TourItem> items = manager.GetItems();
        tourItems.addAll(items);
    }
    public void selectTour(){
        System.out.println("Controller - Select Tour");
        //viewModel .selectTour();
    }

    /**     Log Btn     **/
    public void create_Log(ActionEvent actionEvent){
        System.out.println("Controller - Create Log");
        //viewModel .createLog();
    }
    public void delete_Log(ActionEvent actionEvent){
        System.out.println("Controller - Delete Log");
        //viewModel .deleteLog();
    }

    /**    Bindings     **/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = AppManagerFactory.GetManager();
        SetupListView();
        FormatCells();
        SetCurrentItem();
        // Tours
        //tours_search_bar.textProperty().bindBidirectional(viewModel.tours_search_bar_Property());
        //tours_container.visibleProperty().bindBidirectional(viewModel.tour_container_Property());
        // Tour
        //tour_detail_name.textProperty().bindBidirectional(viewModel.tour_detail_name_Property());
        //tour_detail_description.textProperty().bindBidirectional(viewModel.tour_detail_description_Property());
        //tour_detail_image.imageProperty().bindBidirectional(viewModel.tour_detail_image_Property());
        // Log
        //nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        //dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
        //timeColumn.setCellValueFactory(new PropertyValueFactory<>("Time"));
        //distanceColumn.setCellValueFactory(new PropertyValueFactory<>("Distance"));
        //ratingColumn.setCellValueFactory(new PropertyValueFactory<>("Rating"));
        //tour_log_table.setItems(viewModel.tour_log_table_Property());
    }

    private void SetupListView(){
        tourItems = FXCollections.observableArrayList();
        tourItems.addAll(manager.GetItems());
        listTourItems.setItems(tourItems);
    }

    private void FormatCells() {
        listTourItems.setCellFactory((param -> new ListCell<TourItem>() {
            @Override
            protected void updateItem(TourItem item, boolean empty){
                super.updateItem(item, empty);

                if(empty || (item == null) || (item.getName() == null)){
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        }));
    }

    private void SetCurrentItem(){
        listTourItems.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if ((newValue != null) && (oldValue != newValue)) {
                currentTourItem = newValue;
            }
        }));
    }
}