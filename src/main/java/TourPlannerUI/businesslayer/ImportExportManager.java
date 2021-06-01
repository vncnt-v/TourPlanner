package TourPlannerUI.businesslayer;

import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class ImportExportManager {

    public static void ExportTour(TourItem tourItem, List<TourLog> tourLogs, String filePath) throws IOException {
        String path = Paths.get(filePath).toString();
        try (FileWriter fileWriter = new FileWriter(path);
            BufferedWriter writer = new BufferedWriter(fileWriter)) {
            writer.write(tourItem.getName());
            writer.newLine();
            writer.write(tourItem.getStart());
            writer.newLine();
            writer.write(tourItem.getEnd());
            writer.newLine();
            writer.write(tourItem.getDescription());
            writer.newLine();
            writer.write(String.valueOf(tourItem.getDistance()));
            writer.newLine();
            for (TourLog tourLog : tourLogs){
                writer.write(tourLog.getDate().toString());
                writer.newLine();
                writer.write(tourLog.getReport());
                writer.newLine();
                writer.write(String.valueOf(tourLog.getDistance()));
                writer.newLine();
                writer.write(tourLog.getTotalTime());
                writer.newLine();
                writer.write(tourLog.getStartTime());
                writer.newLine();
                writer.write(String.valueOf(tourLog.getRating()));
                writer.newLine();
                writer.write(String.valueOf(tourLog.getExhausting()));
                writer.newLine();
                writer.write(String.valueOf(tourLog.getAverageSpeed()));
                writer.newLine();
                writer.write(String.valueOf(tourLog.getCalories()));
                writer.newLine();
                writer.write(tourLog.getBreaks());
                writer.newLine();
                writer.write(tourLog.getWeather());
                writer.newLine();
            }
        }
    }

    public static File ImportTour(String filePath) throws IOException {
        return new File(filePath);
    }
}
