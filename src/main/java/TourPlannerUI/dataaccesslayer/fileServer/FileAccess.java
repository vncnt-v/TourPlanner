package TourPlannerUI.dataaccesslayer.fileServer;

import TourPlannerUI.dataaccesslayer.common.IFileAccess;
import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
            Logger log = LogManager.getLogger(FileAccess.class);
            log.error("Cant write File: " + e.getMessage());
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
                    Logger log = LogManager.getLogger(FileAccess.class);
                    log.error("Cant create dir: " + directory);
                    return false;
                }
            }
            ImageIO.write(img, "jpg", new File(defaultStorage + id + ".jpg"));
        } catch (IOException e) {
            Logger log = LogManager.getLogger(FileAccess.class);
            log.error("Exception occured :" + e.getMessage());
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
            Logger log = LogManager.getLogger(FileAccess.class);
            log.error("Exception occured :" + e.getMessage());
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
            Logger log = LogManager.getLogger(FileAccess.class);
            log.error("Exception occured :" + e.getMessage());
        }
        return false;
    }

    @Override
    public String getImagePath(int id) {
        String path = defaultStorage + id + ".jpg";
        return path;
    }

    @Override
    public boolean GenerateReport(TourItem item, List<TourLog> logs, String path) {
        String tourInfo = getTourInfo(item);
        StringBuilder logContent = new StringBuilder();

        for (int i = 0; i < logs.size(); i++){
            logContent.append("\nLog ").append(i + 1).append(":\n");
            logContent.append("Date: ").append(logs.get(i).getDate()).append("\n");
            logContent.append("Start Time: ").append(logs.get(i).getStartTime()).append("\n");
            logContent.append("Report: ").append(logs.get(i).getReport()).append("\n");
            logContent.append("Distance: ").append(logs.get(i).getDistance()).append("\n");
            logContent.append("Total Time: ").append(logs.get(i).getTotalTime()).append("\n");
            logContent.append("Rating: ").append(logs.get(i).getRating()).append("\n");
            logContent.append("Exhausting: ").append(logs.get(i).getExhausting()).append("\n");
            logContent.append("Average Speed: ").append(logs.get(i).getAverageSpeed()).append("\n");
            logContent.append("Calories: ").append(logs.get(i).getCalories()).append("\n");
            logContent.append("Stops: ").append(logs.get(i).getBreaks()).append("\n");
            logContent.append("Weather: ").append(logs.get(i).getWeather()).append("\n");
        }
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            document.add(getTourImage(getImagePath(item.getId())));
            document.add(new Paragraph(tourInfo));
            document.add(new Paragraph("Logs:"));
            document.add(new Paragraph(logContent.toString()));
            document.close();
            return true;
        } catch (Exception e) {
            Logger log = LogManager.getLogger(FileAccess.class);
            log.error("Cant create PDF: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean GenerateSummarizeReport(TourItem item, List<TourLog> logs, String path) {
        String tourInfo = getTourInfo(item);
        float distance = 0;
        int totalHours = 0;
        int totalMinutes = 0;
        int rating = 0;
        int exhausting = 0;
        float averageSpeed = 0;
        float calories = 0;

        for (TourLog log : logs) {
            distance += log.getDistance();
            String string = log.getTotalTime();
            String[] parts = string.split(":");
            if (parts.length == 2) {
                totalHours += Integer.parseInt(parts[0]);
                totalMinutes += Integer.parseInt(parts[1]);
            }
            rating += log.getRating();
            exhausting += log.getExhausting();
            averageSpeed += log.getAverageSpeed();
            calories += log.getCalories();
        }
        if(logs.size() > 0){
            int tmp = totalMinutes+(totalHours*60);
            tmp /= logs.size();
            totalHours = tmp/60;
            totalMinutes = tmp%60;
        }
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            document.add(getTourImage(getImagePath(item.id)));
            document.add(new Paragraph(tourInfo));
            if(logs.size() > 0) {
                document.add(new Paragraph("(" + logs.size() + ") Logs:"));
                document.add(new Paragraph("Average Distance: " + distance / (float)logs.size()));
                document.add(new Paragraph("Average Total Time: " + totalHours + ":" + totalMinutes));
                document.add(new Paragraph("Average Rating: " + rating / (float)logs.size()));
                document.add(new Paragraph("Average Exhausting: " + exhausting / (float)logs.size()));
                document.add(new Paragraph("Average Speed: " + averageSpeed / (float)logs.size()));
                document.add(new Paragraph("Average Calories: " + calories / (float)logs.size()));
            } else {
                document.add(new Paragraph("No Logs!"));
            }
            document.close();
            return true;
        } catch (Exception e) {
            Logger log = LogManager.getLogger(FileAccess.class);
            log.error("Cant create PDF: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private String getTourInfo(TourItem item) {
        String tourInfo = "Tour Info:\n";
        tourInfo += "Name: " + item.getName() + "\n";
        tourInfo += "Start: " + item.getStart() + "\n";
        tourInfo += "End: " + item.getEnd() + "\n";
        tourInfo += "Distance: " + item.getDistance() + "\n";
        tourInfo += "Description: " + item.getDescription() + "\n\n";
        return  tourInfo;
    }

    private Image getTourImage(String imagePath) throws IOException, BadElementException {
        Image image = Image.getInstance(imagePath);
        image.scaleToFit(400f,400f);
        return image;
    }
}
