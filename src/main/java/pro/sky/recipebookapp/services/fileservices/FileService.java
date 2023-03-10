package pro.sky.recipebookapp.services.fileservices;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;

public interface FileService {
    File getDataFile();

    void saveToFile(String json);


    String readFromFile();

    void cleanDataFile();

    Path createTempFile(String suffix);
}