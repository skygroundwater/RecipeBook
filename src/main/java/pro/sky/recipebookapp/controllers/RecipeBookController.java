package pro.sky.recipebookapp.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.recipebookapp.models.Recipe;
import pro.sky.recipebookapp.services.RecipeBookService;

import java.util.Map;


@RestController
@RequestMapping("/recipe")
public class RecipeBookController {

    private final RecipeBookService recipeService;

    public RecipeBookController(RecipeBookService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping()
    public ResponseEntity<Long> addRecipe(@RequestBody Recipe recipe) {
        long id = recipeService.addRecipe(recipe);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable long id) {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe != null) {
            return ResponseEntity.ok(recipe);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> editRecipe(@PathVariable long id,
                                             @RequestBody Recipe recipe) {
        Recipe recipes = recipeService.editRecipe(id, recipe);
        if (recipes != null) {
            return ResponseEntity.ok(recipes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable long id) {
        if (recipeService.deleteRecipe(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping()
    public ResponseEntity<Map<Long, Recipe>> getAllRecipes() {
        Map<Long, Recipe> recipeList = recipeService.getAllRecipes();
        if (recipeList != null) {
            return ResponseEntity.ok(recipeList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}