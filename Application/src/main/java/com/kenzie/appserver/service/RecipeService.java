package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.repositories.model.Enums;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAllRecipes() {
        Iterable<RecipeRecord> recipeRecords = recipeRepository.findAll();
        return StreamSupport.stream(recipeRecords.spliterator(), false)
                .map(this::convertToRecipe)
                .collect(Collectors.toList());
    }

    public Recipe createRecipe(Recipe recipe) {
        RecipeRecord recipeRecord = convertToRecipeRecord(recipe);
        RecipeRecord savedRecord = recipeRepository.save(recipeRecord);
        return convertToRecipe(savedRecord);
    }

    public List<Recipe> getRecipesByCuisine(Enums.Cuisine cuisine) {
        List<RecipeRecord> recipeRecords = recipeRepository.findByCuisine(cuisine);
        return recipeRecords.stream()
                .map(this::convertToRecipe)
                .collect(Collectors.toList());
    }

    public List<Recipe> getRecipesByDietaryRestrictions(Enums.DietaryRestrictions dietaryRestrictions) {
        List<RecipeRecord> recipeRecords = recipeRepository.findByDietaryRestrictions(dietaryRestrictions);
        return recipeRecords.stream()
                .map(this::convertToRecipe)
                .collect(Collectors.toList());
    }

    public Recipe getRecipeById(String id) {
        return recipeRepository.findById(id)
                .map(this::convertToRecipe)
                .orElse(null);
    }

    private Recipe convertToRecipe(RecipeRecord recipeRecord) {
        return new Recipe(
                recipeRecord.getTitle(),
                recipeRecord.getId(),
                recipeRecord.getCuisine().toString(),
                recipeRecord.getDescription(),
                recipeRecord.getDietaryRestrictions().toString(),
                recipeRecord.hasDietaryRestrictions(),
                recipeRecord.getIngredients(),
                recipeRecord.getInstructions()
        );
    }

    private RecipeRecord convertToRecipeRecord(Recipe recipe) {
        RecipeRecord recipeRecord = new RecipeRecord();
        recipeRecord.setTitle(recipe.getTitle());
        recipeRecord.setId(UUID.randomUUID().toString());
        recipeRecord.setCuisine(Enums.Cuisine.valueOf(recipe.getCuisine()));
        recipeRecord.setDescription(recipe.getDescription());
        recipeRecord.setDietaryRestrictions(Enums.DietaryRestrictions.valueOf(recipe.getDietaryRestrictions()));
        recipeRecord.setHasDietaryRestrictions(recipe.hasDietaryRestrictions());
        recipeRecord.setIngredients(recipe.getIngredients());
        recipeRecord.setInstructions(recipe.getInstructions());
        return recipeRecord;
    }
}
