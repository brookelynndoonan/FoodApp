package com.kenzie.appserver.service.model;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private final String id;
    private final String title;
    private final String cuisine;
    private final String description;
    private final String dietaryRestrictions;
    private final boolean hasDietaryRestrictions;
    private final List<String> ingredients;
    private final String instructions;

    public Recipe(String id, String title, String cuisine, String description, String dietaryRestrictions, boolean hasDietaryRestrictions, List<String> ingredients, String instructions) {
        this.title = title;
        this.id = id;
        this.cuisine = cuisine;
        this.description = description;
        this.dietaryRestrictions = dietaryRestrictions;
        this.hasDietaryRestrictions = hasDietaryRestrictions;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public String getTitle() {
        return title;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getDescription() {
        return description;
    }

    public String getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public boolean hasDietaryRestrictions() {
        return hasDietaryRestrictions;
    }

    public List<String> getIngredients() {
        List<String> uniqueIngredients = new ArrayList<>();
        for (String ingredient : ingredients) {
            if (!uniqueIngredients.contains(ingredient)) {
                uniqueIngredients.add(ingredient);
            }
        }
        return uniqueIngredients;
    }


    public String getInstructions() {
        return instructions;
    }

    public String getId() {
        return id;
    }
}
