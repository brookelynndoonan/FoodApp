package com.kenzie.appserver.controller.model;
//for merge
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

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
        return UUID.randomUUID().toString();
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

    public Boolean getHasDietaryRestrictions() {
        return hasDietaryRestrictions;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDietaryRestrictions(String dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public void setHasDietaryRestrictions(Boolean hasDietaryRestrictions) {
        this.hasDietaryRestrictions = hasDietaryRestrictions;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}