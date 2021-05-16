package TourPlannerUI.businesslayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {
    public static String GetConfigProperty(String propertyName) throws FileNotFoundException {
        Properties prop = new Properties();
        String propFileName = "config.properties";

        InputStream stream = new FileInputStream(propFileName);

        try {
            prop.load(stream);
            return prop.getProperty(propertyName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new FileNotFoundException(propFileName + " was not found.");
    }
}
