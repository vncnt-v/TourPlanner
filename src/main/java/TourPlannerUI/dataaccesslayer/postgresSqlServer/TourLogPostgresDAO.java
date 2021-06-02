package TourPlannerUI.dataaccesslayer.postgresSqlServer;

import TourPlannerUI.dataaccesslayer.common.DALFactory;
import TourPlannerUI.dataaccesslayer.common.IDatabase;
import TourPlannerUI.dataaccesslayer.dao.ITourLogDAO;
import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TourLogPostgresDAO implements ITourLogDAO {

    private final String SQL_FIND_BY_ID = "SELECT * FROM \"TourLog\" WHERE \"Id\"=CAST(? AS INTEGER);";
    private final String SQL_FIND_BY_TOUR_ITEM = "SELECT * FROM \"TourLog\" WHERE \"TourItemId\" =CAST(? AS INTEGER);";
    private final String SQL_INSERT_NEW_ITEM_LOG = "INSERT INTO \"TourLog\" (\"Date\",\"StartTime\",\"Report\",\"Distance\",\"TotalTime\",\"Rating\",\"Exhausting\",\"AverageSpeed\",\"Calories\",\"Breaks\",\"Weather\",\"TourItemId\") VALUES (?, ?, ?, CAST(? AS FLOAT), ?, CAST(? AS INTEGER), CAST(? AS INTEGER), CAST(? AS FLOAT), CAST(? AS FLOAT), ?, ?, CAST(? AS INTEGER));";
    private final String SQL_UPDATE_LOG = "UPDATE \"TourLog\" SET \"Date\" = ?,\"StartTime\" = ?,\"Report\" = ?,\"Distance\" = CAST(? AS FLOAT),\"TotalTime\" = ?,\"Rating\" = CAST(? AS INTEGER),\"Exhausting\" = CAST(? AS INTEGER),\"AverageSpeed\" = CAST(? AS FLOAT),\"Calories\"= CAST(? AS FLOAT),\"Breaks\" = ?,\"Weather\" = ? WHERE \"Id\" = CAST(? AS INTEGER);";
    private final String SQL_DELETE_LOG = "DELETE FROM \"TourLog\" WHERE \"Id\"=CAST(? AS INTEGER);";

    private final IDatabase database;

    public TourLogPostgresDAO() throws FileNotFoundException {
        this.database = DALFactory.GetDatabase();
    }

    @Override
    public TourLog FindById(Integer logId) throws SQLException, IOException {
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(logId);
        List<TourLog> tourItems = database.TourReader(SQL_FIND_BY_ID, parameters, TourLog.class);

        if (tourItems.stream().findFirst().isPresent()){
            return tourItems.stream().findFirst().get();
        } else {
            return null;
        }
    }

    @Override
    public TourLog AddNewItemLog(TourLog tourLog, TourItem logItem) throws SQLException, IOException {
        ArrayList<Object> parameters = createTourLogParam(tourLog);

        int resultID = database.InsertNew(SQL_INSERT_NEW_ITEM_LOG, parameters);
        return FindById(resultID);
    }

    @Override
    public boolean UpdateLog(TourLog tourLog) throws SQLException {
        ArrayList<Object> parameters = createTourLogParam(tourLog);

        int check = database.UpdateEntry(SQL_UPDATE_LOG, parameters);
        return check > 0;
    }

    @Override
    public List<TourLog> GetLogsForItem(TourItem item) throws SQLException, IOException {
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

    private ArrayList<Object> createTourLogParam(TourLog tourLog) {
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(tourLog.getDate().toString());
        parameters.add(tourLog.getStartTime());
        parameters.add(tourLog.getReport());
        parameters.add(tourLog.getDistance());
        parameters.add(tourLog.getTotalTime());
        parameters.add(tourLog.getRating());
        parameters.add(tourLog.getExhausting());
        parameters.add(tourLog.getAverageSpeed());
        parameters.add(tourLog.getCalories());
        parameters.add(tourLog.getBreaks());
        parameters.add(tourLog.getWeather());
        parameters.add(tourLog.getId());

        return parameters;
    }
}
