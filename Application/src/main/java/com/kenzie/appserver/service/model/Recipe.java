package com.kenzie.appserver.service.model;

import java.util.List;
import java.util.UUID;

public class Recipe {
    private final String title;

    private final String cuisine;
    private final String description;
    private final String dietaryRestrictions;
    private final boolean dietaryRestrictionsBool;
    private final List<String> ingredients;
    private final String instructions;


    public Recipe(String title, String id,String cuisine, String description, String dietaryRestrictions, boolean dietaryRestrictionsBool, List<String> ingredients, String instructions) {

        this.title = title;
        this.cuisine = cuisine;
        this.description = description;
        this.dietaryRestrictions = dietaryRestrictions;
        this.dietaryRestrictionsBool = dietaryRestrictionsBool;
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

    public boolean isDietaryRestrictionsBool() {
        return dietaryRestrictionsBool;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getId() {
        return id;
    }
}
