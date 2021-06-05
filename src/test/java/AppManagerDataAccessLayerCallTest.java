import TourPlannerUI.businesslayer.AppManager;
import TourPlannerUI.businesslayer.AppManagerFactory;
import TourPlannerUI.dataaccesslayer.common.DALFactory;
import TourPlannerUI.dataaccesslayer.fileServer.FileAccess;
import TourPlannerUI.dataaccesslayer.postgresSqlServer.TourItemPostgresDAO;
import TourPlannerUI.dataaccesslayer.postgresSqlServer.TourLogPostgresDAO;
import TourPlannerUI.model.TourItem;

import TourPlannerUI.model.TourLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AppManagerDataAccessLayerCallTest {

    AppManager manager;

    @BeforeEach
    void setUp() {
        manager = AppManagerFactory.GetManager();
    }

    @Test
    public void GetItemsTest() throws SQLException {
        TourItemPostgresDAO tourItemPostgresDAO = mock(TourItemPostgresDAO.class);
        when(tourItemPostgresDAO.GetItems())
                .thenReturn(new ArrayList<>());
        try (MockedStatic<DALFactory> mb = Mockito.mockStatic(DALFactory.class)) {
            mb.when(DALFactory::CreateTourItemDAO)
                    .thenReturn(tourItemPostgresDAO);
            assertEquals(0,manager.GetItems().size());
            verify(tourItemPostgresDAO).GetItems();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void SearchItemsTest() throws SQLException {
        TourItemPostgresDAO tourItemPostgresDAO = mock(TourItemPostgresDAO.class);
        when(tourItemPostgresDAO.GetItems())
                .thenReturn(new ArrayList<>());
        try (MockedStatic<DALFactory> mb = Mockito.mockStatic(DALFactory.class)) {
            mb.when(DALFactory::CreateTourItemDAO)
                    .thenReturn(tourItemPostgresDAO);
            assertEquals(0,manager.Search("Test",false).size());
            verify(tourItemPostgresDAO).GetItems();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void CreateTourItemTest() throws SQLException, IOException {
        TourItem tourItem = new TourItem("","Wien","Linz","");
        TourItemPostgresDAO tourItemPostgresDAO = mock(TourItemPostgresDAO.class);
        when(tourItemPostgresDAO.AddNewItem(any()))
                .thenReturn(tourItem);
        FileAccess fileAccess = mock(FileAccess.class);
        when(fileAccess.saveImage(any(),anyByte()))
                .thenReturn(true);
        try (MockedStatic<DALFactory> mb = Mockito.mockStatic(DALFactory.class)) {
            mb.when(DALFactory::CreateTourItemDAO)
                    .thenReturn(tourItemPostgresDAO);
            mb.when(DALFactory::GetFileAccess)
                    .thenReturn(fileAccess);
            manager.CreateTourItem(tourItem);
            verify(tourItemPostgresDAO).AddNewItem(any());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void UpdateTourItemTest() throws SQLException {
        TourItem tourItem = new TourItem("","Wien","Linz","");
        TourItemPostgresDAO tourItemPostgresDAO = mock(TourItemPostgresDAO.class);
        when(tourItemPostgresDAO.UpdateItem(any()))
                .thenReturn(true);
        FileAccess fileAccess = mock(FileAccess.class);
        when(fileAccess.saveImage(any(),anyByte()))
                .thenReturn(true);
        try (MockedStatic<DALFactory> mb = Mockito.mockStatic(DALFactory.class)) {
            mb.when(DALFactory::CreateTourItemDAO)
                    .thenReturn(tourItemPostgresDAO);
            mb.when(DALFactory::GetFileAccess)
                    .thenReturn(fileAccess);
            manager.UpdateTourItem(tourItem);
            verify(tourItemPostgresDAO).UpdateItem(any());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void DeleteTourItemTest() throws SQLException {
        TourItemPostgresDAO tourItemPostgresDAO = mock(TourItemPostgresDAO.class);
        when(tourItemPostgresDAO.DeleteItem(anyInt()))
                .thenReturn(true);
        FileAccess fileAccess = mock(FileAccess.class);
        when(fileAccess.saveImage(any(),anyByte()))
                .thenReturn(true);
        try (MockedStatic<DALFactory> mb = Mockito.mockStatic(DALFactory.class)) {
            mb.when(DALFactory::CreateTourItemDAO)
                    .thenReturn(tourItemPostgresDAO);
            mb.when(DALFactory::GetFileAccess)
                    .thenReturn(fileAccess);
            manager.DeleteTourItem(-1);
            verify(tourItemPostgresDAO).DeleteItem(anyInt());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void CreateTourLogTest() throws SQLException, IOException {
        TourItem tourItem = new TourItem("","Wien","Linz","");
        TourLog tourLog = new TourLog(LocalDate.now(), "123", 12.5f, "12:12", "1:00", 5, 5, 12.23f, 100, "0", "", tourItem);
        TourLogPostgresDAO tourLogPostgresDAO = mock(TourLogPostgresDAO.class);
        when(tourLogPostgresDAO.AddNewItemLog(any()))
                .thenReturn(tourLog);
        try (MockedStatic<DALFactory> mb = Mockito.mockStatic(DALFactory.class)) {
            mb.when(DALFactory::CreateTourLogDAO)
                    .thenReturn(tourLogPostgresDAO);
            manager.CreateTourLog(tourLog);
            verify(tourLogPostgresDAO).AddNewItemLog(any());
        } catch (SQLException | IOException | ParseException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void UpdateTourLogTest() throws SQLException {
        TourItem tourItem = new TourItem("","Wien","Linz","");
        TourLog tourLog = new TourLog(LocalDate.now(), "123", 12.5f, "12:12", "1:00", 5, 5, 12.23f, 100, "0", "", tourItem);
        TourLogPostgresDAO tourLogPostgresDAO = mock(TourLogPostgresDAO.class);
        when(tourLogPostgresDAO.UpdateLog(any()))
                .thenReturn(true);
        try (MockedStatic<DALFactory> mb = Mockito.mockStatic(DALFactory.class)) {
            mb.when(DALFactory::CreateTourLogDAO)
                    .thenReturn(tourLogPostgresDAO);
            manager.UpdateTourLog(tourLog);
            verify(tourLogPostgresDAO).UpdateLog(any());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void DeleteTourLogTest() throws SQLException {
        TourLogPostgresDAO tourLogPostgresDAO = mock(TourLogPostgresDAO.class);
        when(tourLogPostgresDAO.DeleteLog(any()))
                .thenReturn(true);
        try (MockedStatic<DALFactory> mb = Mockito.mockStatic(DALFactory.class)) {
            mb.when(DALFactory::CreateTourLogDAO)
                    .thenReturn(tourLogPostgresDAO);
            manager.DeleteTourLog(-1);
            verify(tourLogPostgresDAO).DeleteLog(any());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void GetLogsForItemTest() throws SQLException, IOException {
        TourItem tourItem = new TourItem("","Wien","Linz","");
        TourLogPostgresDAO tourLogPostgresDAO = mock(TourLogPostgresDAO.class);
        when(tourLogPostgresDAO.GetLogsForItem(any()))
                .thenReturn(new ArrayList<>());
        try (MockedStatic<DALFactory> mb = Mockito.mockStatic(DALFactory.class)) {
            mb.when(DALFactory::CreateTourLogDAO)
                    .thenReturn(tourLogPostgresDAO);
            assertEquals(0,manager.GetLogsForItem(tourItem).size());
            verify(tourLogPostgresDAO).GetLogsForItem(any());
        } catch (SQLException | IOException | ParseException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void ImportTourTest() {
        FileAccess fileAccess = mock(FileAccess.class);
        when(fileAccess.loadFile(any()))
                .thenReturn(null);
        try (MockedStatic<DALFactory> mb = Mockito.mockStatic(DALFactory.class)) {
            mb.when(DALFactory::GetFileAccess)
                    .thenReturn(fileAccess);
            manager.ImportTour("Test");
            verify(fileAccess).loadFile(anyString());
        } catch (SQLException | IOException | ParseException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void ExportTourTest() throws SQLException, IOException {
        TourItem tourItem = new TourItem("","Wien","Linz","");
        FileAccess fileAccess = mock(FileAccess.class);
        when(fileAccess.exportTour(any(),anyList(),anyString()))
                .thenReturn(true);
        TourLogPostgresDAO tourLogPostgresDAO = mock(TourLogPostgresDAO.class);
        when(tourLogPostgresDAO.GetLogsForItem(any()))
                .thenReturn(new ArrayList<>());
        try (MockedStatic<DALFactory> mb = Mockito.mockStatic(DALFactory.class)) {
            mb.when(DALFactory::GetFileAccess)
                .thenReturn(fileAccess);
            mb.when(DALFactory::CreateTourLogDAO)
                    .thenReturn(tourLogPostgresDAO);
            manager.ExportTour(tourItem,"Test");
            verify(tourLogPostgresDAO).GetLogsForItem(any());
            verify(fileAccess).exportTour(any(),anyList(),anyString());
        } catch (SQLException | IOException | ParseException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void requestRouteImageTest() {
        FileAccess fileAccess = mock(FileAccess.class);
        when(fileAccess.loadImage(anyInt()))
                .thenReturn(null);
        try (MockedStatic<DALFactory> mb = Mockito.mockStatic(DALFactory.class)) {
            mb.when(DALFactory::GetFileAccess)
                    .thenReturn(fileAccess);
            manager.requestRouteImage(-1);
            verify(fileAccess).loadImage(anyInt());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
