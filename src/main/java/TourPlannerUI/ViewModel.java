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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
            if (tourItem == null) {
                Logger log = LogManager.getLogger(ViewModel.class);
                log.error("Cant Import Tour. Path: " + file.getPath());
            } else {
                tourList.add(tourItem);
                setTourItem(tourItem);
            }
        }
    }
    public void exportTour() throws ParseException, SQLException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Tour_" + currentTourItem.getName().replaceAll("-","_").replace(" ",""));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            if (!manager.ExportTour(currentTourItem, file.getPath())) {
                Logger log = LogManager.getLogger(ViewModel.class);
                log.error("Export Tour failed. ID: " + currentTourLog.getId());
            }
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
        if (manager.DeleteTourItem(currentTourItem.getId())){
            refreshTourList();
        } else {
            Logger log = LogManager.getLogger(ViewModel.class);
            log.error("Update Tour failed. ID: " + currentTourLog.getId());
        }
    }
    public void createReport() throws ParseException, SQLException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Report_" + currentTourItem.getName().replaceAll("-","_").replace(" ",""));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            if (!manager.CreateReportForItem(currentTourItem,file.getPath())) {
                Logger log = LogManager.getLogger(ViewModel.class);
                log.error("Create report failed. Item ID: " + currentTourItem.getId());
            }
        }
    }
    public void createSummarizeReport() throws ParseException, SQLException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Report_Summarize_" + currentTourItem.getName().replaceAll("-","_").replaceAll(" ",""));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            if (!manager.CreateSummarizeReportForItem(currentTourItem,file.getPath())) {
                Logger log = LogManager.getLogger(ViewModel.class);
                log.error("Create summarized report failed. Item ID: " + currentTourItem.getId());
            }
        }
    }

    /** TourLog **/
    public void addTourLog(TourLog tourLog) {
        tourLogList.add(tourLog);
    }
    public void deleteLog() throws IOException, SQLException, ParseException {
        if (!manager.DeleteTourLog(currentTourLog.getId())) {
            Logger log = LogManager.getLogger(ViewModel.class);
            log.error("Delete Tour Log failed. ID: " + currentTourLog.getId());
        } else {
            refreshTourLogList();
        }
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
            Logger log = LogManager.getLogger(ViewModel.class);
            log.error("Get Logs for Item failed: " + e.getMessage());
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
                    Logger log = LogManager.getLogger(ViewModel.class);
                    log.error("Set new Current Item failed: " + e.getMessage());
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
