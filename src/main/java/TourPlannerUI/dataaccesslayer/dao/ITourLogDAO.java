package TourPlannerUI.dataaccesslayer.dao;

import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public interface ITourLogDAO {
    TourLog FindById(Integer logId) throws SQLException;
    TourLog AddNewItemLog(LocalDateTime dateTime, String report, float distance, Duration totalTime, int rating, int exhausting, float averageSpeed, float calories, int breaks, String weather, TourItem logItem) throws SQLException;

    List<TourLog> GetLogsForItem(TourItem tourItem) throws SQLException;
    boolean DeleteLog(Integer id) throws SQLException;
}
