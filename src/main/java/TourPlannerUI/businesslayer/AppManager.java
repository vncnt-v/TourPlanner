package TourPlannerUI.businesslayer;

import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;

import java.sql.SQLException;
import java.util.List;

public interface AppManager {

    /** Tour **/
    List<TourItem> GetItems() throws SQLException;
    List<TourItem> Search(String itemName, boolean caseSensitive) throws SQLException;
    TourItem CreateTourItem(TourItem tourItem) throws SQLException;
    boolean UpdateTourItem(TourItem tourItem) throws SQLException;
    boolean DeleteTourItem(int id) throws SQLException;

    /** Logs **/
    boolean DeleteTourLog(int id) throws SQLException;
    TourLog CreateTourLog(TourLog tourLog) throws SQLException;
    boolean UpdateTourLog(TourLog tourLog) throws SQLException;
    List<TourLog> GetLogsForItem(TourItem item) throws SQLException;

    /** PDF **/
    boolean CreateReportForItem(TourItem item) throws SQLException;
    boolean CreateSummarizeReportForItem(TourItem item) throws SQLException;

    /** Import/Export **/
    boolean ImportTour(TourItem item) throws SQLException;
    boolean ExportTour(TourItem item) throws SQLException;

    /** Logging **/
    void SetLogging();
}
