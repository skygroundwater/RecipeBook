package pro.sky.recipebookapplication.controllers;

import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.recipebookapplication.models.Recipe;
import pro.sky.recipebookapplication.services.RecipeBookService;


@RestController
@RequestMapping("/recipe")
@Tag(name="Рецепт", description = "CRUD-операции добавления рецептов")
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
    @Operation(description = "Поиск рецепта по id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Рецепт был найден",
    content = {@Content(
            mediaType = "application/json", array = @ArraySchema(
                    schema = @Schema(
                            implementation = Recipe.class)))})})
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