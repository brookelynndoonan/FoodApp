package com.kenzie.appserver.service;

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
import java.util.stream.StreamSupport;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAllRecipes() {
        Iterable<RecipeRecord> recipeRecords = recipeRepository.findAll();
        return StreamSupport.stream(recipeRecords.spliterator(), false)
                .map(RecipeMapper::toRecipe)
                .collect(Collectors.toList());
    }

    public Recipe createRecipe(Recipe recipe) {
        String id = UUID.randomUUID().toString();
        RecipeRecord recipeRecord = RecipeMapper.toRecipeRecord(recipe);
        recipeRecord.setId(id);
        RecipeRecord savedRecipeRecord = recipeRepository.save(recipeRecord);
        return RecipeMapper.toRecipe(savedRecipeRecord);
    }

    public List<Recipe> getRecipesByCuisine(String cuisine) {
        Enums.Cuisine cuisineEnum = Enums.Cuisine.valueOf(cuisine);
        List<RecipeRecord> recipeRecords = recipeRepository.findByCuisine(cuisineEnum);
        return recipeRecords.stream()
                .map(RecipeMapper::toRecipe)
                .collect(Collectors.toList());
    }

    public List<Recipe> getRecipesByDietaryRestrictions(String dietaryRestrictions) {
        Enums.DietaryRestrictions dietaryRestrictionsEnum = Enums.DietaryRestrictions.valueOf(dietaryRestrictions);
        List<RecipeRecord> recipeRecords = recipeRepository.findByDietaryRestrictions(dietaryRestrictionsEnum);
        return recipeRecords.stream()
                .map(RecipeMapper::toRecipe)
                .collect(Collectors.toList());
    }

    public Optional<Recipe> getRecipeById(String id) {
        Optional<RecipeRecord> recipeRecordOptional = recipeRepository.findById(id);
        return recipeRecordOptional.map(RecipeMapper::toRecipe);
    }
}
