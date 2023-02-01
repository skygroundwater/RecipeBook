package pro.sky.recipebookapp.services;

import pro.sky.recipebookapp.models.Recipe;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;


public interface RecipeBookService {

    Recipe getRecipe(long id);

    long addRecipe(Recipe recipe);

    Recipe editRecipe(long id, Recipe recipe);

    boolean deleteRecipe(long id);

    Path getTextFile(String recipeName) throws IOException;

    TreeMap<Long, Recipe> getAllRecipes();
}