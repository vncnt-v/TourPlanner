package TourPlannerUI.dataaccesslayer.postgresSqlServer;

import TourPlannerUI.dataaccesslayer.common.DALFactory;
import TourPlannerUI.dataaccesslayer.common.IDatabase;
import TourPlannerUI.dataaccesslayer.dao.ITourItemDAO;
import TourPlannerUI.model.TourItem;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TourItemPostgresDAO implements ITourItemDAO {

    private final String SQL_FIND_BY_ID = "SELECT * FROM \"TourItem\" WHERE \"Id\"=CAST(? AS INTEGER);";
    private final String SQL_GET_ALL_ITEMS = "SELECT * FROM \"TourItem\";";
    private final String SQL_INSERT_NEW_ITEM = "INSERT INTO \"TourItem\" (\"Name\",\"Start\",\"End\",\"Description\",\"Distance\") VALUES (?, ?, ?, ?, CAST(? AS FLOAT));";
    private final String SQL_UPDATE_ITEM = "UPDATE \"TourItem\" SET \"Name\" = ?,\"Start\" = ?,\"End\" = ?,\"Description\" = ?,\"Distance\" = CAST(? AS FLOAT) WHERE \"Id\"=CAST(? AS INTEGER);";
    private final String SQL_DELETE_ITEM = "DELETE FROM \"TourItem\" WHERE \"Id\"=CAST(? AS INTEGER);";

    private IDatabase database;

    public TourItemPostgresDAO() throws FileNotFoundException {
        database = DALFactory.GetDatabase();
    }

    @Override
    public TourItem FindById(Integer itemId) throws SQLException {
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(itemId);
        List<TourItem> tourItems = database.TourReader(SQL_FIND_BY_ID, parameters, TourItem.class);

        return tourItems.stream().findFirst().get();
    }

    @Override
    public TourItem AddNewItem(String name, String start, String end, String description, float distance) throws SQLException {
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(name);
        parameters.add(start);
        parameters.add(end);
        parameters.add(description);
        parameters.add(distance);

        int resultID = database.InsertNew(SQL_INSERT_NEW_ITEM, parameters);
        return FindById(resultID);
    }

    @Override
    public boolean UpdateItem(TourItem tourItem) throws SQLException {
        ArrayList<Object> parameters = new ArrayList<>();

        parameters.add(tourItem.getName());
        parameters.add(tourItem.getStart());
        parameters.add(tourItem.getEnd());
        parameters.add(tourItem.getDescription());
        parameters.add(tourItem.getDistance());
        parameters.add(tourItem.getId());

        int check = database.UpdateEntry(SQL_UPDATE_ITEM, parameters);
        if(check > 0){
            return true;
        }
        return false;
    }

    @Override
    public List<TourItem> GetItems() throws SQLException {
        return database.TourReader(SQL_GET_ALL_ITEMS, TourItem.class);
    }

    @Override
    public boolean DeleteItem(Integer itemId) throws SQLException {
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(itemId);
        database.DeleteEntry(SQL_DELETE_ITEM, parameters);
        return true;
    }
}
