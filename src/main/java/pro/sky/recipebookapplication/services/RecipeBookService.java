package pro.sky.recipebookapplication.services;

import pro.sky.recipebookapplication.models.Recipe;
import java.util.Map;


public interface RecipeBookService {

    Recipe getRecipe(long id);

    long addRecipe(Recipe recipe);

    Recipe editRecipe(long id, Recipe recipe);

    boolean deleteRecipe(long id);

    Map<Long, Recipe> getAllRecipes();
}