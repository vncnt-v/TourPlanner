package TourPlannerUI.businesslayer;

import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface AppManager {

    /** Tour **/
    List<TourItem> GetItems() throws SQLException, IOException;
    List<TourItem> Search(String itemName, boolean caseSensitive) throws SQLException, IOException;
    TourItem CreateTourItem(TourItem tourItem) throws SQLException, IOException;
    boolean UpdateTourItem(TourItem tourItem) throws SQLException, FileNotFoundException;
    boolean DeleteTourItem(int id) throws SQLException, FileNotFoundException;

    /** Logs **/
    boolean DeleteTourLog(int id) throws SQLException;
    TourLog CreateTourLog(TourLog tourLog) throws SQLException, IOException, ParseException;
    boolean UpdateTourLog(TourLog tourLog) throws SQLException;
    List<TourLog> GetLogsForItem(TourItem item) throws SQLException, IOException, ParseException;

    /** PDF **/
    boolean CreateReportForItem(TourItem item, String path) throws SQLException, IOException, ParseException;
    boolean CreateSummarizeReportForItem(TourItem item, String path) throws SQLException, IOException, ParseException;

    /** Import/Export **/
    TourItem ImportTour(String path) throws SQLException, IOException, ParseException;
    boolean ExportTour(TourItem item, String path) throws SQLException, IOException, ParseException;

    /** MapQuest **/
    boolean hasValidRoute(String start, String end);
    Image requestRouteImage(int id) throws FileNotFoundException;
}
