package pro.sky.recipebookapplication.models.ingredients;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class Ingredient {

    private String name;
    private Integer count;
    private String measure;

}