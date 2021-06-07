import TourPlannerUI.Log.LogDetailModel;
import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LogDetailTest {

    TourItem tourItem;
    TourLog tourLog;

    @BeforeEach
    void setUp() {
        tourItem = new TourItem(
                "Test",
                "Wien",
                "Berlin",
                ""
        );
        tourLog = new TourLog(
                LocalDate.now(),
                "",
                12.5f,
                "12:12",
                "1:00",
                5,
                5,
                12.23f,
                100,
                "0",
                "",
                tourItem
        );
    }

    @Test
    public void logInputAccept() {
        LogDetailModel logDetailModel = new LogDetailModel();
        logDetailModel.Init(tourItem,tourLog);
        assertNull(logDetailModel.validateData());
    }

    @Test
    public void logInputDenyStartTime() {
        LogDetailModel logDetailModel = new LogDetailModel();
        logDetailModel.Init(tourItem,tourLog);
        assertNull(logDetailModel.validateData());
        logDetailModel.startTimeField.setValue("");
        assertNotNull(logDetailModel.validateData());
        logDetailModel.startTimeField.setValue("24:00");
        assertNotNull(logDetailModel.validateData());
        logDetailModel.startTimeField.setValue("00:00");
        assertNull(logDetailModel.validateData());
        logDetailModel.startTimeField.setValue("0");
        assertNotNull(logDetailModel.validateData());
        logDetailModel.startTimeField.setValue("10");
        assertNotNull(logDetailModel.validateData());
        logDetailModel.startTimeField.setValue("10:99");
        assertNotNull(logDetailModel.validateData());
        logDetailModel.startTimeField.setValue("ab:cd");
        assertNotNull(logDetailModel.validateData());
        logDetailModel.startTimeField.setValue("a");
        assertNotNull(logDetailModel.validateData());
    }

    @Test
    public void logInputDenyTotalTime() {
        LogDetailModel logDetailModel = new LogDetailModel();
        logDetailModel.Init(tourItem,tourLog);
        assertNull(logDetailModel.validateData());
        logDetailModel.totalTimeField.setValue("");
        assertNotNull(logDetailModel.validateData());
        logDetailModel.totalTimeField.setValue("100:59");
        assertNull(logDetailModel.validateData());
        logDetailModel.totalTimeField.setValue("24:00");
        assertNull(logDetailModel.validateData());
        logDetailModel.totalTimeField.setValue("0");
        assertNotNull(logDetailModel.validateData());
        logDetailModel.totalTimeField.setValue("10");
        assertNotNull(logDetailModel.validateData());
        logDetailModel.totalTimeField.setValue("10:60");
        assertNotNull(logDetailModel.validateData());
        logDetailModel.totalTimeField.setValue("00:00");
        assertNotNull(logDetailModel.validateData());
        logDetailModel.totalTimeField.setValue("ab:cd");
        assertNotNull(logDetailModel.validateData());
        logDetailModel.totalTimeField.setValue("a");
        assertNotNull(logDetailModel.validateData());
    }

    @Test
    public void logInputDistanceTest() {
        LogDetailModel logDetailModel = new LogDetailModel();
        logDetailModel.Init(tourItem,tourLog);
        StringProperty newField = new SimpleStringProperty("123");
        newField.bindBidirectional(logDetailModel.getDistanceField());
        assertNull(logDetailModel.validateData());
        newField.setValue("0");
        assertNotNull(logDetailModel.validateData());
        newField.setValue("-123");
        assertNotNull(logDetailModel.validateData());
        newField.setValue("123,123");
        assertNull(logDetailModel.validateData());
        newField.setValue("123.123");
        assertNull(logDetailModel.validateData());
        newField.setValue("");
        assertNotNull(logDetailModel.validateData());
        newField.setValue("abc");
        assertNotNull(logDetailModel.validateData());
    }

    @Test
    public void logInputAverageSpeedTest() {
        LogDetailModel logDetailModel = new LogDetailModel();
        logDetailModel.Init(tourItem,tourLog);
        assertNull(logDetailModel.validateData());
        StringProperty newField = new SimpleStringProperty("123");
        newField.bindBidirectional(logDetailModel.getAverageSpeedField());
        assertNull(logDetailModel.validateData());
        newField.setValue("0");
        assertNotNull(logDetailModel.validateData());
        newField.setValue("123.123");
        assertNull(logDetailModel.validateData());
        newField.setValue("123,123");
        assertNull(logDetailModel.validateData());
        newField.setValue("978");
        assertNull(logDetailModel.validateData());
        newField.setValue("-123");
        assertNotNull(logDetailModel.validateData());
        newField.setValue("");
        assertNotNull(logDetailModel.validateData());
        newField.setValue("abc");
        assertNotNull(logDetailModel.validateData());
    }

    @Test
    public void logInputPositiveIntRatingExhaustingTest() {
        LogDetailModel logDetailModel = new LogDetailModel();
        logDetailModel.Init(tourItem,tourLog);
        assertNull(logDetailModel.validateData());
        logDetailModel.ratingField.setValue("0");
        assertNull(logDetailModel.validateData());
        logDetailModel.ratingField.setValue("1");
        assertNull(logDetailModel.validateData());
        logDetailModel.ratingField.setValue("2");
        assertNull(logDetailModel.validateData());
        logDetailModel.ratingField.setValue("3");
        assertNull(logDetailModel.validateData());
        logDetailModel.ratingField.setValue("4");
        assertNull(logDetailModel.validateData());
        logDetailModel.ratingField.setValue("5");
        assertNull(logDetailModel.validateData());
        logDetailModel.ratingField.setValue("-5");
        assertNotNull(logDetailModel.validateData());
        logDetailModel.ratingField.setValue("6");
        assertNotNull(logDetailModel.validateData());
        logDetailModel.ratingField.setValue("abc");
        assertNotNull(logDetailModel.validateData());
        logDetailModel.ratingField.setValue("0");
        assertNull(logDetailModel.validateData());
        logDetailModel.exhaustingField.setValue("0");
        assertNull(logDetailModel.validateData());
        logDetailModel.exhaustingField.setValue("1");
        assertNull(logDetailModel.validateData());
        logDetailModel.exhaustingField.setValue("2");
        assertNull(logDetailModel.validateData());
        logDetailModel.exhaustingField.setValue("3");
        assertNull(logDetailModel.validateData());
        logDetailModel.exhaustingField.setValue("4");
        assertNull(logDetailModel.validateData());
        logDetailModel.exhaustingField.setValue("5");
        assertNull(logDetailModel.validateData());
        logDetailModel.exhaustingField.setValue("-5");
        assertNotNull(logDetailModel.validateData());
        logDetailModel.exhaustingField.setValue("6");
        assertNotNull(logDetailModel.validateData());
        logDetailModel.exhaustingField.setValue("abc");
        assertNotNull(logDetailModel.validateData());
    }
}
