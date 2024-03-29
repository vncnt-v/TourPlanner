package TourPlannerUI.dataaccesslayer.common;

import TourPlannerUI.businesslayer.ConfigurationManager;
import TourPlannerUI.dataaccesslayer.dao.ITourItemDAO;
import TourPlannerUI.dataaccesslayer.dao.ITourLogDAO;
import TourPlannerUI.dataaccesslayer.fileServer.FileAccess;
import TourPlannerUI.dataaccesslayer.postgresSqlServer.Database;
import TourPlannerUI.dataaccesslayer.postgresSqlServer.TourItemPostgresDAO;
import TourPlannerUI.dataaccesslayer.postgresSqlServer.TourLogPostgresDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;

public class DALFactory {

    private static IDatabase database;
    private static IFileAccess fileAccess;

    /** FileAccess **/
    public static IFileAccess GetFileAccess() throws FileNotFoundException {
        if (fileAccess == null) {
            String storagePath = ConfigurationManager.GetConfigProperty("FileAccessStoragePath");
            fileAccess = new FileAccess(storagePath);
        }
        return fileAccess;
    }

    /** DATABASE **/
    public static IDatabase GetDatabase() throws FileNotFoundException {
        if (database == null) {
            database = CreateDatabase();
        }
        return database;
    }

    private static IDatabase CreateDatabase() throws FileNotFoundException {
        String connectionString = ConfigurationManager.GetConfigProperty("PostgresSqlConnectionString");
        return CreateDatabase(connectionString);
    }

    private static IDatabase CreateDatabase(String connectionString) {
        try {
            Class<Database> cls = (Class<Database>) Class.forName(Database.class.getName());
            return cls.getConstructor(String.class).newInstance(connectionString);
        } catch (Exception e) {
            Logger log = LogManager.getLogger(DALFactory.class);
            log.error("Cant create Database: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static ITourItemDAO CreateTourItemDAO() {
        try {
            Class<TourItemPostgresDAO> cls = (Class<TourItemPostgresDAO>) Class.forName(TourItemPostgresDAO.class.getName());
            return cls.getConstructor().newInstance();
        } catch (Exception e) {
            Logger log = LogManager.getLogger(DALFactory.class);
            log.error("Cant create TourItemDAO: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static ITourLogDAO CreateTourLogDAO() {
        try {
            Class<TourLogPostgresDAO> cls = (Class<TourLogPostgresDAO>) Class.forName(TourLogPostgresDAO.class.getName());
            return cls.getConstructor().newInstance();
        } catch (Exception e) {
            Logger log = LogManager.getLogger(DALFactory.class);
            log.error("Cant create TourLogDAO: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
