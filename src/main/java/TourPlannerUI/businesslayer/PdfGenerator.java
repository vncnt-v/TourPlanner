package TourPlannerUI.businesslayer;

import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.List;

public class PdfGenerator {
    public static boolean GenerateReport(TourItem item, List<TourLog> logs, String path) {
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
            document.add(new Paragraph("Tour Info:"));
            document.add(new Paragraph(tourInfo));
            document.add(new Paragraph("Logs:"));
            document.add(new Paragraph(logContent.toString()));
            document.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean GenerateSummarizeReport(TourItem item, List<TourLog> logs, String path) {
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
            document.add(new Paragraph("Tour Info:"));
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
            e.printStackTrace();
        }
        return false;
    }

    public static String getTourInfo(TourItem item) {
        String tourInfo;
        tourInfo = "Name: " + item.getName() + "\n";
        tourInfo += "Start: " + item.getStart() + "\n";
        tourInfo += "End: " + item.getEnd() + "\n";
        tourInfo += "Distance: " + item.getDistance() + "\n";
        tourInfo += "Description: " + item.getDescription() + "\n\n";
        return  tourInfo;
    }
}