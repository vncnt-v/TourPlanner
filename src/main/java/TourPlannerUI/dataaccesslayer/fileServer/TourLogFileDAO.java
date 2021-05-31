package TourPlannerUI.dataaccesslayer.fileServer;

import TourPlannerUI.dataaccesslayer.common.DALFactory;
import TourPlannerUI.dataaccesslayer.common.IFileAccess;
import TourPlannerUI.dataaccesslayer.dao.ITourItemDAO;
import TourPlannerUI.dataaccesslayer.dao.ITourLogDAO;
import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;
import TourPlannerUI.model.TourTypes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class TourLogFileDAO implements ITourLogDAO {

    private IFileAccess fileAccess;

    public TourLogFileDAO() {
        this.fileAccess = DALFactory.GetFileAccess();
    }

    @Override
    public TourLog FindById(Integer logId) throws IOException, ParseException, SQLException {
        List<File> foundFiles = fileAccess.SearchFiles(logId.toString(), TourTypes.TourLog);
        return QueryFromFileSystem(foundFiles).stream().findFirst().get();
    }

    @Override
    public TourLog AddNewItemLog(LocalDate date, String report, float distance, String startTime, String totalTime, int rating, int exhausting, float averageSpeed, float calories, String breaks, String weather, TourItem logItem) {

    }

    @Override
    public boolean UpdateLog(TourLog tourLog) {
        return false;
    }

    @Override
    public List<TourLog> GetLogsForItem(TourItem tourItem) {
        return null;
    }

    @Override
    public boolean DeleteLog(Integer id) {
        return false;
    }

    private List<TourLog> QueryFromFileSystem(List<File> foundFiles) throws IOException, ParseException, SQLException {
        List<TourLog> foundTourItems = new ArrayList<>();
        ITourItemDAO tourItemDAO = DALFactory.CreateTourItemDAO();
        for (File file : foundFiles) {
            List<String> fileLines = Files.readAllLines(Path.of(file.getAbsolutePath()));
            foundTourItems.add(new TourLog(
                Integer.parseInt(fileLines.get(0)),
                new SimpleDateFormat("yyyy-MM-dd").parse(fileLines.get(1)).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                fileLines.get(1),
                Float.parseFloat(fileLines.get(3)),
                fileLines.get(4),
                fileLines.get(5),
                Integer.parseInt(fileLines.get(6)),
                Integer.parseInt(fileLines.get(7)),
                Float.parseFloat(fileLines.get(8)),
                Float.parseFloat(fileLines.get(9)),
                fileLines.get(10),
                fileLines.get(11),
                tourItemDAO.FindById(Integer.parseInt(fileLines.get(12)))
            ));
        }

        return foundTourItems;
    }
}
