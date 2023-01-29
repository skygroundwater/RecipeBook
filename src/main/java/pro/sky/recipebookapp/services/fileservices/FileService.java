package pro.sky.recipebookapp.services.fileservices;

import java.io.File;

public interface FileService {
    File getDataFile();

    void saveToFile(String json);
    String readFromFile();

    void cleanDataFile();
}