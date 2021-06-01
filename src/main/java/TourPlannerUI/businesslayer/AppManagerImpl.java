package TourPlannerUI.businesslayer;

import TourPlannerUI.businesslayer.logging.ILogging;
import TourPlannerUI.businesslayer.logging.LoggingImpl;
import TourPlannerUI.dataaccesslayer.common.DALFactory;
import TourPlannerUI.dataaccesslayer.dao.ITourItemDAO;
import TourPlannerUI.dataaccesslayer.dao.ITourLogDAO;
import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;
import javafx.scene.image.Image;
import org.json.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Float.parseFloat;

public class AppManagerImpl implements AppManager {

    /** Tour **/
    @Override
    public List<TourItem> GetItems() throws SQLException, IOException {
        ITourItemDAO tourItemDAO = DALFactory.CreateTourItemDAO();
        return tourItemDAO.GetItems();
    }
    @Override
    public List<TourItem> Search(String itemName, boolean caseSensitive) throws SQLException, IOException {
        List<TourItem> items = GetItems();

        if(caseSensitive){
            return items
                    .stream()
                    .filter(x -> x.getName().contains(itemName))
                    .collect(Collectors.toList());
        }

        return items
                .stream()
                .filter(x -> x.getName().toLowerCase().contains(itemName.toLowerCase()))
                .collect(Collectors.toList());
    }
    @Override
    public TourItem CreateTourItem(TourItem tourItem) throws SQLException, IOException {
        ITourItemDAO tourItemDAO = DALFactory.CreateTourItemDAO();
        return tourItemDAO.AddNewItem(tourItem.getName(),tourItem.getStart(),tourItem.getEnd(),tourItem.getDescription(),tourItem.getDistance());
    }
    @Override
    public boolean UpdateTourItem(TourItem tourItem) throws SQLException {
        ITourItemDAO tourItemDAO = DALFactory.CreateTourItemDAO();
        return tourItemDAO.UpdateItem(tourItem);
    }
    @Override
    public boolean DeleteTourItem(int id) throws SQLException {
        ITourItemDAO tourItemDAO = DALFactory.CreateTourItemDAO();
        return tourItemDAO.DeleteItem(id);
    }

    /** Logs **/
    @Override
    public List<TourLog> GetLogsForItem(TourItem item) throws SQLException, IOException, ParseException {
        ITourLogDAO tourLogDAO = DALFactory.CreateTourLogDAO();
        return tourLogDAO.GetLogsForItem(item);
    }
    @Override
    public TourLog CreateTourLog(TourLog tourLog) throws SQLException, IOException, ParseException {
        ITourLogDAO tourLogDAO = DALFactory.CreateTourLogDAO();
        return tourLogDAO.AddNewItemLog(tourLog, tourLog.getLogTourItem());
    }
    @Override
    public boolean UpdateTourLog(TourLog tourLog) throws SQLException {
        ITourLogDAO tourLogDAO = DALFactory.CreateTourLogDAO();
        return tourLogDAO.UpdateLog(tourLog);
    }
    @Override
    public boolean DeleteTourLog(int id) throws SQLException {
        ITourLogDAO tourLogDAO = DALFactory.CreateTourLogDAO();
        return tourLogDAO.DeleteLog(id);
    }

    /** PDF **/
    @Override
    public boolean CreateReportForItem(TourItem item, String path) throws SQLException, IOException, ParseException {
        ITourLogDAO tourLogDAO = DALFactory.CreateTourLogDAO();
        return PdfGenerator.GenerateReport(item,tourLogDAO.GetLogsForItem(item),path);
    }
    @Override
    public boolean CreateSummarizeReportForItem(TourItem item, String path) throws SQLException, IOException, ParseException {
        ITourLogDAO tourLogDAO = DALFactory.CreateTourLogDAO();
        return PdfGenerator.GenerateSummarizeReport(item,tourLogDAO.GetLogsForItem(item),path);
    }

    /** Import/Export **/
    @Override
    public void ImportTour(String filePath) throws SQLException, IOException, ParseException {
        File file = ImportExportManager.ImportTour(filePath);
        List<String> fileLines = Files.readAllLines(Path.of(file.getAbsolutePath()));
        TourItem newTour = new TourItem(
                -1,
            fileLines.get(0) + " (Imported)",
            fileLines.get(1),
            fileLines.get(2),
            fileLines.get(3),
            Float.parseFloat(fileLines.get(4))
        );
        TourItem importedTour = CreateTourItem(newTour);
        for (int i = 5; i+10 < fileLines.size(); i+=11){
            TourLog newLog = new TourLog(
                -1,
                new SimpleDateFormat("yyyy-MM-dd").parse(fileLines.get(i)).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                fileLines.get(i+1),
                Float.parseFloat(fileLines.get(i+2)),
                fileLines.get(i+4),
                fileLines.get(i+3),
                Integer.parseInt(fileLines.get(i+5)),
                Integer.parseInt(fileLines.get(i+6)),
                Float.parseFloat(fileLines.get(i+7)),
                Float.parseFloat(fileLines.get(i+8)),
                fileLines.get(i+9),
                fileLines.get(i+10),
                importedTour
            );
            CreateTourLog(newLog);
        }
    }
    @Override
    public void ExportTour(TourItem item, List<TourLog> tourLogs, String path) throws SQLException, IOException {
        ImportExportManager.ExportTour(item,tourLogs,path);
    }

    /** MapQuest **/
    @Override
    public String requestRoute(String start, String end) {
        return MapQuestManager.requestRoute(start,end);
    }
    @Override
    public float requestRouteDistance(String start, String end) {
        String jsonString = MapQuestManager.requestRoute(start,end);
        JSONObject obj = new JSONObject(jsonString);
        return obj.getJSONObject("route").getFloat("distance");
    }
    @Override
    public boolean hasValidRoute(String start, String end) {
        String jsonString = MapQuestManager.requestRoute(start,end);
        JSONObject obj = new JSONObject(jsonString);
        return obj.getJSONObject("route").has("distance");
    }
    @Override
    public Image requestRouteImage(String start, String end) {
        String jsonString = MapQuestManager.requestRoute(start,end);
        JSONObject obj = new JSONObject(jsonString);
        if(obj.getJSONObject("route").has("sessionId") && obj.getJSONObject("route").has("boundingBox")) {
            return MapQuestManager.requestRouteImage(obj.getJSONObject("route").getString("sessionId"),obj.getJSONObject("route").getJSONObject("boundingBox"));
        }
        return null;
    }

    /** Logging **/
    @Override
    public void SetLogging() {
        ILogging log = new LoggingImpl();
        log.execute();
    }
}
