package TourPlannerUI;
import TourPlannerUI.businesslayer.AppManager;
import TourPlannerUI.businesslayer.AppManagerFactory;
import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class ViewModel {

    private AppManager manager;
    private Stage stage;

    /** Search **/
    @Getter private final StringProperty searchField = new SimpleStringProperty("");

    /** TourItem **/
    @Getter private final ObjectProperty<TourItem> selectedTourItem = new SimpleObjectProperty<>();
    @Getter @Setter private TourItem currentTourItem;
    @Getter private ObservableList<TourItem> tourList;
    /** TourDetails **/
    @Getter private final StringProperty tourNameField = new SimpleStringProperty("");
    @Getter private final StringProperty tourDetailsField = new SimpleStringProperty("");
    @Getter private final ObjectProperty<javafx.scene.image.Image> tourImageView = new SimpleObjectProperty<>();
    /** TourLog **/
    @Getter private final ObjectProperty<TourLog> selectedTourLog  = new SimpleObjectProperty<>();
    @Getter @Setter private TourLog currentTourLog;
    @Getter private ObservableList<TourLog> tourLogList;

    public void Init(Stage stage) throws IOException, SQLException {
        manager = AppManagerFactory.GetManager();
        this.stage = stage;
        tourList = FXCollections.observableArrayList();
        tourLogList = FXCollections.observableArrayList();
        tourList.addAll(manager.GetItems());
        tourNameField.setValue("Tour");
        tourDetailsField.setValue("select Tour");
        SetCurrentItem();
        SetCurrentLog();
    }

    /** Search **/
    public void clearSearch() throws IOException, SQLException {
        searchField.setValue("");
        refreshTourList();
    }

    /** TourItem **/
    public void addTourItem(TourItem tourItem) {
        tourList.add(tourItem);
    }
    public void importTour() throws ParseException, SQLException, IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            TourItem tourItem = manager.ImportTour(file.getPath());
            tourList.add(tourItem);
            setTourItem(tourItem);
        }
    }
    public void exportTour() throws ParseException, SQLException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Tour_" + currentTourItem.getName().replaceAll("-","_").replace(" ",""));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            manager.ExportTour(currentTourItem, file.getPath());
        }
    }
    public void duplicateTourItem() throws IOException, SQLException, ParseException {
        TourItem newTourItem = manager.CreateTourItem(currentTourItem);
        for (TourLog newTourLog : tourLogList) {
            newTourLog.setLogTourItem(newTourItem);
            manager.CreateTourLog(newTourLog);
        }
        addTourItem(newTourItem);
    }
    public void deleteTour() throws IOException, SQLException {
        manager.DeleteTourItem(currentTourItem.getId());
        refreshTourList();
    }
    public void createReport() throws ParseException, SQLException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Report_" + currentTourItem.getName().replaceAll("-","_").replace(" ",""));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            manager.CreateReportForItem(currentTourItem,file.getPath());
        }
    }
    public void createSummarizeReport() throws ParseException, SQLException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Report_Summarize_" + currentTourItem.getName().replaceAll("-","_").replaceAll(" ",""));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            manager.CreateSummarizeReportForItem(currentTourItem,file.getPath());
        }
    }

    /** TourLog **/
    public void addTourLog(TourLog tourLog) {
        tourLogList.add(tourLog);
    }
    public void deleteLog() throws IOException, SQLException, ParseException {
        manager.DeleteTourLog(currentTourLog.getId());
        refreshTourLogList();
    }
    public void duplicateLog() throws IOException, SQLException, ParseException {
        TourLog newTourLog = manager.CreateTourLog(currentTourLog);
        addTourLog(newTourLog);
        setTourLog(newTourLog);
    }

    /** Tour Events **/
    public void setTourItem(TourItem tourItem) throws IOException {
        currentTourItem = tourItem;
        tourNameField.setValue(tourItem.getName());
        tourImageView.setValue(manager.requestRouteImage(tourItem.getId()));
        currentTourLog = null;
        tourDetailsField.setValue(
                "Start: " + tourItem.getStart() + "\n"
                + "End: " + tourItem.getEnd() + "\n"
                + "Distance: " + tourItem.getDistance() + "\n"
                + "Description: " + tourItem.getDescription());
        try {
            tourLogList.clear();
            tourLogList.addAll(manager.GetLogsForItem(tourItem));
        } catch (SQLException | IOException | ParseException e) {
            e.printStackTrace();
        }
    }
    public void refreshTourList() throws IOException, SQLException {
        tourList.clear();
        tourLogList.clear();
        tourList.addAll(manager.Search(searchField.get(),false));
        tourNameField.setValue("Tour");
        tourDetailsField.setValue("select Tour");
        tourImageView.setValue(null);
    }

    /** TourLog Events **/
    public void setTourLog(TourLog tourLog) {
        currentTourLog = tourLog;
    }
    public void refreshTourLogList() throws IOException, SQLException, ParseException {
        tourLogList.clear();
        tourLogList.addAll(manager.GetLogsForItem(currentTourItem));
    }

    /** Setup **/
    private void SetCurrentItem() {
        selectedTourItem.addListener(((observableValue, oldValue, newValue) -> {
            if ((newValue != null) && (oldValue != newValue)) {
                try {
                    setTourItem(newValue);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));
    }
    private void SetCurrentLog(){
        selectedTourLog.addListener(((observableValue, oldValue, newValue) -> {
            if ((newValue != null) && (oldValue != newValue)) {
                setCurrentTourLog(newValue);
            }
        }));
    }
}
