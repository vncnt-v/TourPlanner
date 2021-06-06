package TourPlannerUI.businesslayer;

import TourPlannerUI.dataaccesslayer.common.DALFactory;
import TourPlannerUI.dataaccesslayer.common.IFileAccess;
import TourPlannerUI.dataaccesslayer.dao.ITourItemDAO;
import TourPlannerUI.dataaccesslayer.dao.ITourLogDAO;
import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppManagerImpl implements AppManager {

    /** Tour **/
    @Override
    public List<TourItem> GetItems() throws SQLException, IOException {
        Logger log = LogManager.getLogger(AppManagerImpl.class);
        log.info("Get tour items");

        ITourItemDAO tourItemDAO = DALFactory.CreateTourItemDAO();
        if (tourItemDAO == null){
            log.error("Cant access TourItemDAO");
            return new ArrayList<>();
        }
        return tourItemDAO.GetItems();
    }
    @Override
    public List<TourItem> Search(String itemName, boolean caseSensitive) throws SQLException, IOException {
        Logger log = LogManager.getLogger(AppManagerImpl.class);
        log.info("Search tour items");

        List<TourItem> items = GetItems();

        if(caseSensitive){
                return items
                        .stream()
                        .filter(x -> {
                            try {
                                for (TourLog item : GetLogsForItem(x)){
                                    if (item.getReport().contains(itemName) || item.getDate().toString().contains(itemName) || item.getStartTime().contains(itemName) || String.valueOf(item.getRating()).contains(itemName) || item.getWeather().contains(itemName) || item.getBreaks().contains(itemName) || String.valueOf(item.getCalories()).contains(itemName) || String.valueOf(item.getAverageSpeed()).contains(itemName) || String.valueOf(item.getExhausting()).contains(itemName)){
                                        return true;
                                    }
                                }
                                if (x.getName().contains(itemName) || String.valueOf(x.getDistance()).contains(itemName) || x.getStart().contains(itemName) || x.getEnd().contains(itemName) || x.getDescription().contains(itemName)){
                                    return true;
                                }
                            } catch (SQLException | IOException | ParseException e) {
                                e.printStackTrace();
                            }
                            return false;
                        })
                        .collect(Collectors.toList());
        }

        return items
                .stream()
                .filter(x -> {
                    try {
                        for (TourLog item : GetLogsForItem(x)){
                            if (item.getReport().toLowerCase().contains(itemName.toLowerCase()) || item.getDate().toString().toLowerCase().contains(itemName.toLowerCase()) || item.getStartTime().toLowerCase().contains(itemName.toLowerCase()) || String.valueOf(item.getRating()).toLowerCase().contains(itemName.toLowerCase()) || item.getWeather().toLowerCase().contains(itemName.toLowerCase()) || item.getBreaks().toLowerCase().contains(itemName.toLowerCase()) || String.valueOf(item.getCalories()).toLowerCase().contains(itemName.toLowerCase()) || String.valueOf(item.getAverageSpeed()).toLowerCase().contains(itemName.toLowerCase()) || String.valueOf(item.getExhausting()).toLowerCase().contains(itemName.toLowerCase())){
                                return true;
                            }
                        }
                        if (x.getName().toLowerCase().contains(itemName.toLowerCase()) || String.valueOf(x.getDistance()).toLowerCase().contains(itemName.toLowerCase()) || x.getStart().toLowerCase().contains(itemName.toLowerCase()) || x.getEnd().toLowerCase().contains(itemName.toLowerCase()) || x.getDescription().toLowerCase().contains(itemName.toLowerCase())){
                            return true;
                        }
                    } catch (SQLException | IOException | ParseException e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }
    @Override
    public TourItem CreateTourItem(TourItem tourItem) throws SQLException, IOException {
        Logger log = LogManager.getLogger(AppManagerImpl.class);
        log.info("Create tour item");
        ITourItemDAO tourItemDAO = DALFactory.CreateTourItemDAO();
        IFileAccess fileAccess = DALFactory.GetFileAccess();
        tourItem.setDistance(requestRouteDistance(tourItem.getStart(),tourItem.getEnd()));
        if (tourItemDAO == null){
            log.error("Cant access TourItemDAO");
            return null;
        }
        TourItem result = tourItemDAO.AddNewItem(tourItem);
        fileAccess.saveImage(MapQuestManager.requestRouteImage(tourItem.getStart(),tourItem.getEnd()),result.getId());
        return result;
    }
    @Override
    public boolean UpdateTourItem(TourItem tourItem) throws SQLException, FileNotFoundException {
        Logger log = LogManager.getLogger(AppManagerImpl.class);
        log.info("Update tour item");
        ITourItemDAO tourItemDAO = DALFactory.CreateTourItemDAO();
        IFileAccess fileAccess = DALFactory.GetFileAccess();
        fileAccess.saveImage(MapQuestManager.requestRouteImage(tourItem.getStart(),tourItem.getEnd()),tourItem.getId());
        tourItem.setDistance(requestRouteDistance(tourItem.getStart(),tourItem.getEnd()));
        if (tourItemDAO == null){
            log.error("Cant access TourItemDAO");
            return false;
        }
        return tourItemDAO.UpdateItem(tourItem);
    }
    @Override
    public boolean DeleteTourItem(int id) throws SQLException, FileNotFoundException {
        Logger log = LogManager.getLogger(AppManagerImpl.class);
        log.info("Delete tour items");
        ITourItemDAO tourItemDAO = DALFactory.CreateTourItemDAO();
        IFileAccess fileAccess = DALFactory.GetFileAccess();
        fileAccess.deleteImage(id);
        if (tourItemDAO == null){
            log.error("Cant access TourItemDAO");
            return false;
        }
        return tourItemDAO.DeleteItem(id);
    }

    /** Logs **/
    @Override
    public List<TourLog> GetLogsForItem(TourItem item) throws SQLException, IOException, ParseException {
        Logger log = LogManager.getLogger(AppManagerImpl.class);
        log.info("Get logs for items");
        ITourLogDAO tourLogDAO = DALFactory.CreateTourLogDAO();
        if (tourLogDAO == null){
            log.error("Cant access TourLogDAO");
            return null;
        }
        return tourLogDAO.GetLogsForItem(item);
    }
    @Override
    public TourLog CreateTourLog(TourLog tourLog) throws SQLException, IOException, ParseException {
        Logger log = LogManager.getLogger(AppManagerImpl.class);
        log.info("Create Tour Log");
        ITourLogDAO tourLogDAO = DALFactory.CreateTourLogDAO();
        if (tourLogDAO == null){
            log.error("Cant access TourLogDAO");
            return null;
        }
        return tourLogDAO.AddNewItemLog(tourLog);
    }
    @Override
    public boolean UpdateTourLog(TourLog tourLog) throws SQLException {
        Logger log = LogManager.getLogger(AppManagerImpl.class);
        log.info("Update Tour Log");
        ITourLogDAO tourLogDAO = DALFactory.CreateTourLogDAO();
        if (tourLogDAO == null){
            log.error("Cant access TourLogDAO");
            return false;
        }
        return tourLogDAO.UpdateLog(tourLog);
    }
    @Override
    public boolean DeleteTourLog(int id) throws SQLException {
        Logger log = LogManager.getLogger(AppManagerImpl.class);
        log.info("Delete Tour Log");
        ITourLogDAO tourLogDAO = DALFactory.CreateTourLogDAO();
        if (tourLogDAO == null){
            log.error("Cant access TourLogDAO");
            return false;
        }
        return tourLogDAO.DeleteLog(id);
    }

    /** PDF **/
    @Override
    public boolean CreateReportForItem(TourItem item, String path) throws SQLException, IOException, ParseException {
        Logger log = LogManager.getLogger(AppManagerImpl.class);
        log.info("Create PDF report for item");
        ITourLogDAO tourLogDAO = DALFactory.CreateTourLogDAO();
        if (tourLogDAO == null){
            log.error("Cant access TourLogDAO");
            return false;
        }
        return PdfGenerator.GenerateReport(item,tourLogDAO.GetLogsForItem(item),path);
    }
    @Override
    public boolean CreateSummarizeReportForItem(TourItem item, String path) throws SQLException, IOException, ParseException {
        Logger log = LogManager.getLogger(AppManagerImpl.class);
        log.info("Create summarized PDF report for item");
        ITourLogDAO tourLogDAO = DALFactory.CreateTourLogDAO();
        if (tourLogDAO == null){
            log.error("Cant access TourLogDAO");
            return false;
        }
        return PdfGenerator.GenerateSummarizeReport(item,tourLogDAO.GetLogsForItem(item),path);
    }

    /** Import/Export **/
    @Override
    public TourItem ImportTour(String filePath) throws SQLException, IOException {
        Logger log = LogManager.getLogger(AppManagerImpl.class);
        log.info("Import tour");
        IFileAccess fileAccess = DALFactory.GetFileAccess();
        File file =  fileAccess.loadFile(filePath);
        if (file == null){
            log.error("Cant access File");
            return null;
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
                log.error("Cant access File: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
        return importedTour;
    }
    @Override
    public boolean ExportTour(TourItem item, String path) throws IOException, SQLException, ParseException {
        Logger log = LogManager.getLogger(AppManagerImpl.class);
        log.info("Export tour");
        IFileAccess fileAccess = DALFactory.GetFileAccess();
        return fileAccess.exportTour(item,GetLogsForItem(item),path);
    }

    /** MapQuest **/
    private float requestRouteDistance(String start, String end) {
        Logger log = LogManager.getLogger(AppManagerImpl.class);
        log.info("Request route distance");
        String jsonString = MapQuestManager.requestRoute(start,end);
        if (jsonString == null){
            log.error("Cant access JsonString");
            return 0;
        }
        JSONObject obj = new JSONObject(jsonString);
        return obj.getJSONObject("route").getFloat("distance");
    }
    @Override
    public boolean hasValidRoute(String start, String end) {
        Logger log = LogManager.getLogger(AppManagerImpl.class);
        log.info("Has valid route");
        String jsonString = MapQuestManager.requestRoute(start,end);
        if (jsonString == null){
            log.error("Cant access JsonString");
            return false;
        }
        JSONObject obj = new JSONObject(jsonString);
        return obj.getJSONObject("route").has("distance");
    }
    @Override
    public Image requestRouteImage(int id) throws FileNotFoundException {
        Logger log = LogManager.getLogger(AppManagerImpl.class);
        log.info("Request route image");
        IFileAccess fileAccess = DALFactory.GetFileAccess();
        BufferedImage img = fileAccess.loadImage(id);
        if (img != null){
            return SwingFXUtils.toFXImage(img, null);
        }
        log.error("Cant access route image");
        return null;
    }
}
