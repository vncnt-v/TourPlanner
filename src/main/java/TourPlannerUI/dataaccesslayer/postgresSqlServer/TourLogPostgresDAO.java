package TourPlannerUI.dataaccesslayer.postgresSqlServer;

import TourPlannerUI.dataaccesslayer.common.DALFactory;
import TourPlannerUI.dataaccesslayer.common.IDatabase;
import TourPlannerUI.dataaccesslayer.dao.ITourItemDAO;
import TourPlannerUI.dataaccesslayer.dao.ITourLogDAO;
import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TourLogPostgresDAO implements ITourLogDAO {

    private final String SQL_FIND_BY_ID = "SELECT * FROM \"TourLog\" WHERE \"Id\"=CAST(? AS INTEGER);";
    private final String SQL_FIND_BY_TOUR_ITEM = "SELECT * FROM \"TourLog\" WHERE \"TourItemId\" =CAST(? AS INTEGER);";
    private final String SQL_INSERT_NEW_ITEM_LOG = "INSERT INTO \"TourLog\" (\"Date\",\"StartTime\",\"Report\",\"Distance\",\"TotalTime\",\"Rating\",\"Exhausting\",\"AverageSpeed\",\"Calories\",\"Breaks\",\"Weather\",\"TourItemId\") VALUES (?, ?, ?, CAST(? AS FLOAT), ?, CAST(? AS INTEGER), CAST(? AS INTEGER), CAST(? AS FLOAT), CAST(? AS FLOAT), ?, ?, CAST(? AS INTEGER));";
    private final String SQL_UPDATE_LOG = "UPDATE \"TourLog\" SET \"Date\" = ?,\"StartTime\" = ?,\"Report\" = ?,\"Distance\" = CAST(? AS FLOAT),\"TotalTime\" = ?,\"Rating\" = CAST(? AS INTEGER),\"Exhausting\" = CAST(? AS INTEGER),\"AverageSpeed\" = CAST(? AS FLOAT),\"Calories\"= CAST(? AS FLOAT),\"Breaks\" = ?,\"Weather\" = ? WHERE \"Id\" = CAST(? AS INTEGER);";
    private final String SQL_DELETE_LOG = "DELETE FROM \"TourLog\" WHERE \"Id\"=CAST(? AS INTEGER);";

    private IDatabase database;
    private ITourItemDAO tourLogDAO;

    public TourLogPostgresDAO() throws FileNotFoundException {
        this.database = DALFactory.GetDatabase();
        this.tourLogDAO = DALFactory.CreateTourItemDAO();
    }

    @Override
    public TourLog FindById(Integer logId) throws SQLException {
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(logId);
        List<TourLog> tourItems = database.TourReader(SQL_FIND_BY_ID, parameters, TourLog.class);

        return tourItems.stream().findFirst().get();
    }

    @Override
    public TourLog AddNewItemLog(LocalDate date, String report, float distance, String startTime, String totalTime, int rating, int exhausting, float averageSpeed, float calories, String breaks, String weather, TourItem logItem) throws SQLException {
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(date.toString());
        parameters.add(startTime);
        parameters.add(report);
        parameters.add(distance);
        parameters.add(totalTime);
        parameters.add(rating);
        parameters.add(exhausting);
        parameters.add(averageSpeed);
        parameters.add(calories);
        parameters.add(breaks);
        parameters.add(weather);
        parameters.add(logItem.getId());

        int resultID = database.InsertNew(SQL_INSERT_NEW_ITEM_LOG, parameters);
        return FindById(resultID);
    }

    @Override
    public boolean UpdateLog(TourLog tourlog) throws SQLException {
        ArrayList<Object> parameters = new ArrayList<>();

        parameters.add(tourlog.getDate());
        parameters.add(tourlog.getStartTime());
        parameters.add(tourlog.getReport());
        parameters.add(tourlog.getDistance());
        parameters.add(tourlog.getTotalTime());
        parameters.add(tourlog.getRating());
        parameters.add(tourlog.getExhausting());
        parameters.add(tourlog.getAverageSpeed());
        parameters.add(tourlog.getCalories());
        parameters.add(tourlog.getBreaks());
        parameters.add(tourlog.getWeather());

        parameters.add(tourlog.getId());

        int check = database.UpdateEntry(SQL_UPDATE_LOG, parameters);
        if(check > 0){
            return true;
        }
        return false;
    }

    @Override
    public List<TourLog> GetLogsForItem(TourItem item) throws SQLException {
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(item.getId());

        return database.TourReader(SQL_FIND_BY_TOUR_ITEM, parameters, TourLog.class);
    }

    @Override
    public boolean DeleteLog(Integer logId) throws SQLException {
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(logId);
        database.DeleteEntry(SQL_DELETE_LOG, parameters);
        return true;
    }
}
