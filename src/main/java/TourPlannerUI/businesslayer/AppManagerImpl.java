package TourPlannerUI.businesslayer;

import TourPlannerUI.dataaccesslayer.common.DALFactory;
import TourPlannerUI.dataaccesslayer.dao.ITourItemDAO;
import TourPlannerUI.dataaccesslayer.dao.ITourLogDAO;
import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
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
    public TourItem CreateTourItem(String name, String description, float distance) throws SQLException {
        ITourItemDAO tourItemDAO = DALFactory.CreateTourItemDAO();
        return tourItemDAO.AddNewItem(name,description,distance);
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
    public TourLog CreateTourLog(LocalDateTime dateTime, String report, float distance, Duration totalTime, int rating, int exhausting, float averageSpeed, float calories, int breaks, String weather, TourItem item) throws SQLException {
        ITourLogDAO tourLogDAO = DALFactory.CreateTourLogDAO();
        return tourLogDAO.AddNewItemLog(dateTime,report,distance,totalTime,rating,exhausting,averageSpeed,calories,breaks,weather,item);
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
        return ReportGenerator.GenerateReport(item,tourLogDAO.GetLogsForItem(item));
    }
    @Override
    public boolean CreateSummarizeReportForItem(TourItem item) throws SQLException {
        ITourLogDAO tourLogDAO = DALFactory.CreateTourLogDAO();
        return ReportGenerator.GenerateSummarizeReport(item,tourLogDAO.GetLogsForItem(item));
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
}
