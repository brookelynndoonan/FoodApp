package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.model.Enums;
import com.kenzie.appserver.repositories.model.RecipeRecord;

import java.util.ArrayList;
import java.util.List;

public class SampleData {

    public static List<RecipeRecord> getSampleRecipeRecords() {
        List<RecipeRecord> recipeRecords = new ArrayList<>();

        // Recipe 1
        RecipeRecord recipeRecord1 = new RecipeRecord();
        recipeRecord1.setTitle("Spaghetti Bolognese");
        recipeRecord1.setCuisine(Enums.Cuisine.ITALIAN);
        recipeRecord1.setDescription("Classic Italian pasta dish");
        recipeRecord1.setDietaryRestrictions(Enums.DietaryRestrictions.NONE);
        // Set other properties as needed for recipeRecord1
        recipeRecords.add(recipeRecord1);

        // Recipe 2
        RecipeRecord recipeRecord2 = new RecipeRecord();
        recipeRecord2.setTitle("Chicken Stir-Fry");
        recipeRecord2.setDescription("Healthy and delicious stir-fry");
        recipeRecord2.setCuisine(Enums.Cuisine.ASIAN);
        recipeRecord2.setDietaryRestrictions(Enums.DietaryRestrictions.GLUTEN_FREE);
        // Set other properties as needed for recipeRecord2
        recipeRecords.add(recipeRecord2);

        // Add more recipes as needed

        return recipeRecords;
    }
}

