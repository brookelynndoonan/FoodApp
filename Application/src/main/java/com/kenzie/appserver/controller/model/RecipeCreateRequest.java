package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class RecipeCreateRequest {

    @NotEmpty
    @JsonProperty("id")
    private String id;

    @NotEmpty
    @JsonProperty("title")
    private String title;

    @NotEmpty
    @JsonProperty("cuisine")
    private String cuisine;

    @NotEmpty
    @JsonProperty("description")
    private String description;

    @NotEmpty
    @JsonProperty("dietaryRestrictions")
    private String dietaryRestrictions;


    @NotNull
    @JsonProperty("hasDietaryRestrictions")
    private Boolean hasDietaryRestrictions = false;

    @NotEmpty
    @JsonProperty("ingredients")
    private List<String> ingredients;

    @NotEmpty
    @JsonProperty("instructions")
    private String instructions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setDietaryRestrictions(String dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public Boolean getHasDietaryRestrictions() {
        return hasDietaryRestrictions;
    }

    public void setHasDietaryRestrictions(Boolean hasDietaryRestrictions) {
        this.hasDietaryRestrictions = hasDietaryRestrictions;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
