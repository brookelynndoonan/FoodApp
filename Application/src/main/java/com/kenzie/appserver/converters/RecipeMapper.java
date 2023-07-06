package com.kenzie.appserver.converters;

import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.model.Recipe;

import java.util.List;

public class RecipeMapper {

    public static RecipeRecord recipeToRecipeRecord(Recipe recipe) {
        RecipeRecord recipeRecord = new RecipeRecord();
        recipeRecord.setTitle(recipe.getTitle());
        recipeRecord.setCuisine(recipe.getCuisine());
        recipeRecord.setDescription(recipe.getDescription());
        recipeRecord.setDietaryRestrictions(recipe.getDietaryRestrictions());
        recipeRecord.setHasDietaryRestrictions(recipe.getHasDietaryRestrictions());
        recipeRecord.setIngredients(recipe.getIngredients());
        recipeRecord.setInstructions(recipe.getInstructions());
        return recipeRecord;
    }

        public static Recipe recipeRecordtoRecipe(RecipeRecord recipeRecord) {
            List<String> ingredients = recipeRecord.getIngredients();
            return new Recipe(
                    recipeRecord.getId(),
                    recipeRecord.getTitle(),
                    recipeRecord.getCuisine(),
                    recipeRecord.getDescription(),
                    recipeRecord.getDietaryRestrictions(),
                    recipeRecord.getHasDietaryRestrictions(),
                    ingredients, // Pass the ingredients list directly
                    recipeRecord.getInstructions()
            );
        }
    }


