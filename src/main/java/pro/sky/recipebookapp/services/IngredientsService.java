package pro.sky.recipebookapp.services;

import java.util.Map;
import pro.sky.recipebookapp.models.ingredients.Ingredient;


public interface IngredientsService {

    Ingredient getIngredients(long id);

    Ingredient editIngredients(long id, Ingredient ingredients);

    long addIngredients(Ingredient ingredients);

    boolean deleteIngredient(long id);

    Map<Long, Ingredient> getAllIngredients();
}