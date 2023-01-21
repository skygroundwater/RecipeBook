package pro.sky.recipebookapplication.services.impl;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.springframework.stereotype.Service;
import pro.sky.recipebookapplication.models.Recipe;
import pro.sky.recipebookapplication.services.RecipeBookService;


@Service
public class RecipeBookServiceImpl implements RecipeBookService {

    private static long id = 1;
    private final Map<Long, Recipe> listRecipes = new TreeMap<>();

    @Override
    public Recipe getRecipe(long id) {
        return listRecipes.get(id);
    }

    @Override
    public long addRecipe(Recipe recipe) {
        listRecipes.put(id, recipe);
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

    @Override
    public Map<Long, Recipe> getAllRecipes() {
        return listRecipes;
    }
}