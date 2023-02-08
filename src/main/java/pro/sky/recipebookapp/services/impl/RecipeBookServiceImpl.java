package pro.sky.recipebookapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import pro.sky.recipebookapp.models.Recipe;
import pro.sky.recipebookapp.services.RecipeBookService;
import pro.sky.recipebookapp.services.fileservices.RecipesFileService;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.TreeMap;


@Service
public class RecipeBookServiceImpl implements RecipeBookService {

    private final RecipesFileService recipeFileService;
    public static long id = 1;
    private Map<Long, Recipe> listRecipes = new TreeMap<>();

    public RecipeBookServiceImpl(RecipesFileService recipeFileService) {
        this.recipeFileService = recipeFileService;
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
            recipeFileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile(){

        String json = recipeFileService.readFromFile();
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