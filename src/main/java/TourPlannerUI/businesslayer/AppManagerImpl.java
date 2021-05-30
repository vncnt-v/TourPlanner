package TourPlannerUI.businesslayer;

import TourPlannerUI.businesslayer.logging.ILogging;
import TourPlannerUI.businesslayer.logging.LoggingImpl;
import TourPlannerUI.dataaccesslayer.common.DALFactory;
import TourPlannerUI.dataaccesslayer.dao.ITourItemDAO;
import TourPlannerUI.dataaccesslayer.dao.ITourLogDAO;
import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class AppManagerImpl implements AppManager {

    /** Tour **/
    @Override
    public List<TourItem> GetItems() throws SQLException {
        ITourItemDAO tourItemDAO = DALFactory.CreateTourItemDAO();
        return tourItemDAO.GetItems();
    }
    @Override
    public List<TourItem> Search(String itemName, boolean caseSensitive) throws SQLException {
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
    public TourItem CreateTourItem(TourItem tourItem) throws SQLException {
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
    public List<TourLog> GetLogsForItem(TourItem item) throws SQLException {
        ITourLogDAO tourLogDAO = DALFactory.CreateTourLogDAO();
        return tourLogDAO.GetLogsForItem(item);
    }
    @Override
    public TourLog CreateTourLog(TourLog tourLog) throws SQLException {
        ITourLogDAO tourLogDAO = DALFactory.CreateTourLogDAO();
        return tourLogDAO.AddNewItemLog(tourLog.getDate(),tourLog.getReport(),tourLog.getDistance(),tourLog.getStartTime(),tourLog.getTotalTime(),tourLog.getRating(),tourLog.getExhausting(),tourLog.getAverageSpeed(),tourLog.getCalories(),tourLog.getBreaks(),tourLog.getWeather(),tourLog.getLogTourItem());
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
    public boolean CreateReportForItem(TourItem item) throws SQLException {
        ITourLogDAO tourLogDAO = DALFactory.CreateTourLogDAO();
        return PdfGenerator.GenerateReport(item,tourLogDAO.GetLogsForItem(item));
    }
    @Override
    public boolean CreateSummarizeReportForItem(TourItem item) throws SQLException {
        ITourLogDAO tourLogDAO = DALFactory.CreateTourLogDAO();
        return PdfGenerator.GenerateSummarizeReport(item,tourLogDAO.GetLogsForItem(item));
    }

    /** Import/Export **/
    @Override
    public boolean ImportTour(TourItem item) throws SQLException {
        // ToDo
        return true;
    }
    @Override
    public boolean ExportTour(TourItem item) throws SQLException {
        // ToDo
        return true;
    }

    /** Logging **/
    @Override
    public void SetLogging() {
        ILogging log = new LoggingImpl();
        log.execute();
    }
}
