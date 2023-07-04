package com.kenzie.appserver.service;

import com.kenzie.appserver.exceptions.RecipeNotFoundException;
import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.repositories.model.Enums;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.model.Recipe;
import com.kenzie.appserver.service.model.RecipeMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
                .map(RecipeMapper::recipeToRecipeRecord)
                .collect(Collectors.toList());
    }

    public Recipe addNewRecipe(Recipe recipe) {
        recipe.setId(UUID.randomUUID().toString());
        RecipeRecord recipeRecord = RecipeMapper.recipeToRecipeRecord(recipe);
        recipeRepository.save(recipeRecord);
        return recipe;
    }

    public List<Recipe> getRecipesByCuisine(String cuisine) {
        Enums.Cuisine cuisineEnum = Enums.Cuisine.valueOf(cuisine);
        List<RecipeRecord> recipeRecords = recipeRepository.findByCuisine(cuisineEnum);
        return recipeRecords.stream()
                .map(RecipeMapper::recipeToRecipeRecord)
                .collect(Collectors.toList());
    }

    public List<Recipe> getRecipesByDietaryRestrictions(String dietaryRestrictions) {
        Enums.DietaryRestrictions dietaryRestrictionsEnum = Enums.DietaryRestrictions.valueOf(dietaryRestrictions);
        List<RecipeRecord> recipeRecords = recipeRepository.findByDietaryRestrictions(dietaryRestrictionsEnum);
        return recipeRecords.stream()
                .map(RecipeMapper::recipeToRecipeRecord)
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
}
