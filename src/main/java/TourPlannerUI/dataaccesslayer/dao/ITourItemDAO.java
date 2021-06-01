package TourPlannerUI.dataaccesslayer.dao;

import TourPlannerUI.model.TourItem;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ITourItemDAO {
    TourItem FindById(Integer itemId) throws SQLException, IOException;
    TourItem AddNewItem(String name, String start, String end, String description, float distance) throws SQLException, IOException;
    boolean UpdateItem(TourItem tourItem) throws SQLException;
    List<TourItem> GetItems() throws SQLException, IOException;
    boolean DeleteItem(Integer id) throws SQLException;
}
