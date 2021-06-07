package TourPlannerUI.dataaccesslayer.common;

import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public interface IFileAccess {

    boolean exportTour(TourItem tourItem, List<TourLog> tourLogs, String path);
    File loadFile(String path);
    boolean saveImage(BufferedImage image, int id);
    String getImagePath(int id);
    BufferedImage loadImage(int id);
    boolean deleteImage(int id);
    boolean GenerateReport(TourItem item, List<TourLog> logs, String path);
    boolean GenerateSummarizeReport(TourItem item, List<TourLog> logs, String path);
}
