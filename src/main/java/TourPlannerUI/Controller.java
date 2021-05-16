package TourPlannerUI;

import TourPlannerUI.businesslayer.AppManager;
import TourPlannerUI.businesslayer.AppManagerFactory;
import TourPlannerUI.businesslayer.NameGenerator;
import TourPlannerUI.businesslayer.TourNameGenerator;
import TourPlannerUI.dataaccesslayer.common.DALFactory;
import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private AppManager manager;
    public TextField searchField;

    /** Tour **/
    private ObservableList<TourItem> tourItems;
    public ListView<TourItem> listTourItems;
    public Button deleteItemBtn;
    public MenuItem deleteItemMenuBtn;
    public Label tourNameLabel;
    public Label tourDescriptionLabel;
    private TourItem currentTourItem;

    /** Logs **/
    private ObservableList<TourLog> tourLogs;
    public TableView<TourLog> listTourLogs;
    public Button createLogBtn;
    public MenuItem createLogMenuBtn;
    public Button deleteLogBtn;
    public MenuItem deleteLogMenuBtn;
    private TourLog currentTourLog;

    /** PDF **/
    public MenuItem createPdfMenuBtn;
    public MenuItem createSummarizePdfMenuBtn;

    /** Export Menu Btn **/
    public MenuItem exportTourBtn;

    /** Tour Methods **/
    public void createItemAction(ActionEvent actionEvent) throws SQLException {
        TourItem tourItem = manager.CreateTourItem(TourNameGenerator.GenerateName(),NameGenerator.GenerateName(18),(new Random().nextInt(4900)/100f));
        tourItems.add(tourItem);
    }
    public void deleteItemAction(ActionEvent actionEvent) throws SQLException {
        manager.DeleteTourItem(currentTourItem.getId());
        tourItems.clear();
        tourLogs.clear();
        List<TourItem> items = manager.Search(searchField.textProperty().getValue(),false);
        tourItems.addAll(items);
        tourNameLabel.textProperty().setValue("Tour");
        tourDescriptionLabel.textProperty().setValue("(select Tour)");
    }

    /** Log Methods **/
    public void createLogAction(ActionEvent actionEvent) throws SQLException {
        TourLog tourLog = manager.CreateTourLog(
                LocalDateTime.now(),
                NameGenerator.GenerateName(15),
                (new Random().nextInt(20000)/100f),
                Duration.ZERO,
                new Random().nextInt(11),
                new Random().nextInt(11),
                new Random().nextInt(500)/10f,
                new Random().nextInt(10000)/10f,
                new Random().nextInt(10),
                "Cloudy",
                currentTourItem
        );
        tourLogs.add(tourLog);
    }
    public void deleteLogAction(ActionEvent actionEvent) throws SQLException {
        manager.DeleteTourLog(currentTourLog.getId());
        tourLogs.clear();
        tourLogs.addAll(manager.GetLogsForItem(currentTourItem));
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

        createLogBtn.disableProperty().bind(listTourItems.getSelectionModel().selectedItemProperty().isNull());
        deleteItemBtn.disableProperty().bind(listTourItems.getSelectionModel().selectedItemProperty().isNull());
        deleteItemMenuBtn.disableProperty().bind(listTourItems.getSelectionModel().selectedItemProperty().isNull());
        deleteLogBtn.disableProperty().bind(listTourLogs.getSelectionModel().selectedItemProperty().isNull());
        deleteLogMenuBtn.disableProperty().bind(listTourLogs.getSelectionModel().selectedItemProperty().isNull());
        createLogMenuBtn.disableProperty().bind(listTourItems.getSelectionModel().selectedItemProperty().isNull());
        createPdfMenuBtn.disableProperty().bind(listTourItems.getSelectionModel().selectedItemProperty().isNull());
        createSummarizePdfMenuBtn.disableProperty().bind(listTourItems.getSelectionModel().selectedItemProperty().isNull());
        exportTourBtn.disableProperty().bind(listTourItems.getSelectionModel().selectedItemProperty().isNull());

        tourNameLabel.textProperty().setValue("Tour");
        tourDescriptionLabel.textProperty().setValue("(select Tour)");

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
                tourNameLabel.textProperty().setValue(currentTourItem.getName());
                tourDescriptionLabel.textProperty().setValue("Distance: " + currentTourItem.getDistance() + " km\nDescription: " + currentTourItem.getDescription());
            }
        }));
    }

    private void SetupTableView() throws SQLException {
        tourLogs = FXCollections.observableArrayList();
        listTourLogs.setItems(tourLogs);
    }

    private void FormatTable() {
        TableColumn tableColDateTime = new TableColumn("Date Time");
        TableColumn tableColReport = new TableColumn("Report");
        TableColumn tableColDistance = new TableColumn("Distance");
        TableColumn tableColTotalTime = new TableColumn("Total Time");
        TableColumn tableColRating = new TableColumn("Rating");
        TableColumn tableColExhausting = new TableColumn("Exhausting");
        TableColumn tableColAverageSpeed = new TableColumn("Average Speed");
        TableColumn tableColCalories = new TableColumn("Calories");
        TableColumn tableColBreaks = new TableColumn("Breaks");
        TableColumn tableColWeather = new TableColumn("Weather");

        listTourLogs.getColumns().clear();
        listTourLogs.getColumns().addAll(tableColDateTime,tableColReport,tableColDistance,tableColTotalTime,tableColRating,tableColExhausting,tableColAverageSpeed,tableColCalories,tableColBreaks,tableColWeather);

        tableColDateTime.setCellValueFactory( new PropertyValueFactory<TourLog,String>("DateTime"));
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