package TourPlannerUI.businesslayer;

import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;
import javafx.scene.control.TableColumn;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public interface AppManager {

    /** Tour **/
    List<TourItem> GetItems() throws SQLException;
    List<TourItem> Search(String itemName, boolean caseSensitive) throws SQLException;
    TourItem CreateTourItem(String name, String description, float distance) throws SQLException;
    boolean DeleteTourItem(int id) throws SQLException;

    /** Logs **/
    boolean DeleteTourLog(int id) throws SQLException;
    TourLog CreateTourLog(LocalDateTime dateTime, String report, float distance, Duration totalTime, int rating, int exhausting, float averageSpeed, float calories, int breaks, String weather, TourItem item) throws SQLException;
    List<TourLog> GetLogsForItem(TourItem item) throws SQLException;

    /** PDF **/
    boolean CreateReportForItem(TourItem item) throws SQLException;
    boolean CreateSummarizeReportForItem(TourItem item) throws SQLException;

    /** Import/Export **/
    boolean ImportTour(TourItem item) throws SQLException;
    boolean ExportTour(TourItem item) throws SQLException;
}
