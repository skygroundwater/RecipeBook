package pro.sky.recipebookapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import pro.sky.recipebookapp.models.ingredients.Ingredient;
import pro.sky.recipebookapp.services.IngredientsService;
import pro.sky.recipebookapp.services.fileservices.IngredientsFileService;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.TreeMap;


@Service
public class IngredientsServiceImpl implements IngredientsService {

    private final IngredientsFileService ingredientsFileService;
    private static long id = 1;
    private Map<Long, Ingredient> listIngredients = new TreeMap<>();

    public IngredientsServiceImpl(IngredientsFileService ingredientsFileService) {
        this.ingredientsFileService = ingredientsFileService;
    }

    @PostConstruct
    private void init(){
        readFromFile();
    }

    @Override
    public Ingredient getIngredients(long id) {
        return listIngredients.get(id);
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
        listIngredients.put(id, ingredients);
        saveToFile();
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
    private void saveToFile(){
        try {
            String json = new ObjectMapper().writeValueAsString(listIngredients);
            ingredientsFileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile(){

        String json = ingredientsFileService.readFromFile();
        try {
            listIngredients = new ObjectMapper().readValue(json, new TypeReference<TreeMap<Long, Ingredient>>(){});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Long, Ingredient> getAllIngredients() {
        return listIngredients;
    }
}