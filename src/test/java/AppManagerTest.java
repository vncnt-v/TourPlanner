import TourPlannerUI.businesslayer.AppManager;
import TourPlannerUI.businesslayer.AppManagerFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AppManagerTest {

    AppManager manager;

    @BeforeEach
    void setUp() {
        manager = AppManagerFactory.GetManager();
    }

    @Test
    public void hasValidRoute() {
        assertTrue(manager.hasValidRoute("Berlin","Wien"));
    }

    @Test
    public void hasNotValidRoute() {
        assertFalse(manager.hasValidRoute("XZY","Wien"));
    }
}
