package TourPlannerUI.dataaccesslayer.common;

import TourPlannerUI.businesslayer.ConfigurationManager;
import TourPlannerUI.dataaccesslayer.dao.ITourItemDAO;
import TourPlannerUI.dataaccesslayer.dao.ITourLogDAO;
import TourPlannerUI.dataaccesslayer.fileServer.FileAccess;
import TourPlannerUI.dataaccesslayer.fileServer.TourItemFileDAO;
import TourPlannerUI.dataaccesslayer.fileServer.TourLogFileDAO;
import TourPlannerUI.dataaccesslayer.postgresSqlServer.Database;
import TourPlannerUI.dataaccesslayer.postgresSqlServer.TourItemPostgresDAO;
import TourPlannerUI.dataaccesslayer.postgresSqlServer.TourLogPostgresDAO;

import java.io.FileNotFoundException;

public class DALFactory {

    private static IDatabase database;
    private static  IFileAccess fileAccess;

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
            e.printStackTrace();
        }
        return null;
    }

    /** FILE ACCESS **/
    public static IFileAccess GetFileAccess() throws FileNotFoundException {
        if (fileAccess == null) {
            fileAccess = CreateFileAccess();
        }
        return fileAccess;
    }

    private static IFileAccess CreateFileAccess() throws FileNotFoundException {
        String connectionString = ConfigurationManager.GetConfigProperty("FileAccessConnectionString");
        return CreateFileAccess(connectionString);
    }

    private static IFileAccess CreateFileAccess(String connectionString) {
        try {
            Class<FileAccess> cls = (Class<FileAccess>) Class.forName(FileAccess.class.getName());
            return cls.getConstructor(String.class).newInstance(connectionString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static ITourItemDAO CreateTourItemDAO() {
        try {
            if(UseFileSystem()){
                Class<TourItemFileDAO> cls = (Class<TourItemFileDAO>) Class.forName(TourItemFileDAO.class.getName());
                return cls.getConstructor().newInstance();
            }
            Class<TourItemPostgresDAO> cls = (Class<TourItemPostgresDAO>) Class.forName(TourItemPostgresDAO.class.getName());
            return cls.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ITourLogDAO CreateTourLogDAO() {
        try {
            if(UseFileSystem()){
                Class<TourLogFileDAO> cls = (Class<TourLogFileDAO>) Class.forName(TourLogFileDAO.class.getName());
                return cls.getConstructor().newInstance();
            }
            Class<TourLogPostgresDAO> cls = (Class<TourLogPostgresDAO>) Class.forName(TourLogPostgresDAO.class.getName());
            return cls.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean UseFileSystem() throws FileNotFoundException {
        return Boolean.parseBoolean(ConfigurationManager.GetConfigProperty("UseFileSystem"));
    }
}
