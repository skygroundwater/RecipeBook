package pro.sky.recipebookapplication.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import pro.sky.recipebookapplication.models.ingredients.Ingredient;

@Data
@AllArgsConstructor
public class Recipe {

    private String title;
    private int time;
    private List<Ingredient> ingredientsList;
    private List<String> instruction;

}