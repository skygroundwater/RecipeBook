package pro.sky.recipebookapp.services.impl;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.springframework.stereotype.Service;
import pro.sky.recipebookapp.models.Recipe;
import pro.sky.recipebookapp.services.RecipeBookService;


@Service
public class RecipeBookServiceImpl implements RecipeBookService {

    public static long id = 1;
    private final Map<Long, Recipe> listRecipes = new TreeMap<>();

    @Override
    public Recipe getRecipe(long id) {
        for (Entry<Long, Recipe> entry : listRecipes.entrySet()) {
            if (entry != null && entry.getKey() == id) {
                Recipe recipe = listRecipes.get(id);
                if (recipe != null) {
                    return recipe;
                }
            }
        }
        return null;
    }

    @Override
    public long addRecipe(Recipe recipe) {
        listRecipes.getOrDefault(id, null);
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
        for (long i = 0; i < listRecipes.size(); ) {
            listRecipes.get(++i);
        }
        return listRecipes;
    }
}