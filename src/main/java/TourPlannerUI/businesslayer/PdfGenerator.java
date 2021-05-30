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
        String logContent = "";

        for (int i = 0; i < logs.size(); i++){
            logContent += "\nLog " + (i+1) + ":\n";
            logContent += "Date: " + logs.get(i).getDate() + "\n";
            logContent += "Start Time: " + logs.get(i).getStartTime() + "\n";
            logContent += "Report: " + logs.get(i).getReport() + "\n";
            logContent += "Distance: " + logs.get(i).getDistance() + "\n";
            logContent += "Total Time: " + logs.get(i).getTotalTime() + "\n";
            logContent += "Rating: " + logs.get(i).getRating() + "\n";
            logContent += "Exhausting: " + logs.get(i).getExhausting() + "\n";
            logContent += "Average Speed: " + logs.get(i).getAverageSpeed() + "\n";
            logContent += "Calories: " + logs.get(i).getCalories() + "\n";
            logContent += "Stops: " + logs.get(i).getBreaks() + "\n";
            logContent += "Weather: " + logs.get(i).getWeather() + "\n";
        }
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            document.add(new Paragraph("Tour Info:"));
            document.add(new Paragraph(tourInfo));
            document.add(new Paragraph("Logs:"));
            document.add(new Paragraph(logContent));
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

        for (int i = 0; i < logs.size(); i++){
            distance += logs.get(i).getDistance();
            String string = logs.get(i).getTotalTime();
            String[] parts = string.split(":");
            if (parts.length == 2){
                totalHours += Integer.parseInt(parts[0]);
                totalMinutes += Integer.parseInt(parts[1]);
            }
            rating += logs.get(i).getRating();
            exhausting += logs.get(i).getExhausting();
            averageSpeed += logs.get(i).getAverageSpeed();
            calories += logs.get(i).getCalories();
        }
        if(logs.size() > 0){
            int tmp = totalMinutes+(totalHours*60);
            tmp /= logs.size();
            totalHours = tmp/60;
            totalMinutes = totalMinutes%60;
        }
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            document.add(new Paragraph("Tour Info:"));
            document.add(new Paragraph(tourInfo));
            if(logs.size() > 0) {
                document.add(new Paragraph("(" + logs.size() + ") Logs:"));
                document.add(new Paragraph("Average Distance: " + distance / logs.size()));
                document.add(new Paragraph("Average Total Time: " + totalHours + ":" + totalMinutes));
                document.add(new Paragraph("Average Rating: " + rating / logs.size()));
                document.add(new Paragraph("Average Exhausting: " + exhausting / logs.size()));
                document.add(new Paragraph("Average Speed: " + averageSpeed / logs.size()));
                document.add(new Paragraph("Average Calories: " + calories / logs.size()));
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