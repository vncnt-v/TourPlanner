package TourPlannerUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Logger log = LogManager.getLogger(Main.class);
        log.info("Application started");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TourPlanner.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.setStage(primaryStage);
        primaryStage.setTitle("Tour Planner");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(400);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
