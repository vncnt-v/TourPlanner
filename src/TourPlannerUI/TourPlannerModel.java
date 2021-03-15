package TourPlannerUI;
import TourPlannerUI.model.TourDetailEntry;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourPlannerModel {

    ///////////////////////
    //     Properties    //
    ///////////////////////

    // Views
    private Property<Boolean> tourNewView = new SimpleBooleanProperty(false);
    private Property<Boolean> tourDetailView = new SimpleBooleanProperty(false);
    private Property<Boolean> routeNewView = new SimpleBooleanProperty(false);
    private Property<Boolean> routeDetailView = new SimpleBooleanProperty(false);

    // Tour New
    private StringProperty tourNewName = new SimpleStringProperty("");
    private StringProperty tourNewStart = new SimpleStringProperty("");
    private StringProperty tourNewEnd = new SimpleStringProperty("");
    private StringProperty tourNewDescription = new SimpleStringProperty("");

    // Tour Detail
    private StringProperty tourDetailName = new SimpleStringProperty("");
    //private Property<ImageView> tourDetailImage = new ImageView("");
    private StringProperty tourDetailDescription = new SimpleStringProperty("");
    private final ObservableList<TourDetailEntry> tourDetaiTableView =
            FXCollections.observableArrayList(
                    new TourDetailEntry("Über Melk", "5:30","12","15.07.2020"),
                    new TourDetailEntry("mit Andi 2", "4:57","12.5","10.10.2020"),
                    new TourDetailEntry("mit Andi", "4:17","12.5","10.10.2020")
            );

    ////////////////////
    //    Bindings    //
    ////////////////////

    // Views
    public Property<Boolean> tour_new_ViewProperty(){
        return tourNewView;
    }
    public Property<Boolean> tour_detail_ViewProperty(){
        return tourDetailView;
    }
    public Property<Boolean> route_new_ViewProperty(){
        return routeNewView;
    }
    public Property<Boolean> route_detail_ViewProperty(){
        return routeDetailView;
    }

    // Tour New
    public StringProperty tour_new_name_Property(){
        return tourNewName;
    }
    public StringProperty tour_new_start_Property(){
        return tourNewStart;
    }
    public StringProperty tour_new_end_Property(){
        return tourNewEnd;
    }
    public StringProperty tour_new_description_Property(){
        return tourNewDescription;
    }

    // Tour Detail
    public StringProperty tour_detail_name_Property(){
        return tourDetailName;
    }
    public StringProperty tour_detail_description_Property(){
        return tourDetailDescription;
    }
    public ObservableList<TourDetailEntry> tour_detail_table_view_Property(){
        return tourDetaiTableView;
    }

    //////////////////////////
    // Change Views Visible //
    //////////////////////////
    public void setTourNewVisible(boolean bool) {
        this.tourNewView.setValue(bool);
    }
    public void setTourDetailVisible(boolean bool) {
        setNewDetailFields("HE1","charlie");
        this.tourDetailView.setValue(bool);
    }
    public void setRouteNewVisible(boolean bool) {
        this.routeNewView.setValue(bool);
    }
    public void setRouteDetailVisible(boolean bool) {
        this.routeDetailView.setValue(bool);
    }

    ///////////////////////////
    // Clear Tour New Fields //
    ///////////////////////////
    private void clearNewTourFields(){
        tourNewName.set("");
        tourNewStart.set("");
        tourNewEnd.set("");
        tourNewDescription.set("");
    }

    ////////////////////////////
    // Set Tour Detail Fields //
    ////////////////////////////
    public void setNewDetailFields(String name, String description){
        tourDetailName.set(name);
        //tourDetailImage.set("");
        tourDetailDescription.set(description);
        //tourDetailTable.set("");
    }
}
