package TourPlannerUI.businesslayer;

import TourPlannerUI.businesslayer.logging.ILogging;
import TourPlannerUI.businesslayer.logging.LoggingImpl;
import TourPlannerUI.dataaccesslayer.common.DALFactory;
import TourPlannerUI.dataaccesslayer.common.IFileAccess;
import TourPlannerUI.dataaccesslayer.dao.ITourItemDAO;
import TourPlannerUI.dataaccesslayer.dao.ITourLogDAO;
import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.json.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

public class AppManagerImpl implements AppManager {

    /** Tour **/
    @Override
    public List<TourItem> GetItems() throws SQLException, IOException {
        ITourItemDAO tourItemDAO = DALFactory.CreateTourItemDAO();
        assert tourItemDAO != null;
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
        IFileAccess fileAccess = DALFactory.GetFileAccess();
        tourItem.setDistance(requestRouteDistance(tourItem.getStart(),tourItem.getEnd()));
        assert tourItemDAO != null;
        TourItem result = tourItemDAO.AddNewItem(tourItem);
        fileAccess.saveImage(MapQuestManager.requestRouteImage(tourItem.getStart(),tourItem.getEnd()),result.getId());
        return result;
    }
    @Override
    public boolean UpdateTourItem(TourItem tourItem) throws SQLException, FileNotFoundException {
        ITourItemDAO tourItemDAO = DALFactory.CreateTourItemDAO();
        IFileAccess fileAccess = DALFactory.GetFileAccess();
        fileAccess.saveImage(MapQuestManager.requestRouteImage(tourItem.getStart(),tourItem.getEnd()),tourItem.getId());
        tourItem.setDistance(requestRouteDistance(tourItem.getStart(),tourItem.getEnd()));
        assert tourItemDAO != null;
        return tourItemDAO.UpdateItem(tourItem);
    }
    @Override
    public boolean DeleteTourItem(int id) throws SQLException, FileNotFoundException {
        ITourItemDAO tourItemDAO = DALFactory.CreateTourItemDAO();
        IFileAccess fileAccess = DALFactory.GetFileAccess();
        fileAccess.deleteImage(id);
        assert tourItemDAO != null;
        return tourItemDAO.DeleteItem(id);
    }

    /** Logs **/
    @Override
    public List<TourLog> GetLogsForItem(TourItem item) throws SQLException, IOException, ParseException {
        ITourLogDAO tourLogDAO = DALFactory.CreateTourLogDAO();
        assert tourLogDAO != null;
        return tourLogDAO.GetLogsForItem(item);
    }
    @Override
    public TourLog CreateTourLog(TourLog tourLog) throws SQLException, IOException, ParseException {
        ITourLogDAO tourLogDAO = DALFactory.CreateTourLogDAO();
        assert tourLogDAO != null;
        return tourLogDAO.AddNewItemLog(tourLog);
    }
    @Override
    public boolean UpdateTourLog(TourLog tourLog) throws SQLException {
        ITourLogDAO tourLogDAO = DALFactory.CreateTourLogDAO();
        assert tourLogDAO != null;
        return tourLogDAO.UpdateLog(tourLog);
    }
    @Override
    public boolean DeleteTourLog(int id) throws SQLException {
        ITourLogDAO tourLogDAO = DALFactory.CreateTourLogDAO();
        assert tourLogDAO != null;
        return tourLogDAO.DeleteLog(id);
    }

    /** PDF **/
    @Override
    public boolean CreateReportForItem(TourItem item, String path) throws SQLException, IOException, ParseException {
        ITourLogDAO tourLogDAO = DALFactory.CreateTourLogDAO();
        assert tourLogDAO != null;
        return PdfGenerator.GenerateReport(item,tourLogDAO.GetLogsForItem(item),path);
    }
    @Override
    public boolean CreateSummarizeReportForItem(TourItem item, String path) throws SQLException, IOException, ParseException {
        ITourLogDAO tourLogDAO = DALFactory.CreateTourLogDAO();
        assert tourLogDAO != null;
        return PdfGenerator.GenerateSummarizeReport(item,tourLogDAO.GetLogsForItem(item),path);
    }

    /** Import/Export **/
    @Override
    public boolean ImportTour(String filePath) throws SQLException, IOException {
        IFileAccess fileAccess = DALFactory.GetFileAccess();
        File file =  fileAccess.loadFile(filePath);
        if (!file.exists()){
            return false;
        }
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
            TourLog newLog;
            try {
                newLog = new TourLog(
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
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean ExportTour(TourItem item, List<TourLog> tourLogs, String path) throws IOException {
        IFileAccess fileAccess = DALFactory.GetFileAccess();
        return fileAccess.exportTour(item,tourLogs,path);
    }

    /** MapQuest **/
    private float requestRouteDistance(String start, String end) {
        String jsonString = MapQuestManager.requestRoute(start,end);
        assert jsonString != null;
        JSONObject obj = new JSONObject(jsonString);
        return obj.getJSONObject("route").getFloat("distance");
    }
    @Override
    public boolean hasValidRoute(String start, String end) {
        String jsonString = MapQuestManager.requestRoute(start,end);
        assert jsonString != null;
        JSONObject obj = new JSONObject(jsonString);
        return obj.getJSONObject("route").has("distance");
    }
    @Override
    public Image requestRouteImage(int id) throws FileNotFoundException {
        IFileAccess fileAccess = DALFactory.GetFileAccess();
        BufferedImage img = fileAccess.loadImage(id);
        if (img != null){
            return SwingFXUtils.toFXImage(img, null);
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
