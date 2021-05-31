package TourPlannerUI.dataaccesslayer.fileServer;

import TourPlannerUI.dataaccesslayer.common.DALFactory;
import TourPlannerUI.dataaccesslayer.common.IFileAccess;
import TourPlannerUI.dataaccesslayer.dao.ITourItemDAO;
import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourTypes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TourItemFileDAO implements ITourItemDAO {

    private IFileAccess fileAccess;

    public TourItemFileDAO() {
        this.fileAccess = DALFactory.GetFileAccess();
    }

    @Override
    public TourItem FindById(Integer itemId) throws SQLException, IOException {
        List<File> foundFiles = fileAccess.SearchFiles(itemId.toString(), TourTypes.TourItem);
        return QueryFromFileSystem(foundFiles).stream().findFirst().get();
    }

    @Override
    public TourItem AddNewItem(String name, String start, String end, String description, float distance) throws SQLException {
        return null;
    }

    @Override
    public boolean UpdateItem(TourItem tourItem) throws SQLException {
        return false;
    }

    @Override
    public List<TourItem> GetItems() throws SQLException {
        return null;
    }

    @Override
    public boolean DeleteItem(Integer id) throws SQLException {
        return false;
    }

    private List<TourItem> QueryFromFileSystem(List<File> foundFiles) throws IOException {
        List<TourItem> foundTourItems = new ArrayList<>();

        for (File file : foundFiles) {
            List<String> fileLines = Files.readAllLines(Path.of(file.getAbsolutePath()));
            foundTourItems.add(new TourItem(
                    Integer.parseInt(fileLines.get(0)),
                    fileLines.get(1),
                    fileLines.get(2),
                    fileLines.get(3),
                    fileLines.get(4),
                    Float.parseFloat(fileLines.get(5))
            ));
        }

        return foundTourItems;
    }
}
