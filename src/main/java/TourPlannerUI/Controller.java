package TourPlannerUI;

import TourPlannerUI.Log.LogDetailController;
import TourPlannerUI.Tour.TourDetailController;
import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @Setter public Stage stage;
    public ViewModel viewModel = new ViewModel();

    /** Search **/
    @FXML private TextField searchField;

    /** TourItem **/
    @FXML public Button tourDeleteBtn;
    @FXML public Button tourEditBtn;
    @FXML public ListView<TourItem> tourListView;
    /** TourDetails **/
    @FXML public Label tourNameField;
    @FXML public Label tourDetailsField;
    @FXML public ImageView tourImageField;
    /** Menu **/
    @FXML public MenuItem tourMenuDeleteBtn;
    @FXML public MenuItem tourMenuEditBtn;
    @FXML public MenuItem tourMenuDuplicateBtn;
    @FXML public MenuItem tourMenuExportBtn;
    @FXML public MenuItem tourMenuReportBtn;
    @FXML public MenuItem tourMenuReportSumBtn;

    /** TourLog **/
    @FXML private Button tourLogAddBtn;
    @FXML private Button tourLogDeleteBtn;
    @FXML private Button tourLogEditBtn;
    @FXML private TableView<TourLog> tourLogListView;
    /** Menu **/
    @FXML private MenuItem tourLogMenuAddBtn;
    @FXML private MenuItem tourLogMenuEditBtn;
    @FXML private MenuItem tourLogMenuDuplicateBtn;
    @FXML private MenuItem tourLogMenuDeleteBtn;


    /** Tour **/
    public void newTourAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/TourDetail.fxml"));
            DialogPane tourDetailDialogPane = fxmlLoader.load();
            TourDetailController tourDetailController = fxmlLoader.getController();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(tourDetailDialogPane);
            tourDetailController.Init(null,dialog);
            dialog.setTitle("Create new Tour");
            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if (clickedButton.isPresent() && clickedButton.get() == ButtonType.OK){
                viewModel.addTourItem(tourDetailController.createTourItem());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void duplicateTourAction() throws SQLException, IOException, ParseException {
        viewModel.duplicateTourItem();
    }
    public void editTourAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/TourDetail.fxml"));
            DialogPane tourDetailDialogPane = fxmlLoader.load();
            TourDetailController tourDetailController = fxmlLoader.getController();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(tourDetailDialogPane);
            tourDetailController.Init(viewModel.getCurrentTourItem(),dialog);
            dialog.setTitle("Edit Tour");
            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if (clickedButton.isPresent() && clickedButton.get() == ButtonType.OK){
                tourDetailController.updateTourItem();
                viewModel.refreshTourList();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteItemAction() throws SQLException, IOException {
        viewModel.deleteTour();
    }


    /** Log **/
    public void createLogAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/LogDetail.fxml"));
            DialogPane logDetailDialogPane = fxmlLoader.load();
            LogDetailController logDetailController = fxmlLoader.getController();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(logDetailDialogPane);
            logDetailController.Init(viewModel.getCurrentTourItem(),null,dialog);
            dialog.setTitle("Create new Log");
            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if (clickedButton.isPresent() && clickedButton.get() == ButtonType.OK){
                viewModel.addTourLog(logDetailController.createTourLog());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void duplicateLogAction() throws SQLException, IOException, ParseException {
        viewModel.duplicateLog();
    }
    public void deleteLogAction() throws SQLException, IOException, ParseException {
        viewModel.deleteLog();
    }
    public void editLogAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/LogDetail.fxml"));
            DialogPane logDetailDialogPane = fxmlLoader.load();
            LogDetailController logDetailController = fxmlLoader.getController();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(logDetailDialogPane);
            logDetailController.Init(viewModel.getCurrentTourItem(),viewModel.getCurrentTourLog(),dialog);
            dialog.setTitle("Edit Log");
            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if (clickedButton.isPresent() && clickedButton.get() == ButtonType.OK){
                logDetailController.updateTourLog();
                viewModel.refreshTourLogList();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /** Import/Export Methods **/
    public void importTourAction() throws SQLException, IOException, ParseException {
        viewModel.importTour();
    }
    public void exportTourAction() throws SQLException, IOException, ParseException {
        viewModel.exportTour();
    }

    /** Search Methods **/
    public void searchAction() throws SQLException, IOException {
        viewModel.refreshTourList();
    }
    public void clearAction() throws SQLException, IOException {
        viewModel.clearSearch();
    }

    /** Create Pdf **/
    public void CreateReportForItem() throws SQLException, IOException, ParseException {
        viewModel.createReport();
    }
    public void CreateSummarizeReportForItem() throws SQLException, IOException, ParseException {
        viewModel.createSummarizeReport();
    }

    /** Init **/
    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FormatCells();
        FormatTable();
        // TourItem
        searchField.textProperty().bindBidirectional(viewModel.getSearchField());
        tourDeleteBtn.disableProperty().bind(viewModel.getSelectedTourItem().isNull());
        tourEditBtn.disableProperty().bind(viewModel.getSelectedTourItem().isNull());
        viewModel.getSelectedTourItem().bind(
                tourListView.getSelectionModel().selectedItemProperty());
        tourNameField.textProperty().bind(viewModel.getTourNameField());
        tourDetailsField.textProperty().bind(viewModel.getTourDetailsField());
        tourImageField.imageProperty().bindBidirectional(viewModel.getTourImageView());
        // Menu
        tourMenuDeleteBtn.disableProperty().bind(viewModel.getSelectedTourItem().isNull());
        tourMenuEditBtn.disableProperty().bind(viewModel.getSelectedTourItem().isNull());
        tourMenuDuplicateBtn.disableProperty().bind(viewModel.getSelectedTourItem().isNull());
        tourMenuExportBtn.disableProperty().bind(viewModel.getSelectedTourItem().isNull());
        tourMenuReportBtn.disableProperty().bind(viewModel.getSelectedTourItem().isNull());
        tourMenuReportSumBtn.disableProperty().bind(viewModel.getSelectedTourItem().isNull());
        // TourLog
        tourLogAddBtn.disableProperty().bind(viewModel.getSelectedTourItem().isNull());
        tourLogDeleteBtn.disableProperty().bind(viewModel.getSelectedTourLog().isNull());
        tourLogEditBtn.disableProperty().bind(viewModel.getSelectedTourLog().isNull());
        viewModel.getSelectedTourLog().bind(
                tourLogListView.getSelectionModel().selectedItemProperty());
        // Menu
        tourLogMenuAddBtn.disableProperty().bind(viewModel.getSelectedTourItem().isNull());
        tourLogMenuEditBtn.disableProperty().bind(viewModel.getSelectedTourLog().isNull());
        tourLogMenuDuplicateBtn.disableProperty().bind(viewModel.getSelectedTourLog().isNull());
        tourLogMenuDeleteBtn.disableProperty().bind(viewModel.getSelectedTourLog().isNull());

        viewModel.Init(stage);
        tourListView.setItems(viewModel.getTourList());
        tourLogListView.setItems(viewModel.getTourLogList());
    }

    private void FormatCells() {
        tourListView.setCellFactory((param -> new ListCell<>() {
            @Override
            protected void updateItem(TourItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || (item == null) || (item.getName() == null)) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        }));
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
        tourLogListView.getColumns().clear();
        tourLogListView.getColumns().addAll(tableColDateTime,tableColStartTime,tableColReport,tableColDistance,tableColTotalTime,tableColRating,tableColExhausting,tableColAverageSpeed,tableColCalories,tableColBreaks,tableColWeather);
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
}