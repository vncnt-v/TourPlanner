package TourPlannerUI.dataaccesslayer.dao;

import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface ITourLogDAO {
    TourLog FindById(Integer logId) throws SQLException;
    TourLog AddNewItemLog(LocalDate date, String report, float distance, String startTime, String totalTime, int rating, int exhausting, float averageSpeed, float calories, String breaks, String weather, TourItem logItem) throws SQLException;
    boolean UpdateLog(TourLog tourLog) throws SQLException;
    List<TourLog> GetLogsForItem(TourItem tourItem) throws SQLException;
    boolean DeleteLog(Integer id) throws SQLException;
}
