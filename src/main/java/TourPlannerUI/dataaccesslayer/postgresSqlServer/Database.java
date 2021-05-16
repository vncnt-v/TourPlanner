package TourPlannerUI.dataaccesslayer.postgresSqlServer;

import TourPlannerUI.dataaccesslayer.common.DALFactory;
import TourPlannerUI.dataaccesslayer.common.IDatabase;
import TourPlannerUI.dataaccesslayer.dao.ITourItemDAO;
import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Database implements IDatabase {

    private String connectionString;

    public Database(String connectionString){
        this.connectionString = connectionString;
    }

    private Connection CreateConnection() throws SQLException {
        try {
            return DriverManager.getConnection(connectionString, "postgres", "123");
        } catch (SQLException e){
            e.printStackTrace();
        }
        throw new SQLException("Establishing connection failed.");
    }
    @Override
    public int InsertNew(String sqlQuery, ArrayList<Object> parameters) throws SQLException {
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
        } catch (SQLException e){
            e.printStackTrace();
        }
        throw new SQLException("Creating data failed, no ID obtained. " + sqlQuery);
    }

    @Override
    public int DeleteEntry(String sqlQuery, ArrayList<Object> parameters) throws SQLException {
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
        } catch (SQLException e){
            e.printStackTrace();
        }
        throw new SQLException("Delete data failed, no ID obtained. " + sqlQuery);
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
        } catch (SQLException e){
            e.printStackTrace();
        }
        throw new SQLException("Reading data failed. " + sqlQuery);
    }

    @Override
    public <T> List<T> TourReader(String sqlQuery, ArrayList<Object> parameters, Class<T> tourType) throws SQLException {
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
        } catch (SQLException e){
            e.printStackTrace();
        }
        throw new SQLException("Creating data failed, no ID obtained. " + sqlQuery);
    }

    private List<TourItem> QueryTourItemDataFromResultSet(ResultSet result) throws SQLException {
        List<TourItem> tourItemList = new ArrayList<>();

        while (result.next()) {
            tourItemList.add(new TourItem(
                result.getInt("Id"),
                result.getString("Name"),
                result.getString("Description"),
                result.getFloat("Distance")
            ));
        }
        return tourItemList;
    }

    private List<TourLog> QueryDataLogDataFromResultSet(ResultSet result) throws SQLException {
        List<TourLog> tourLogList = new ArrayList<>();
        ITourItemDAO tourItemDAO = DALFactory.CreateTourItemDAO();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        while (result.next()) {
                tourLogList.add(new TourLog(
                    result.getInt("Id"),
                    LocalDateTime.parse(result.getString("DateTime"), formatter),
                    result.getString("Report"),
                    result.getFloat("Distance"),
                    Duration.parse(result.getString("TotalTime")),
                    result.getInt("Rating"),
                    result.getInt("Exhausting"),
                    result.getFloat("AverageSpeed"),
                    result.getFloat("Calories"),
                    result.getInt("Breaks"),
                    result.getString("Weather"),
                    tourItemDAO.FindById(result.getInt("TourItemId"))
            ));
        }
        return tourLogList;
    }
}
