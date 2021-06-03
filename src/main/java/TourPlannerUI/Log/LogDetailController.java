package TourPlannerUI.Log;

import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class LogDetailController {

    public LogDetailModel viewModel = new LogDetailModel();

    @FXML
    public DatePicker dateField;
    @FXML
    public TextField startTimeField;
    @FXML
    public TextField distanceField;
    @FXML
    public TextField totalTimeField;
    @FXML
    public ChoiceBox<String> ratingField;
    @FXML
    public ChoiceBox<String> exhaustingField;
    @FXML
    public TextField averageSpeedField;
    @FXML
    public TextField caloriesField;
    @FXML
    public ChoiceBox<String> breaksField;
    @FXML
    public ChoiceBox<String> weatherField;
    @FXML
    public TextArea reportArea;

    public void Init(TourItem tourItem,TourLog tourLog, Dialog<ButtonType> dialog){
        dateField.valueProperty().bindBidirectional(viewModel.getDateField());
        startTimeField.textProperty().bindBidirectional(viewModel.getStartTimeField());
        totalTimeField.textProperty().bindBidirectional(viewModel.getTotalTimeField());
        distanceField.textProperty().bindBidirectional(viewModel.getDistanceField(),new NumberStringConverter());
        ratingField.valueProperty().bindBidirectional(viewModel.getRatingField());
        exhaustingField.valueProperty().bindBidirectional(viewModel.getExhaustingField());
        averageSpeedField.textProperty().bindBidirectional(viewModel.getAverageSpeedField(),new NumberStringConverter());
        caloriesField.textProperty().bindBidirectional(viewModel.getCaloriesField(),new NumberStringConverter());
        breaksField.valueProperty().bindBidirectional(viewModel.getBreaksField());
        weatherField.valueProperty().bindBidirectional(viewModel.getWeatherField());
        reportArea.textProperty().bindBidirectional(viewModel.getReportArea());

        ratingField.setItems(viewModel.getRatingChoices());
        exhaustingField.setItems(viewModel.getExhaustingChoices());
        breaksField.setItems(viewModel.getBreaksChoices());
        weatherField.setItems(viewModel.getWeatherChoices());

        viewModel.Init(tourItem, tourLog);

        final Button btOk = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        btOk.addEventFilter(
            ActionEvent.ACTION,
            event -> {
                if (!viewModel.validateData()){
                    event.consume();
                }
            }
        );
    }
    public boolean updateTourLog () throws SQLException {
        return viewModel.updateTourLog();
    }
    public TourLog createTourLog () throws IOException, SQLException, ParseException {
        return viewModel.createTourLog();
    }
}

