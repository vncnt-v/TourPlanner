package TourPlannerUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("TourPlanner.fxml"));
        primaryStage.setTitle("Tour Planner");
        primaryStage.setScene(new Scene(root, 1200, 675));
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(675);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
