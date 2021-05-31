package TourPlannerUI.dataaccesslayer.common;

import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;
import TourPlannerUI.model.TourTypes;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IFileAccess {
    int CreateTourItemFile(String name, String start, String end, String description, float distance) throws IOException;
    int CreateTourLogFile(TourLog tourLog, TourItem tourItem) throws IOException;
    int UpdateTourItemFile(String id, TourItem tourItem) throws IOException;
    int UpdateTourLogFile(String id, TourLog tourLog) throws IOException;
    int DeleteTourItemFile(String id) throws IOException;
    int DeleteTourLogFile(String id) throws IOException;
    List<File> SearchFiles(String searchTerm, TourTypes tourType) throws IOException;
    List<File> GetAllFiles(TourTypes tourType);
}
