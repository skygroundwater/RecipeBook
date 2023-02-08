package pro.sky.recipebookapp.services.fileservices;

public interface RecipesFileService {
    boolean saveToFile(String json);

    String readFromFile();
}
