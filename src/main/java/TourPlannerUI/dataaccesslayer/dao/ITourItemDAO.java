package TourPlannerUI.dataaccesslayer.dao;

import TourPlannerUI.model.TourItem;

import java.sql.SQLException;
import java.util.List;

public interface ITourItemDAO {
    TourItem FindById(Integer itemId) throws SQLException;
    TourItem AddNewItem(String name, String start, String end, String description, float distance) throws SQLException;
    boolean UpdateItem(TourItem tourItem) throws SQLException;
    List<TourItem> GetItems() throws SQLException;
    boolean DeleteItem(Integer id) throws SQLException;
}
