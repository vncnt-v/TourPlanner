import TourPlannerUI.Tour.TourDetailModel;
import TourPlannerUI.model.TourItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TourDetailTest {

    TourItem tourItem;

    @BeforeEach
    void setUp() {
        tourItem = new TourItem(
                "Test",
                "Wien",
                "Berlin",
                ""
        );
    }

    @Test
    public void tourInputAccept() {
        TourDetailModel tourDetailModel = new TourDetailModel();
        tourDetailModel.Init(tourItem);
        assertNull(tourDetailModel.validateData());
        tourDetailModel.description.setValue("Lorem Ipsum");
        assertNull(tourDetailModel.validateData());
    }

    @Test
    public void tourInputEmptyName() {
        TourDetailModel tourDetailModel = new TourDetailModel();
        tourDetailModel.Init(tourItem);
        assertNull(tourDetailModel.validateData());
        tourDetailModel.name.setValue("");
        assertNotNull(tourDetailModel.validateData());
    }

    @Test
    public void tourInputEmptyStart() {
        TourDetailModel tourDetailModel = new TourDetailModel();
        tourDetailModel.Init(tourItem);
        assertNull(tourDetailModel.validateData());
        tourDetailModel.start.setValue("");
        assertNotNull(tourDetailModel.validateData());
    }

    @Test
    public void tourInputEmptyEnd() {
        TourDetailModel tourDetailModel = new TourDetailModel();
        tourDetailModel.Init(tourItem);
        assertNull(tourDetailModel.validateData());
        tourDetailModel.end.setValue("");
        assertNotNull(tourDetailModel.validateData());
    }

    @Test
    public void tourInputWrongStart() {
        TourDetailModel tourDetailModel = new TourDetailModel();
        tourDetailModel.Init(tourItem);
        assertNull(tourDetailModel.validateData());
        tourDetailModel.start.setValue("XYZ");
        assertNotNull(tourDetailModel.validateData());
    }

    @Test
    public void tourInputWrongEnd() {
        TourDetailModel tourDetailModel = new TourDetailModel();
        tourDetailModel.Init(tourItem);
        assertNull(tourDetailModel.validateData());
        tourDetailModel.end.setValue("XYZ");
        assertNotNull(tourDetailModel.validateData());
    }
}
