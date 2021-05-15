package TourPlannerUI.businesslayer;

import TourPlannerUI.model.TourItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AppManagerImpl implements AppManager {

    //TourItemDAO tourItemDAO = new TourItemDAO();

    @Override
    public List<TourItem> GetItems() {
        return null; //tourItemDAO.GetItems();
    }

    @Override
    public List<TourItem> Search(String itemName, boolean caseSensitive) {
        List<TourItem> items = GetItems();

        if(caseSensitive){
            return items
                    .stream()
                    .filter(x -> x.getName().contains(itemName))
                    .collect(Collectors.toList());
        }

        return items
                .stream()
                .filter(x -> x.getName().toLowerCase().contains(itemName.toLowerCase()))
                .collect(Collectors.toList());
    }
}
