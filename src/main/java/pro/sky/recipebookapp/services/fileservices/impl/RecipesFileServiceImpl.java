package pro.sky.recipebookapp.services.fileservices.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.sky.recipebookapp.services.fileservices.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class RecipesFileServiceImpl implements FileService {

    @Value("${path.to.data.file}")
    private String dataFilePath;

    @Value("${name.of.recipes.data.file}")
    private String dataFileName;

    @Override
    public File getDataFile(){
        return new File(dataFilePath + "/" + dataFileName);
    }

    @Override
    public void saveToFile(String json){
        try {
            cleanDataFile();
            Files.writeString(Path.of(dataFilePath, dataFileName), json);
        } catch (IOException e) {
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

    @Override
    public void cleanDataFile() {
        try {
            Path path = Path.of(dataFilePath, dataFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}