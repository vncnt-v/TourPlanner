import TourPlannerUI.businesslayer.AppManager;
import TourPlannerUI.businesslayer.AppManagerFactory;
import TourPlannerUI.dataaccesslayer.common.DALFactory;
import TourPlannerUI.dataaccesslayer.fileServer.FileAccess;
import TourPlannerUI.dataaccesslayer.postgresSqlServer.TourItemPostgresDAO;
import TourPlannerUI.dataaccesslayer.postgresSqlServer.TourLogPostgresDAO;
import TourPlannerUI.model.TourItem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import TourPlannerUI.model.TourLog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AppManagerTest {

    @Mock
    TourItemPostgresDAO tourItemPostgresDAO;

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

    /** Tours **/
    @Test
    public void getTours() throws SQLException {
        ArrayList<TourItem> tourItems = new ArrayList<>();
        tourItems.add(new TourItem(
                "Test",
                "Wien",
                "Berlin",
                ""
        ));
        tourItems.add(new TourItem(
                "Test02",
                "Berlin",
                "Wien",
                ""
        ));
        tourItems.add(new TourItem(
                "Test03",
                "Innsbruck",
                "Wien",
                ""
        ));
        tourItems.add(new TourItem(
                "Test04",
                "Wien",
                "Innsbruck",
                ""
        ));
        tourItemPostgresDAO = mock(TourItemPostgresDAO.class);
        when(tourItemPostgresDAO.GetItems())
                .thenReturn(tourItems);
        try (MockedStatic<DALFactory> mb = Mockito.mockStatic(DALFactory.class)) {
            mb.when(DALFactory::CreateTourItemDAO)
                    .thenReturn(tourItemPostgresDAO);
            assertEquals(manager.GetItems().get(3).getName(),tourItems.get(3).getName());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addTour() throws SQLException, IOException {
        TourItem tourItem = new TourItem(
                "***Test***",
                "Wien",
                "Berlin",
                ""
        );
        tourItemPostgresDAO = mock(TourItemPostgresDAO.class);
        FileAccess fileAccess = mock(FileAccess.class);
        when(fileAccess.saveImage(any(),anyInt()))
                .thenReturn(true);
        when(tourItemPostgresDAO.AddNewItem(any()))
                .thenReturn(tourItem);
        try (MockedStatic<DALFactory> mb = Mockito.mockStatic(DALFactory.class)) {
            mb.when(DALFactory::CreateTourItemDAO)
                    .thenReturn(tourItemPostgresDAO);
            mb.when(DALFactory::GetFileAccess)
                    .thenReturn(fileAccess);
            assertEquals(manager.CreateTourItem(tourItem).getName(),tourItem.getName());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void searchTour() throws SQLException {
        ArrayList<TourItem> tourItems = new ArrayList<>();
        tourItems.add(new TourItem(
                "Test",
                "Wien",
                "Berlin",
                ""
        ));
        tourItems.add(new TourItem(
                "Test02",
                "Berlin",
                "Wien",
                ""
        ));
        tourItems.add(new TourItem(
                "Test03",
                "Innsbruck",
                "Wien",
                ""
        ));
        tourItems.add(new TourItem(
                "Test04",
                "Wien",
                "Innsbruck",
                ""
        ));
        tourItemPostgresDAO = mock(TourItemPostgresDAO.class);
        when(tourItemPostgresDAO.GetItems())
                .thenReturn(tourItems);
        try (MockedStatic<DALFactory> mb = Mockito.mockStatic(DALFactory.class)) {
            mb.when(DALFactory::CreateTourItemDAO)
                    .thenReturn(tourItemPostgresDAO);
            assertEquals(manager.Search("Test03",false).size(),1);
            assertEquals(manager.Search("Test03",false).get(0).getName(),tourItems.get(2).getName());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    /** Logs **/
    @Test
    public void GetLogsForItem() throws SQLException, IOException {
        TourItem tourItem = new TourItem(
                0,
                "Test",
                "Wien",
                "Berlin",
                "",
                12
        );
        ArrayList<TourLog> tourLogs = new ArrayList<>();
        tourLogs.add(new TourLog(
                LocalDate.now(),
                "123",
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
        ));
        tourLogs.add(new TourLog(
                LocalDate.now(),
                "321",
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
        ));
        TourLogPostgresDAO tourLogPostgresDAO = mock(TourLogPostgresDAO.class);
        when(tourLogPostgresDAO.GetLogsForItem(tourItem))
                .thenReturn(tourLogs);
        try (MockedStatic<DALFactory> mb = Mockito.mockStatic(DALFactory.class)) {
            mb.when(DALFactory::CreateTourLogDAO)
                    .thenReturn(tourLogPostgresDAO);
            assertEquals(manager.GetLogsForItem(tourItem).size(),2);
            assertEquals(manager.GetLogsForItem(tourItem).get(0).getReport(),tourLogs.get(0).getReport());
        } catch (SQLException | IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addLog() throws SQLException, IOException {
        TourItem tourItem = new TourItem(
                "***Test***",
                "Wien",
                "Berlin",
                ""
        );
        TourLog tourLog = new TourLog(
                LocalDate.now(),
                "321",
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
        TourLogPostgresDAO tourLogItemPostgresDAO = mock(TourLogPostgresDAO.class);
        when(tourLogItemPostgresDAO.AddNewItemLog(any()))
                .thenReturn(tourLog);
        try (MockedStatic<DALFactory> mb = Mockito.mockStatic(DALFactory.class)) {
            mb.when(DALFactory::CreateTourLogDAO)
                    .thenReturn(tourLogItemPostgresDAO);
            assertEquals(manager.CreateTourLog(tourLog).getReport(),tourLog.getReport());
            verify(tourLogItemPostgresDAO).AddNewItemLog(any());
        } catch (SQLException | IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
