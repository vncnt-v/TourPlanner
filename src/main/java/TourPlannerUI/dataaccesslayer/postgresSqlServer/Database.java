package TourPlannerUI.dataaccesslayer.postgresSqlServer;

import TourPlannerUI.businesslayer.ConfigurationManager;
import TourPlannerUI.dataaccesslayer.common.DALFactory;
import TourPlannerUI.dataaccesslayer.common.IDatabase;
import TourPlannerUI.dataaccesslayer.dao.ITourItemDAO;
import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class Database implements IDatabase {

    private final String connectionString;

    public Database(String connectionString){
        this.connectionString = connectionString;
    }

    private Connection CreateConnection() throws SQLException, FileNotFoundException {
        String usernameString = ConfigurationManager.GetConfigProperty("PostgresSqlUsername");
        String pwdString = ConfigurationManager.GetConfigProperty("PostgresSqlPwd");
        try {
            return DriverManager.getConnection(connectionString, usernameString, pwdString);
        } catch (SQLException e){
            Logger log = LogManager.getLogger(Database.class);
            log.error("Establishing connection failed: " + e.getMessage());
            e.printStackTrace();
        }
        throw new SQLException("Establishing connection failed.");
    }
    @Override
    public int InsertNew(String sqlQuery, ArrayList<Object> parameters) throws SQLException {
        return executeStatement(sqlQuery,parameters);
    }

    @Override
    public int UpdateEntry(String sqlQuery, ArrayList<Object> parameters) throws SQLException {
        return executeStatement(sqlQuery,parameters);
    }

    @Override
    public void DeleteEntry(String sqlQuery, ArrayList<Object> parameters) throws SQLException {
        executeStatement(sqlQuery, parameters);
    }

    public int executeStatement(String sqlQuery, ArrayList<Object> parameters) throws SQLException {
        try(Connection conn = CreateConnection();
            PreparedStatement pre = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {

            for (int i = 0; i < parameters.size(); i++) {
                pre.setString(i+1,parameters.get(i).toString());
            }
            int affectedRows = pre.executeUpdate();

            if (affectedRows > 0){
                try (ResultSet generatedKeys = pre.getGeneratedKeys()){
                    if (generatedKeys.next()){
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException | FileNotFoundException e){
            Logger log = LogManager.getLogger(Database.class);
            log.error("SQL Error: " + sqlQuery + " - " + e.getMessage());
            e.printStackTrace();
        }
        throw new SQLException("SQL Error " + sqlQuery);
    }

    @Override
    public <T> List<T> TourReader(String sqlQuery, Class<T> tourType) throws SQLException {
        try(Connection conn = CreateConnection();
            Statement statement = conn.createStatement()) {

            ResultSet result = statement.executeQuery(sqlQuery);
            if(tourType.getTypeName().equals(TourItem.class.getName())) {
                return (List<T>) QueryTourItemDataFromResultSet(result);
            }
            if(tourType.getTypeName().equals(TourLog.class.getName())) {
                return (List<T>) QueryDataLogDataFromResultSet(result);
            }
        } catch (SQLException | ParseException | IOException e){
            Logger log = LogManager.getLogger(Database.class);
            log.error("Reading data failed: " + sqlQuery + " - " + e.getMessage());
            e.printStackTrace();
        }
        throw new SQLException("Reading data failed. " + sqlQuery);
    }

    @Override
    public <T> List<T> TourReader(String sqlQuery, ArrayList<Object> parameters, Class<T> tourType) throws SQLException, IOException {
        try(Connection conn = CreateConnection();
            PreparedStatement pre = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {

            for (int i = 0; i < parameters.size(); i++) {
                pre.setString(i+1,parameters.get(i).toString());
            }
            ResultSet result = pre.executeQuery();
            if(tourType.getTypeName().equals(TourItem.class.getName())) {
                return (List<T>) QueryTourItemDataFromResultSet(result);
            }
            if(tourType.getTypeName().equals(TourLog.class.getName())) {
                return (List<T>) QueryDataLogDataFromResultSet(result);
            }
        } catch (SQLException | ParseException | FileNotFoundException e){
            Logger log = LogManager.getLogger(Database.class);
            log.error("Creating data failed: " + sqlQuery + " - " + e.getMessage());
            e.printStackTrace();
        }
        throw new SQLException("Creating data failed: " + sqlQuery);
    }

    private List<TourItem> QueryTourItemDataFromResultSet(ResultSet result) throws SQLException {
        List<TourItem> tourItemList = new ArrayList<>();

        while (result.next()) {
            tourItemList.add(new TourItem(
                result.getInt("Id"),
                result.getString("Name"),
                result.getString("Start"),
                result.getString("End"),
                result.getString("Description"),
                result.getFloat("Distance")
            ));
        }
        return tourItemList;
    }

    private List<TourLog> QueryDataLogDataFromResultSet(ResultSet result) throws SQLException, ParseException, IOException {
        List<TourLog> tourLogList = new ArrayList<>();
        ITourItemDAO tourItemDAO = DALFactory.CreateTourItemDAO();
        while (result.next()) {
            assert tourItemDAO != null;
            tourLogList.add(new TourLog(
                    result.getInt("Id"),
                    new SimpleDateFormat("yyyy-MM-dd").parse(result.getString("Date")).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    result.getString("Report"),
                    result.getFloat("Distance"),
                    result.getString("StartTime"),
                    result.getString("TotalTime"),
                    result.getInt("Rating"),
                    result.getInt("Exhausting"),
                    result.getFloat("AverageSpeed"),
                    result.getFloat("Calories"),
                    result.getString("Breaks"),
                    result.getString("Weather"),
                    tourItemDAO.FindById(result.getInt("TourItemId"))
            ));
        }
        return tourLogList;
    }
}
