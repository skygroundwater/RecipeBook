package pro.sky.recipebookapp.services.fileservices.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.sky.recipebookapp.services.fileservices.RecipesFileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class RecipesFileServiceImpl implements RecipesFileService {
    @Value("${path.to.data.file}")
    private String dataFilePath;
    @Value("${name.of.recipes.data.file}")
    private String dataFileName;

    @Override
    public boolean saveToFile(String json){
        try {
            cleanDataFile();
            Files.writeString(Path.of(dataFilePath, dataFileName), json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public  String readFromFile(){
        try {
            return Files.readString(Path.of(dataFilePath, dataFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean cleanDataFile() {
        try {
            Path path = Path.of(dataFilePath, dataFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}