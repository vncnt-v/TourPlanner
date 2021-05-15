package TourPlannerUI;
import TourPlannerUI.model.TourLogEntry;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;

public class TourPlannerModel {

    ///////////////////////
    //     Properties    //
    ///////////////////////
    // Tours
    private StringProperty searchBar = new SimpleStringProperty("");
    //private StringProperty toursContainer = new SimpleStringProperty("");

    // Tour Detail
    private StringProperty tourDetailName = new SimpleStringProperty("Tour: ");
    //private Property<ImageView> tourDetailImage = new ImageView("");
    private StringProperty tourDetailDescription = new SimpleStringProperty("");

    // Tour Log
    private final ObservableList<TourLogEntry> tourLogEntries = FXCollections.observableArrayList();

    ////////////////////
    //    Bindings    //
    ////////////////////

    // Tours
    public StringProperty tours_search_bar_Property(){
        return searchBar;
    }
    //public StringProperty tour_container_Property(){ return toursContainer; }

    // Tour Detail
    public StringProperty tour_detail_name_Property(){
        return tourDetailName;
    }
    //public StringProperty tour_detail_image_Property(){ return tourDetailImage; }
    public StringProperty tour_detail_description_Property(){
        return tourDetailDescription;
    }

    public ObservableList<TourLogEntry> tour_log_table_Property(){ return tourLogEntries; }


    /////////////////////
    //      Tours      //
    /////////////////////
    // Select
    public void selectTour(){
        tourDetailDescription.setValue("Lorem");
        tourDetailName.setValue("Tour:  Home - FH Technikum");
        tourLogEntries.clear();
        tourLogEntries.add(new TourLogEntry("first entry", "15.07.2020","4:34","123","★★★★"));
        tourLogEntries.add(new TourLogEntry("second entry", "10.10.2020","6:12","321","★★★"));
        tourLogEntries.add(new TourLogEntry("third entry", "10.10.2020","3:50","231","★★★★"));
        System.out.println("ViewModel - Select Tour");
    }
    // Search
    public void searchTour(){
        System.out.println("Search: " + searchBar.getValue());
        System.out.println("ViewModel - Search Tour");
    }
    // Create
    public void createTour(){
        System.out.println("ViewModel - Create Tour");
    }
    // Delete
    public void deleteTour(){
        System.out.println("ViewModel - Delete Tour");
    }

    /////////////////////
    //   Tour Detail   //
    /////////////////////
    // Create Log
    public void createLog(){
        System.out.println("ViewModel - Create Log");
    }
    // Delete Log
    public void deleteLog(){
        System.out.println("ViewModel - Delete Log");
    }

}
