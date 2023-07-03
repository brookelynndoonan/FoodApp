package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@DynamoDBTable(tableName = "Recipes")
public class RecipeRecord {

    private String id;
    private String title;
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
    public boolean getHasDietaryRestrictions() {
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
        if (title == null || title.trim().isEmpty()) {
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
        }
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
                addIngredient(Ingredient.createIngredientFromIngredientString(ingredient));
            }
        }
    }

    public void addIngredient(Ingredient ingredient) {
        if (ingredients == null) {
            ingredients = new ArrayList<>();
        }
        ingredients.add(ingredient.getIngredientString());
    }

    public void setInstructions(String instructions) {
        if (instructions == null || instructions.isEmpty()) {
            throw new IllegalArgumentException("Instructions must not be null or empty.");
        }

        // Split the instructions into individual steps
        String[] steps = instructions.split("\\n");

        // Format the steps as a numbered list
        StringBuilder formattedInstructions = new StringBuilder();
        for (int i = 0; i < steps.length; i++) {
            formattedInstructions.append((i + 1)).append(". ").append(steps[i]);
            if (i < steps.length - 1) {
                formattedInstructions.append("\n");
            }
        }

        this.instructions = formattedInstructions.toString();
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

    public static class Ingredient {
        private final String name;
        private final int quantity;
        private final Enums.QuantityType quantityType;
        private final String ingredientString;

        public Ingredient(String name, int quantity, Enums.QuantityType quantityType) {
            if (quantity <= 0) {
                throw new IllegalArgumentException("Ingredient quantity must be a positive value.");
            }
            this.name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
            this.quantity = quantity;
            this.quantityType = quantityType;
            this.ingredientString = name + " " + quantity + " " + quantityType.toString().toLowerCase();
        }

        public static Ingredient createIngredientFromIngredientString(String ingredientString) {
            // Split the ingredient string into parts (quantity, unit, name)
            String[] parts = ingredientString.split(" ");

            // Extract the quantity, unit, and name
            String name = parts[0];
            int quantity = Integer.parseInt(parts[1]);
            String unit = parts[2];

            // Convert the unit to uppercase for case-insensitive comparison
            unit = unit.toUpperCase();

            // Use a case-insensitive search to find the matching QuantityType enum constant
            String finalUnit = unit;
            Enums.QuantityType quantityType = Arrays.stream(Enums.QuantityType.values())
                    .filter(q -> q.name().equalsIgnoreCase(finalUnit))
                    .findFirst()
                    .orElse(null);

            // Create the Ingredient object
            return new Ingredient(name, quantity, Enums.QuantityType.valueOf(unit));
        }


        public String getIngredientString() {
            return ingredientString;
        }

        public String getName() {
            return name;
        }

        public int getQuantity() {
            return quantity;
        }

        public Enums.QuantityType getQuantityType() {
            return quantityType;
        }
    }
}