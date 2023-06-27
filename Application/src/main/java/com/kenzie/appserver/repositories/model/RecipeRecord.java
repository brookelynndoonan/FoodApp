package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@DynamoDBTable(tableName = "Recipes")
public class RecipeRecord {

    private String title;
    @Id
    private String id;
    private String cuisine;
    private String description;
    private String dietaryRestrictions;
    private boolean hasDietaryRestrictions;
    private List<String> ingredients;
    private String instructions;

    @DynamoDBHashKey(attributeName = "title")
    public String getTitle() {
        return title;
    }

    @DynamoDBAttribute(attributeName = "id")
    public String getId() {
        return id;
    }

    @DynamoDBAttribute(attributeName = "cuisine")
    public String getCuisine() {
        return cuisine;
    }

    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return description;
    }

    @DynamoDBAttribute(attributeName = "dietaryRestrictions")
    public String getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    @DynamoDBAttribute(attributeName = "dietaryRestrictionsBool")
    public boolean isHasDietaryRestrictions() {
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

    public void setCuisine(String cuisine) {
        if (cuisine == null) {
            throw new IllegalArgumentException("Cuisine must not be blank.");
        }
        String cuisinePattern = "[A-Z][a-zA-Z0-9 ]*";
        if (!cuisine.matches(cuisinePattern)) {
            throw new IllegalArgumentException("Invalid cuisine format. Cuisine should start with a capital letter and contain only alphanumeric characters.");
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

    public void setDietaryRestrictions(String dietaryRestrictions) {
        if (dietaryRestrictions == null) {
            throw new IllegalArgumentException("Dietary restrictions must be selected.");
        }
        this.dietaryRestrictions = dietaryRestrictions;
        this.hasDietaryRestrictions = dietaryRestrictions.equals("");
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
        return hasDietaryRestrictions == that.hasDietaryRestrictions && Objects.equals(title, that.title) && Objects.equals(id, that.id) && Objects.equals(cuisine, that.cuisine) && Objects.equals(description, that.description) && Objects.equals(dietaryRestrictions, that.dietaryRestrictions) && Objects.equals(ingredients, that.ingredients) && Objects.equals(instructions, that.instructions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getId(), getCuisine(), getDescription(), getDietaryRestrictions(), isHasDietaryRestrictions(), getIngredients(), getInstructions());
    }
}

