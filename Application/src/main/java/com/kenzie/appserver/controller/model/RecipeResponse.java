package com.kenzie.appserver.controller.model;
//for merge
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecipeResponse {
    @JsonProperty("title")
    private String title;
    @JsonProperty("id")
    private String id;
    @JsonProperty("cuisine")
    private String cuisine;

    @JsonProperty("description")
    private String description;

    @JsonProperty("dietaryRestrictions")
    private String dietaryRestrictions;

    @JsonProperty("hasDietaryRestrictions")
    private Boolean hasDietaryRestrictions;

    @JsonProperty("ingredients")
    private List<String> ingredients;

    @JsonProperty("instructions")
    private String instructions;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
