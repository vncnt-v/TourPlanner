package TourPlannerUI.businesslayer;

import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;
import javafx.scene.image.Image;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface AppManager {

    /** Tour **/
    List<TourItem> GetItems() throws SQLException, IOException;
    List<TourItem> Search(String itemName, boolean caseSensitive) throws SQLException, IOException;
    TourItem CreateTourItem(TourItem tourItem) throws SQLException, IOException;
    boolean UpdateTourItem(TourItem tourItem) throws SQLException;
    boolean DeleteTourItem(int id) throws SQLException;

    /** Logs **/
    boolean DeleteTourLog(int id) throws SQLException;
    TourLog CreateTourLog(TourLog tourLog) throws SQLException, IOException, ParseException;
    boolean UpdateTourLog(TourLog tourLog) throws SQLException;
    List<TourLog> GetLogsForItem(TourItem item) throws SQLException, IOException, ParseException;

    /** PDF **/
    boolean CreateReportForItem(TourItem item, String path) throws SQLException, IOException, ParseException;
    boolean CreateSummarizeReportForItem(TourItem item, String path) throws SQLException, IOException, ParseException;

    /** Import/Export **/
    void ImportTour(String path) throws SQLException, IOException, ParseException;
    void ExportTour(TourItem item, List<TourLog> tourLogs, String path) throws SQLException, IOException;

    /** MapQuest **/
    String requestRoute(String start, String end);
    float requestRouteDistance(String start, String end);
    boolean hasValidRoute(String start, String end);
    Image requestRouteImage(String start, String end);

    /** Logging **/
    void SetLogging();
}
