package TourPlannerUI.businesslayer;

import TourPlannerUI.model.TourItem;

import java.util.List;

public interface AppManager {

    public List<TourItem> GetItems();
    public List<TourItem> Search(String itemName, boolean caseSensitive);
}
