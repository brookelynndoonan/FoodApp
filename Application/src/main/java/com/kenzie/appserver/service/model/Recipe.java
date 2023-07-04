package com.kenzie.appserver.service.model;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String id;
    private String title;
    private String cuisine;
    private String description;
    private String dietaryRestrictions;
    private boolean getHasDietaryRestrictions;
    private List<String> ingredients;
    private String instructions;

    public Recipe(String id, String title, String cuisine, String description, String dietaryRestrictions, boolean getHasDietaryRestrictions, List<String> ingredients, String instructions) {
        this.title = title;
        this.id = id;
        this.cuisine = cuisine;
        this.description = description;
        this.dietaryRestrictions = dietaryRestrictions;
        this.getHasDietaryRestrictions = getHasDietaryRestrictions;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public Recipe() {
    }

    public String getTitle() {
        return title;
    }

    public String getCuisine() {
        return cuisine.toUpperCase().replace(" ", "_");
    }

    public String getDescription() {
        return description;
    }

    public String getDietaryRestrictions() {
        return dietaryRestrictions.toUpperCase().replace(" ", "_");
    }

    public boolean getGetHasDietaryRestrictions() {
        return getHasDietaryRestrictions;
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

    public void setId(String id) {
        this.id = id;
    }
}
