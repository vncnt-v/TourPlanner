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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TourLogPostgresDAO implements ITourLogDAO {

    private final String SQL_FIND_BY_ID = "SELECT * FROM \"TourLog\" WHERE \"Id\"=CAST(? AS INTEGER);";
    private final String SQL_FIND_BY_TOUR_ITEM = "SELECT * FROM \"TourLog\" WHERE \"TourItemId\" =CAST(? AS INTEGER);";
    private final String SQL_INSERT_NEW_ITEM_LOG = "INSERT INTO \"TourLog\" (\"DateTime\",\"Report\",\"Distance\",\"TotalTime\",\"Rating\",\"Exhausting\",\"AverageSpeed\",\"Calories\",\"Breaks\",\"Weather\",\"TourItemId\") VALUES (?, ?, CAST(? AS FLOAT), ?, CAST(? AS INTEGER), CAST(? AS INTEGER), CAST(? AS FLOAT), CAST(? AS FLOAT), CAST(? AS INTEGER), ?, CAST(? AS INTEGER));";
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
    public TourLog AddNewItemLog(LocalDateTime dateTime, String report, float distance, Duration totalTime, int rating, int exhausting, float averageSpeed, float calories, int breaks, String weather, TourItem logItem) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(dateTime.format(formatter));
        parameters.add(report);
        parameters.add(distance);
        parameters.add(totalTime.toString());
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
