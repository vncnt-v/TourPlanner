package TourPlannerUI.dataaccesslayer.fileServer;

import TourPlannerUI.dataaccesslayer.common.IFileAccess;
import TourPlannerUI.model.TourItem;
import TourPlannerUI.model.TourLog;
import TourPlannerUI.model.TourTypes;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class FileAccess implements IFileAccess {

    private String filePath;

    public FileAccess(String filePath) {
        this.filePath = filePath;
    }

    private List<File> GetFileInfo(String startFolder, TourTypes searchType) {
        File dir = new File(startFolder);
        return Arrays.asList(dir.listFiles(new FileExtensionFilter(".txt")));
    }

    @Override
    public int CreateTourItemFile(String name, String start, String end, String description, float distance) throws IOException {
        int id = UUID.randomUUID().hashCode();

        String fileName = id + "_MediaItem.txt";
        String path = Paths.get(filePath,fileName).toString();
        try (FileWriter fileWriter = new FileWriter(path);
            BufferedWriter writer = new BufferedWriter(fileWriter)) {
            writer.write(String.valueOf(id));
            writer.newLine();
            writer.write(name);
            writer.newLine();
            writer.write(start);
            writer.newLine();
            writer.write(end);
            writer.newLine();
            writer.write(description);
            writer.newLine();
            writer.write(String.valueOf(distance));
            writer.newLine();
        }
        return id;
    }

    @Override
    public int CreateTourLogFile(TourLog tourLog, TourItem tourItem) throws IOException {
        int id = UUID.randomUUID().hashCode();

        String fileName = id + "_MediaLog.txt";
        String path = Paths.get(filePath,fileName).toString();
        try (FileWriter fileWriter = new FileWriter(path);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            writer.write(String.valueOf(id));
            writer.newLine();
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
            writer.write(tourLog.getStartTime());
            writer.newLine();
            writer.write(tourLog.getRating());
            writer.newLine();
            writer.write(tourLog.getExhausting());
            writer.newLine();
            writer.write(String.valueOf(tourLog.getAverageSpeed()));
            writer.newLine();
            writer.write(String.valueOf(tourLog.getCalories()));
            writer.newLine();
            writer.write(tourLog.getBreaks());
            writer.newLine();
            writer.write(tourLog.getWeather());
            writer.newLine();
            writer.write(String.valueOf(tourItem.getId()));
            writer.newLine();
        }
        return id;
    }

    @Override
    public int UpdateTourItemFile(String id, TourItem tourItem) throws IOException {
        return 0;
    }

    @Override
    public int UpdateTourLogFile(String id, TourLog tourLog) throws IOException {
        return 0;
    }

    @Override
    public int DeleteTourItemFile(String id) throws IOException {
        return 0;
    }

    @Override
    public int DeleteTourLogFile(String id) throws IOException {
        return 0;
    }

    @Override
    public List<File> SearchFiles(String searchTerm, TourTypes searchType) throws IOException {
        List<File> fileList = GetFileInfo(filePath, searchType);
        List<File> queryMatchingFiles = new ArrayList<>();

        for (File file : fileList) {
            try (Stream<String> streamOfLines = Files.lines(file.toPath())) {
                Optional<String> line = streamOfLines
                        .filter( l -> l.contains(searchTerm))
                        .findFirst();
                if(line.isPresent()) {
                    queryMatchingFiles.add(file);
                }
            }
        }
        return queryMatchingFiles;
    }

    @Override
    public List<File> GetAllFiles(TourTypes tourType) {
        return GetFileInfo(filePath,tourType);
    }

    private static class FileExtensionFilter implements FilenameFilter {

        private String fileExtension;

        public FileExtensionFilter(String fileExtension) {
            this.fileExtension = fileExtension;
        }

        @Override
        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(fileExtension);
        }
    }
}
