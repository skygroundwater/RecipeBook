package pro.sky.recipebookapp.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pro.sky.recipebookapp.models.ingredients.Ingredient;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    private String title;
    private int time;
    private List<Ingredient> ingredientsList;
    private List<String> instruction;

}