package TourPlannerUI.businesslayer;

import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.List;

public class PdfGenerator {
    public static boolean GenerateReport(TourItem item, List<TourLog> logs) {
        String tourInfo = "";
        String logContent = "";
        tourInfo += "Name: " + item.getName() + "\n";
        tourInfo += "Description: " + item.getDescription() + "\n";
        tourInfo += "Distance: " + item.getDistance() + "\n\n";
        for (int i = 0; i < logs.size(); i++){
            logContent += "Log " + (i+1) + ":\n";
            logContent += "Date Time: " + logs.get(i).getDate() + ":\n";
            logContent += "Report: " + logs.get(i).getReport() + ":\n";
            logContent += "Distance: " + logs.get(i).getDistance() + ":\n";
            logContent += "Total Time: " + logs.get(i).getTotalTime() + ":\n";
            logContent += "Rating: " + logs.get(i).getRating() + ":\n";
            logContent += "Exhausting: " + logs.get(i).getExhausting() + ":\n";
            logContent += "Average Speed: " + logs.get(i).getAverageSpeed() + ":\n";
            logContent += "Calories: " + logs.get(i).getCalories() + ":\n";
            logContent += "Breaks: " + logs.get(i).getBreaks() + ":\n";
            logContent += "Weather: " + logs.get(i).getWeather() + ":\n";
        }
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("Report_" + item.getName() + ".pdf"));
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

    public static boolean GenerateSummarizeReport(TourItem item, List<TourLog> logs) {
        String tourInfo = "";
        float distance = 0;
        //float totalTime = 0;
        int rating = 0;
        int exhausting = 0;
        float averageSpeed = 0;
        float calories = 0;
        int breaks = 0;

        tourInfo += "Name: " + item.getName() + "\n";
        tourInfo += "Description: " + item.getDescription() + "\n";
        tourInfo += "Distance: " + item.getDistance() + "\n\n";
        for (int i = 0; i < logs.size(); i++){
            distance += logs.get(i).getDistance();
            //totalTime += logs.get(i).getTotalTime();
            rating += logs.get(i).getRating();
            exhausting += logs.get(i).getExhausting();
            averageSpeed += logs.get(i).getAverageSpeed();
            calories += logs.get(i).getCalories();
            //breaks += logs.get(i).getBreaks();
        }
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("Report_summarize_" + item.getName() + ".pdf"));
            document.open();
            document.add(new Paragraph("Tour Info:"));
            document.add(new Paragraph(tourInfo));
            document.add(new Paragraph("Logs:"));
            document.add(new Paragraph("Average Distance: " + distance/logs.size()));
            //document.add(new Paragraph("Average Total Time: " + totalTime/logs.size()));
            document.add(new Paragraph("Average Rating: " + rating/logs.size()));
            document.add(new Paragraph("Average Exhausting: " + exhausting/logs.size()));
            document.add(new Paragraph("Average Speed: " + averageSpeed/logs.size()));
            document.add(new Paragraph("Average Calories: " + calories/logs.size()));
            document.add(new Paragraph("Average Breaks: " + breaks/logs.size()));
            document.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}