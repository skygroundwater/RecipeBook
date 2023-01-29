package pro.sky.recipebookapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import pro.sky.recipebookapp.models.Recipe;
import pro.sky.recipebookapp.services.RecipeBookService;
import pro.sky.recipebookapp.services.fileservices.FileService;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.TreeMap;


@Service
public class RecipeBookServiceImpl implements RecipeBookService {

    private final FileService fileService;
    public static long id = 1;
    private Map<Long, Recipe> listRecipes = new TreeMap<>();

    public RecipeBookServiceImpl(@Qualifier("recipesFileServiceImpl") FileService fileService) {
        this.fileService = fileService;
    }

    @PostConstruct
    private void init(){
        readFromFile();
    }

    @Override
    public Recipe getRecipe(long id) {
        return listRecipes.get(id);
    }

    @Override
    public long addRecipe(Recipe recipe) {
        listRecipes.put(id, recipe);
        saveToFile();
        return id++;
    }

    @Override
    public Recipe editRecipe(long id, Recipe recipe) {
        if (listRecipes.containsKey(id)) {
            listRecipes.put(id, recipe);
            return recipe;
        }
        return null;
    }

    @Override
    public boolean deleteRecipe(long id) {
        if (listRecipes.containsKey(id)) {
            listRecipes.remove(id);
            return true;
        }
        return false;
    }

    private void saveToFile(){
        try {
            String json = new ObjectMapper().writeValueAsString(listRecipes);
            fileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile(){

        String json = fileService.readFromFile();
        try {
            listRecipes = new ObjectMapper().readValue(json, new TypeReference<TreeMap<Long, Recipe>>(){});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Long, Recipe> getAllRecipes() {
        return listRecipes;
    }
}