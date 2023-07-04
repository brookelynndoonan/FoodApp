package com.kenzie.appserver.service.model;

import com.kenzie.appserver.repositories.model.Enums;
import com.kenzie.appserver.repositories.model.RecipeRecord;

public class RecipeMapper {

    public static Recipe recipeToRecipeRecord(RecipeRecord recipeRecord) {
        return new Recipe(
                recipeRecord.getId(),
                recipeRecord.getTitle(),
                recipeRecord.getCuisine().toString(),
                recipeRecord.getDescription(),
                recipeRecord.getDietaryRestrictions().toString(),
                recipeRecord.getHasDietaryRestrictions(),
                recipeRecord.getIngredients(),
                recipeRecord.getInstructions()
        );
    }

    public static RecipeRecord recipeToRecipeRecord(Recipe recipe) {
        RecipeRecord recipeRecord = new RecipeRecord();
//      recipeRecord.setId(UUID.randomUUID().toString());
        recipeRecord.setTitle(recipe.getTitle());
        // Convert cuisine from String to Enums.Cuisine
        recipeRecord.setCuisine(Enums.Cuisine.valueOf(recipe.getCuisine()));
        recipeRecord.setDescription(recipe.getDescription());
        // Convert dietaryRestrictions from String to Enums.DietaryRestrictions
        recipeRecord.setDietaryRestrictions(Enums.DietaryRestrictions.valueOf(recipe.getDietaryRestrictions()));
        recipeRecord.setHasDietaryRestrictions(recipe.getGetHasDietaryRestrictions());
        recipeRecord.setIngredients(recipe.getIngredients());
        recipeRecord.setInstructions(recipe.getInstructions());
        return recipeRecord;
    }
}
