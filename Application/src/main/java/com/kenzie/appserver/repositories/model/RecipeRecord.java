package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@DynamoDBTable(tableName = "Recipes")
public class RecipeRecord {

    private String title;
    private String id;
    private Enums.Cuisine cuisine;
    private String description;
    private Enums.DietaryRestrictions dietaryRestrictions;
    private boolean hasDietaryRestrictions;
    private List<String> ingredients;
    private String instructions;

    @DynamoDBHashKey(attributeName = "id")
    public String getId() {
        return id;
    }

    @DynamoDBAttribute(attributeName = "title")
    public String getTitle() {
        return title;
    }

    @DynamoDBAttribute(attributeName = "cuisine")
    public Enums.Cuisine getCuisine() {
        return cuisine;
    }

    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return description;
    }

    @DynamoDBAttribute(attributeName = "dietaryRestrictions")
    public Enums.DietaryRestrictions getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    @DynamoDBAttribute(attributeName = "hasDietaryRestrictions")
    public boolean hasDietaryRestrictions() {
        return hasDietaryRestrictions;
    }

    @DynamoDBAttribute(attributeName = "ingredients")
    public List<String> getIngredients() {
        return ingredients;
    }

    @DynamoDBAttribute(attributeName = "instructions")
    public String getInstructions() {
        return instructions;
    }

      public void setTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title must not be blank.");
        }
        String titlePattern = "[A-Z][a-zA-Z0-9 ]*";
        if (!title.matches(titlePattern)) {
            throw new IllegalArgumentException("Invalid title format. Title should start with a capital letter and contain only alphanumeric characters.");
        }
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCuisine(Enums.Cuisine cuisine) {
        if (cuisine == null) {
            throw new IllegalArgumentException("Cuisine must be selected.");
        }
        this.cuisine = cuisine;
    }

    public void setDescription(String description) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description must not be null or empty.");
        } //Description must be less than 250 characters
        if (description.length() > 250) {
            throw new IllegalArgumentException("Description must be less than or equal to 250 characters.");
        }
        this.description = description;
    }

    public void setDietaryRestrictions(Enums.DietaryRestrictions dietaryRestrictions) {
        if (dietaryRestrictions == null) {
            throw new IllegalArgumentException("Dietary restrictions must be selected.");
        }
        this.dietaryRestrictions = dietaryRestrictions;
        this.hasDietaryRestrictions = dietaryRestrictions != Enums.DietaryRestrictions.NONE;
    }

    public void setHasDietaryRestrictions(boolean hasDietaryRestrictions) {
        this.hasDietaryRestrictions = hasDietaryRestrictions;
    }

    public void setIngredients(List<String> ingredients) {
        if (ingredients != null) {
            for (String ingredient : ingredients) {
                addIngredient(ingredient);
            }
        }
    }

    public void addIngredient(String ingredient) {
        if (this.ingredients == null) {
            this.ingredients = new ArrayList<>();
        }
        this.ingredients.add(ingredient);
    }

    public void setInstructions(String instructions) {
        if (instructions == null || instructions.isEmpty()) {
            throw new IllegalArgumentException("Instructions must not be null or empty.");
        }
        this.instructions = instructions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeRecord that = (RecipeRecord) o;
        return hasDietaryRestrictions == that.hasDietaryRestrictions && Objects.equals(getTitle(), that.getTitle()) && Objects.equals(getId(), that.getId()) && getCuisine() == that.getCuisine() && Objects.equals(getDescription(), that.getDescription()) && getDietaryRestrictions() == that.getDietaryRestrictions() && Objects.equals(getIngredients(), that.getIngredients()) && Objects.equals(getInstructions(), that.getInstructions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getId(), getCuisine(), getDescription(), getDietaryRestrictions(), hasDietaryRestrictions, getIngredients(), getInstructions());
    }
}

