package pro.sky.recipebookapp.services.fileservices;

public interface IngredientsFileService {
    boolean saveToFile(String json);
    String readFromFile();
}
