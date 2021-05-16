package TourPlannerUI.dataaccesslayer.common;

import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourTypes;

import java.io.File;
import java.util.List;

public interface IFileAccess {
    int CreateTourItemFile(String name, String description);
    int CreateTourLogFile(String logText, TourItem tourItem);
    List<File> SearchFiles(String searchTerm, TourTypes tourType);
    List<File> GetAllFiles(TourTypes tourType);
}
