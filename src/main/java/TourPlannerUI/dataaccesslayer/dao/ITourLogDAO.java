package TourPlannerUI.dataaccesslayer.dao;

import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface ITourLogDAO {
    TourLog FindById(Integer logId) throws SQLException, IOException, ParseException;
    TourLog AddNewItemLog(TourLog tourLog, TourItem logItem) throws SQLException, IOException, ParseException;
    boolean UpdateLog(TourLog tourLog) throws SQLException;
    List<TourLog> GetLogsForItem(TourItem tourItem) throws SQLException, IOException, ParseException;
    boolean DeleteLog(Integer id) throws SQLException;
}
