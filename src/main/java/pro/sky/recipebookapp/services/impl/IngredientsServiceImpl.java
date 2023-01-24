package pro.sky.recipebookapp.services.impl;

import java.util.Map;
import java.util.TreeMap;
import org.springframework.stereotype.Service;
import pro.sky.recipebookapp.models.ingredients.Ingredient;
import pro.sky.recipebookapp.services.IngredientsService;


@Service
public class IngredientsServiceImpl implements IngredientsService {

    public static long id = 1;
    private final Map<Long, Ingredient> listIngredients = new TreeMap<>();

    @Override
    public Ingredient getIngredients(long id) {
        for (Map.Entry<Long, Ingredient> entry : listIngredients.entrySet()) {
            if (entry != null && entry.getKey() == id) {
                Ingredient ingredients = listIngredients.get(id);
                if (ingredients != null) {
                    return ingredients;
                }
            }
        }
        return null;
    }

    @Override
    public Ingredient editIngredients(long id, Ingredient ingredients) {
        if (listIngredients.containsKey(id)) {
            listIngredients.put(id, ingredients);
            return ingredients;
        }
        return null;
    }

    @Override
    public long addIngredients(Ingredient ingredients) {
        listIngredients.getOrDefault(id, null);
        listIngredients.put(id, ingredients);
        return id++;
    }


    @Override
    public boolean deleteIngredient(long id) {
        if (listIngredients.containsKey(id)) {
            listIngredients.remove(id);
            return true;
        }
        return false;
    }

    public Map<Long, Ingredient> getAllIngredients() {
        for (long i = 0; i < listIngredients.size(); ) {
            listIngredients.get(++i);
        }
        return listIngredients;
    }
}