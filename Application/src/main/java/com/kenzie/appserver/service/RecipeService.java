package com.kenzie.appserver.service;
//for merge
import com.kenzie.appserver.converters.RecipeMapper;
import com.kenzie.appserver.exceptions.RecipeNotFoundException;
import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class RecipeService {


    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAllRecipes() {
        List<RecipeRecord> recipeRecords = recipeRepository.findAll();
        return recipeRecords.stream()
                .map(RecipeMapper::recipeRecordtoRecipe)
                .collect(Collectors.toList());
    }

    public Recipe addNewRecipe(Recipe recipe) {
        RecipeRecord recipeRecord = new RecipeRecord();
        recipeRecord.setId(recipe.getId());
        recipeRecord.setTitle(recipe.getTitle());
        recipeRecord.setCuisine(recipe.getCuisine());
        recipeRecord.setDescription(recipe.getDescription());
        recipeRecord.setDietaryRestrictions(recipe.getDietaryRestrictions());
        recipeRecord.setHasDietaryRestrictions(recipe.getHasDietaryRestrictions());
        recipeRecord.setIngredients(recipe.getIngredients());
        recipeRecord.setInstructions(recipe.getInstructions());
        recipeRepository.save(recipeRecord);
        return recipe;
    }


    public List<Recipe> getRecipesByCuisine(String cuisine) {
        List<RecipeRecord> recipeRecords = recipeRepository.findByCuisine(cuisine);
        List<Recipe> recipes = recipeRecords.stream()
                .map(RecipeMapper::recipeRecordtoRecipe)
                .collect(Collectors.toList());
        return recipes;
    }

    public List<Recipe> getRecipesByDietaryRestrictions(String dietaryRestrictions) {
        List<RecipeRecord> recipeRecords = recipeRepository.findByDietaryRestrictions(dietaryRestrictions);
        return recipeRecords.stream()
                .map(RecipeMapper::recipeRecordtoRecipe)
                .collect(Collectors.toList());
    }

    public RecipeRecord getRecipeById(String id) throws RecipeNotFoundException {
        Optional<RecipeRecord> optionalRecipeRecord = recipeRepository.findById(id);

        if (optionalRecipeRecord.isPresent()) {
            return optionalRecipeRecord.get();
        } else {
            throw new RecipeNotFoundException("Recipe not found with ID: " + id);
        }
    }
<<<<<<< HEAD

    public List<Recipe> searchRecipes(String cuisine, String dietaryRestrictions, String query) {
        if (cuisine != null && dietaryRestrictions != null  && query == null) {
            // Search by cuisine & dietary restrictions
            List<RecipeRecord> recipeRecords = recipeRepository.findByCuisineAndDietaryRestrictions(cuisine, dietaryRestrictions);
            return recipeRecords.stream()
                    .map(RecipeMapper::recipeRecordtoRecipe)
                    .collect(Collectors.toList());
        } else if (cuisine != null && dietaryRestrictions != null) {
            // Search by cuisine, dietary restrictions, and query
            List<RecipeRecord> recipeRecords = recipeRepository.findByCuisineAndDietaryRestrictionsAndTitleContains(cuisine, dietaryRestrictions, query);
            return recipeRecords.stream()
                    .map(RecipeMapper::recipeRecordtoRecipe)
                    .collect(Collectors.toList());
        } else if (cuisine != null) {
            // Search by cuisine and query
            List<RecipeRecord> recipeRecords = recipeRepository.findByCuisineAndTitleContaining(cuisine, query);
            return recipeRecords.stream()
                    .map(RecipeMapper::recipeRecordtoRecipe)
                    .collect(Collectors.toList());
        } else if (dietaryRestrictions != null) {
            // Search by dietary restrictions and query
            List<RecipeRecord> recipeRecords = recipeRepository.findByDietaryRestrictionsAndTitleContaining(dietaryRestrictions, query);
            return recipeRecords.stream()
                    .map(RecipeMapper::recipeRecordtoRecipe)
                    .collect(Collectors.toList());
        } else {
            // Search by query only
            List<RecipeRecord> recipeRecords = recipeRepository.findByTitleContaining(query);
            return recipeRecords.stream()
                    .map(RecipeMapper::recipeRecordtoRecipe)
                    .collect(Collectors.toList());
        }
    }


=======
>>>>>>> 2a9dfe5 (resolving merge conflict)
    public void deleteRecipeById(String id) {
        recipeRepository.deleteById(id);
    }
}