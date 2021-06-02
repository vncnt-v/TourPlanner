package TourPlannerUI.dataaccesslayer.fileServer;

import TourPlannerUI.dataaccesslayer.common.IFileAccess;
import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileAccess implements IFileAccess {

    private final String defaultStorage;

    public FileAccess(String defaultStorage){
        this.defaultStorage = defaultStorage;
    }

    @Override
    public boolean exportTour(TourItem tourItem, List<TourLog> tourLogs, String filePath) {
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
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public File loadFile(String filePath) {
        return new File(filePath);
    }

    @Override
    public boolean saveImage(BufferedImage img, int id) {
        try {
            File directory = new File(defaultStorage);
            if (! directory.exists()){
                if (!directory.mkdir()){
                    return false;
                }
            }
            ImageIO.write(img, "jpg", new File(defaultStorage + id + ".jpg"));
        } catch (IOException e) {
            System.out.println("Exception occured :" + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public BufferedImage loadImage(int id) {
        BufferedImage bImage;
        try {
            File initialImage = new File(defaultStorage + id + ".jpg");
            bImage = ImageIO.read(initialImage);
        } catch (IOException e) {
            System.out.println("Exception occured :" + e.getMessage());
            return null;
        }
        return bImage;
    }

    @Override
    public boolean deleteImage(int id) {
        try {
            File file = new File(defaultStorage + id + ".jpg");
            return Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            System.out.println("Exception occured :" + e.getMessage());
        }
        return false;
    }
}
