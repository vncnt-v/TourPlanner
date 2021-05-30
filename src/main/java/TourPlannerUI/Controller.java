package TourPlannerUI;

import TourPlannerUI.Log.LogDetailController;
import TourPlannerUI.Tour.TourDetailController;
import TourPlannerUI.businesslayer.AppManager;
import TourPlannerUI.businesslayer.AppManagerFactory;
import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.SneakyThrows;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    // create custom viewmodel
    public ViewModel viewModel = new ViewModel();

    private AppManager manager;
    public TextField searchField;

    /** Tour **/
    private ObservableList<TourItem> tourItems;
    public ListView<TourItem> listTourItems;
    public Button editItemBtn;
    public MenuItem editItemMenuBtn;
    public Button deleteItemBtn;
    public MenuItem deleteItemMenuBtn;
    public MenuItem duplicateItemMenuBtn;
    public Label tourNameLabel;
    public Label tourDescriptionLabel;
    private TourItem currentTourItem;

    /** Logs **/
    private ObservableList<TourLog> tourLogs;
    public TableView<TourLog> listTourLogs;
    public Button createLogBtn;
    public MenuItem createLogMenuBtn;
    public Button editLogBtn;
    public MenuItem editLogMenuBtn;
    public Button deleteLogBtn;
    public MenuItem deleteLogMenuBtn;
    public MenuItem duplicateLogMenuBtn;
    private TourLog currentTourLog;

    /** PDF **/
    public MenuItem createPdfMenuBtn;
    public MenuItem createSummarizePdfMenuBtn;

    /** Export Menu Btn **/
    public MenuItem exportTourBtn;

    public void newTourAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/TourDetail.fxml"));
            DialogPane tourDetailDialogPane = fxmlLoader.load();
            TourDetailController tourDetailController = fxmlLoader.getController();
            TourItem tourItem = new TourItem();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(tourDetailDialogPane);
            dialog.setTitle("Create new Tour");
            tourDetailController.setTour(tourItem,dialog);
            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if (clickedButton.get() == ButtonType.OK){
                TourItem newTourItem = manager.CreateTourItem(tourItem);
                tourItems.add(newTourItem);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void duplicateTourAction(ActionEvent actionEvent) throws SQLException {
        TourItem newTourItem = manager.CreateTourItem(currentTourItem);
        for(int i = 0; i < tourLogs.size(); i++){
            TourLog newTourLog = tourLogs.get(i);
            newTourLog.setLogTourItem(newTourItem);
            manager.CreateTourLog(newTourLog);
        }
        tourItems.add(newTourItem);
    }

    public void duplicateLogAction(ActionEvent actionEvent) throws SQLException {
        TourLog newTourLog = manager.CreateTourLog(currentTourLog);
        tourLogs.add(newTourLog);
    }

    public void editTourAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/TourDetail.fxml"));
            DialogPane tourDetailDialogPane = fxmlLoader.load();
            TourDetailController tourDetailController = fxmlLoader.getController();
            Dialog<ButtonType> dialog = new Dialog<>();

            dialog.setDialogPane(tourDetailDialogPane);
            tourDetailController.setTour(currentTourItem,dialog);
            dialog.setTitle("Create new Tour");

            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if (clickedButton.get() == ButtonType.OK){
                manager.UpdateTourItem(currentTourItem);
                tourItems.clear();
                tourLogs.clear();
                List<TourItem> items = manager.Search(searchField.textProperty().getValue(),false);
                tourItems.addAll(items);
                tourNameLabel.textProperty().setValue("Tour");
                tourDescriptionLabel.textProperty().setValue("select Tour");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteItemAction(ActionEvent actionEvent) throws SQLException {
        manager.DeleteTourItem(currentTourItem.getId());
        tourItems.clear();
        tourLogs.clear();
        List<TourItem> items = manager.Search(searchField.textProperty().getValue(),false);
        tourItems.addAll(items);
        tourNameLabel.textProperty().setValue("Tour");
        tourDescriptionLabel.textProperty().setValue("select Tour");
    }

    /** Log Methods **/
    public void createLogAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/LogDetail.fxml"));
            DialogPane logDetailDialogPane = fxmlLoader.load();
            LogDetailController logDetailController = fxmlLoader.getController();
            TourLog tourLog = new TourLog(currentTourItem);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(logDetailDialogPane);
            dialog.setTitle("Create new Log");
            logDetailController.setLog(tourLog,dialog);
            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if (clickedButton.get() == ButtonType.OK){
                TourLog newTourLogItem = manager.CreateTourLog(tourLog);
                tourLogs.add(newTourLogItem);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteLogAction(ActionEvent actionEvent) throws SQLException {
        manager.DeleteTourLog(currentTourLog.getId());
        tourLogs.clear();
        tourLogs.addAll(manager.GetLogsForItem(currentTourItem));
    }

    public void editLogAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/LogDetail.fxml"));
            DialogPane logDetailDialogPane = fxmlLoader.load();
            LogDetailController logDetailController = fxmlLoader.getController();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(logDetailDialogPane);
            dialog.setTitle("Create new Log");
            logDetailController.setLog(currentTourLog,dialog);
            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if (clickedButton.get() == ButtonType.OK){
                manager.UpdateTourLog(currentTourLog);
                tourLogs.clear();
                tourLogs.addAll(manager.GetLogsForItem(currentTourItem));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /** Import/Export Methods **/
    public void importTourAction(ActionEvent actionEvent) throws SQLException {
        manager.ImportTour(currentTourItem);
        tourLogs.clear();
        tourLogs.addAll(manager.GetLogsForItem(currentTourItem));
    }
    public void exportTourAction(ActionEvent actionEvent) throws SQLException {
        manager.ExportTour(currentTourItem);
    }

    /** Search Methods **/
    public void searchAction(ActionEvent actionEvent) throws SQLException {
        tourItems.clear();
        List<TourItem> items = manager.Search(searchField.textProperty().getValue(),false);
        tourItems.addAll(items);
    }
    public void clearAction(ActionEvent actionEvent) throws SQLException {
        tourItems.clear();
        searchField.textProperty().setValue("");
        List<TourItem> items = manager.GetItems();
        tourItems.addAll(items);
    }

    /** Create Pdf **/
    public void CreateReportForItem(ActionEvent actionEvent) throws SQLException {
        manager.CreateReportForItem(currentTourItem);
    }
    public void CreateSummarizeReportForItem(ActionEvent actionEvent) throws SQLException {
        manager.CreateSummarizeReportForItem(currentTourItem);
    }

    /** Init & Select Item **/
    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = AppManagerFactory.GetManager();
        SetupListView();
        FormatCells();
        SetCurrentItem();

        SetupTableView();
        FormatTable();
        SetCurrentLog();

        Bindings.bindBidirectional(tourNameLabel.textProperty(), viewModel.getTourNameLabel());
        Bindings.bindBidirectional(tourNameLabel.textProperty(), viewModel.getTourNameLabel());

        createLogBtn.disableProperty().bind(listTourItems.getSelectionModel().selectedItemProperty().isNull());
        editItemBtn.disableProperty().bind(listTourItems.getSelectionModel().selectedItemProperty().isNull());
        editItemMenuBtn.disableProperty().bind(listTourItems.getSelectionModel().selectedItemProperty().isNull());
        deleteItemBtn.disableProperty().bind(listTourItems.getSelectionModel().selectedItemProperty().isNull());
        deleteItemMenuBtn.disableProperty().bind(listTourItems.getSelectionModel().selectedItemProperty().isNull());
        deleteLogBtn.disableProperty().bind(listTourLogs.getSelectionModel().selectedItemProperty().isNull());
        deleteLogMenuBtn.disableProperty().bind(listTourLogs.getSelectionModel().selectedItemProperty().isNull());
        createLogMenuBtn.disableProperty().bind(listTourItems.getSelectionModel().selectedItemProperty().isNull());
        createPdfMenuBtn.disableProperty().bind(listTourItems.getSelectionModel().selectedItemProperty().isNull());
        createSummarizePdfMenuBtn.disableProperty().bind(listTourItems.getSelectionModel().selectedItemProperty().isNull());
        exportTourBtn.disableProperty().bind(listTourItems.getSelectionModel().selectedItemProperty().isNull());

        duplicateItemMenuBtn.disableProperty().bind(listTourItems.getSelectionModel().selectedItemProperty().isNull());
        duplicateLogMenuBtn.disableProperty().bind(listTourLogs.getSelectionModel().selectedItemProperty().isNull());
        editLogBtn.disableProperty().bind(listTourLogs.getSelectionModel().selectedItemProperty().isNull());
        editLogMenuBtn.disableProperty().bind(listTourLogs.getSelectionModel().selectedItemProperty().isNull());

        tourNameLabel.textProperty().setValue("Tour");
        tourDescriptionLabel.textProperty().setValue("(select Tour)");
        //manager.SetLogging();
    }

    private void SetupListView() throws SQLException {
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

    private void SetCurrentItem() {
        listTourItems.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if ((newValue != null) && (oldValue != newValue)) {
                currentTourItem = newValue;
                currentTourLog = null;
                try {
                    tourLogs.clear();
                    tourLogs.addAll(manager.GetLogsForItem(currentTourItem));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                viewModel.setTourName(currentTourItem.getName());
                tourDescriptionLabel.textProperty().setValue("Start: " + currentTourItem.getStart() + "\n"+"End: " + currentTourItem.getEnd() + "\n"+"Distance: " + currentTourItem.getDistance() + " km\nDescription: " + currentTourItem.getDescription());
            }
        }));
    }

    private void SetupTableView() throws SQLException {
        tourLogs = FXCollections.observableArrayList();
        listTourLogs.setItems(tourLogs);
    }

    private void FormatTable() {
        TableColumn tableColDateTime = new TableColumn("Date");
        TableColumn tableColStartTime = new TableColumn("Start Time");
        TableColumn tableColReport = new TableColumn("Report");
        TableColumn tableColDistance = new TableColumn("Distance");
        TableColumn tableColTotalTime = new TableColumn("Total Time");
        TableColumn tableColRating = new TableColumn("Rating");
        TableColumn tableColExhausting = new TableColumn("Exhausting");
        TableColumn tableColAverageSpeed = new TableColumn("Average Speed");
        TableColumn tableColCalories = new TableColumn("Calories");
        TableColumn tableColBreaks = new TableColumn("Stops");
        TableColumn tableColWeather = new TableColumn("Weather");

        listTourLogs.getColumns().clear();
        listTourLogs.getColumns().addAll(tableColDateTime,tableColStartTime,tableColReport,tableColDistance,tableColTotalTime,tableColRating,tableColExhausting,tableColAverageSpeed,tableColCalories,tableColBreaks,tableColWeather);

        tableColDateTime.setCellValueFactory( new PropertyValueFactory<TourLog,String>("Date"));
        tableColStartTime.setCellValueFactory( new PropertyValueFactory<TourLog,String>("StartTime"));
        tableColReport.setCellValueFactory(new PropertyValueFactory<TourLog,String>("Report"));
        tableColDistance.setCellValueFactory(new PropertyValueFactory<TourLog,String>("Distance"));
        tableColTotalTime.setCellValueFactory(new PropertyValueFactory<TourLog,String>("TotalTime"));
        tableColRating.setCellValueFactory(new PropertyValueFactory<TourLog,String>("Rating"));
        tableColExhausting.setCellValueFactory(new PropertyValueFactory<TourLog,String>("Exhausting"));
        tableColAverageSpeed.setCellValueFactory(new PropertyValueFactory<TourLog,String>("AverageSpeed"));
        tableColCalories.setCellValueFactory(new PropertyValueFactory<TourLog,String>("Calories"));
        tableColBreaks.setCellValueFactory(new PropertyValueFactory<TourLog,String>("Breaks"));
        tableColWeather.setCellValueFactory(new PropertyValueFactory<TourLog,String>("Weather"));
    }

    private void SetCurrentLog(){
        listTourLogs.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if ((newValue != null) && (oldValue != newValue)) {
                currentTourLog = newValue;
            }
        }));
    }
}