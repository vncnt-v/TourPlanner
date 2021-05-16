package TourPlannerUI.dataaccesslayer.common;

import TourPlannerUI.businesslayer.ConfigurationManager;
import TourPlannerUI.dataaccesslayer.dao.ITourItemDAO;
import TourPlannerUI.dataaccesslayer.dao.ITourLogDAO;
import TourPlannerUI.dataaccesslayer.postgresSqlServer.Database;
import TourPlannerUI.dataaccesslayer.postgresSqlServer.TourItemPostgresDAO;
import TourPlannerUI.dataaccesslayer.postgresSqlServer.TourLogPostgresDAO;

import java.io.FileNotFoundException;
import java.lang.module.Configuration;

public class DALFactory {

    private static IDatabase database;

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
            e.printStackTrace();
        }
        return null;
    }

    public static ITourItemDAO CreateTourItemDAO() {
        try {
            Class<TourItemPostgresDAO> cls = (Class<TourItemPostgresDAO>) Class.forName(TourItemPostgresDAO.class.getName());
            return cls.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ITourLogDAO CreateTourLogDAO() {
        try {
            Class<TourLogPostgresDAO> cls = (Class<TourLogPostgresDAO>) Class.forName(TourLogPostgresDAO.class.getName());
            return cls.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
