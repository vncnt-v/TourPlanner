package TourPlannerUI.Log;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

public class LogDetailModel {

    @Getter
    private ObservableList<Integer> ratingChoices = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5);
    @Getter
    private ObservableList<Integer> exhaustingChoices = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5);
    @Getter
    private ObservableList<String> breaksChoices = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "5+");
    @Getter
    private ObservableList<String> weatherChoices = FXCollections.observableArrayList("Sunny", "Cloudy", "Snowy", "Windy", "Rainy", "Foggy");
}
