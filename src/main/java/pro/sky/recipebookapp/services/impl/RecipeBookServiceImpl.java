package pro.sky.recipebookapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import pro.sky.recipebookapp.models.Recipe;
import pro.sky.recipebookapp.models.ingredients.Ingredient;
import pro.sky.recipebookapp.services.RecipeBookService;
import pro.sky.recipebookapp.services.fileservices.FileService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;


@Service
public class RecipeBookServiceImpl implements RecipeBookService {

    private final FileService fileService;
    public static long id = 1;
    private TreeMap<Long, Recipe> listRecipes = new TreeMap<>();

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
    public Path getTextFile(String recipeName) {
        Path file = fileService.createTempFile(recipeName);
        try(Writer writer = Files.newBufferedWriter(file, StandardOpenOption.APPEND)) {
            for (Map.Entry<Long, Recipe> recipe : getAllRecipes().entrySet()) {
                writer.append(recipe.getValue().getTitle())
                        .append("\n")
                        .append("Время приготовления: ")
                        .append(String.valueOf(recipe.getValue().getTime()))
                        .append("\n")
                        .append("Ингредиенты: \n");
                for(Ingredient ingredient: recipe.getValue().getIngredientsList()){
                    writer.append(ingredient.toString())
                            .append("\n");
                }
                writer.append("инструкция приготовления: \n");
                int counterForSteps = 1;
                for(String step: recipe.getValue().getInstruction()){
                    writer.append(String.valueOf(counterForSteps)).append(". ").append(step)
                            .append("\n");
                    counterForSteps++;
                }
            }
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TreeMap<Long, Recipe> getAllRecipes() {
        return listRecipes;
    }
}