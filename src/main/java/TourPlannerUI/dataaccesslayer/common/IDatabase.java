package TourPlannerUI.dataaccesslayer.common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IDatabase {
    int InsertNew(String sqlQuery, ArrayList<Object> parameters) throws SQLException;
    int DeleteEntry(String sqlQuery, ArrayList<Object> parameters) throws SQLException;
    <T> List<T> TourReader(String sqlQuery, Class<T> tourType) throws SQLException;
    <T>List<T> TourReader(String sqlQuery, ArrayList<Object> parameters, Class<T> tourType) throws SQLException;
}
