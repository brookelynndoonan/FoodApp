//package com.kenzie.appserver.service.model;
//
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
//import com.kenzie.appserver.repositories.model.Enums;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//@DynamoDBTable(tableName = "Recipes")
//public class ExtraRecipeRecord {
//
//    private String title;
//    private String id;
//    private Enums.Cuisine cuisine;
//    private String description;
//    private Enums.DietaryRestrictions dietaryRestrictions;
//    private boolean hasDietaryRestrictions;
//    private List<String> ingredients;
//    private String instructions;
//
//    @DynamoDBHashKey(attributeName = "id")
//    public String getId() {
//        return id;
//    }
//
//    @DynamoDBAttribute(attributeName = "title")
//    public String getTitle() {
//        return title;
//    }
//
//    @DynamoDBAttribute(attributeName = "cuisine")
//    public Enums.Cuisine getCuisine() {
//        return cuisine;
//    }
//
//    @DynamoDBAttribute(attributeName = "description")
//    public String getDescription() {
//        return description;
//    }
//
//    @DynamoDBAttribute(attributeName = "dietaryRestrictions")
//    public Enums.DietaryRestrictions getDietaryRestrictions() {
//        return dietaryRestrictions;
//    }
//
//    @DynamoDBAttribute(attributeName = "dietaryRestrictionsBool")
//    public boolean hasDietaryRestrictions() {
//        return hasDietaryRestrictions;
//    }
//
//    @DynamoDBAttribute(attributeName = "ingredients")
//    public List<String> getIngredients() {
//        return ingredients;
//    }
//
//    @DynamoDBAttribute(attributeName = "instructions")
//    public String getInstructions() {
//        return instructions;
//    }
//
//   public void setTitle(String title) {
//        if (title == null) {
//            throw new IllegalArgumentException("Title must not be blank.");
//        }
//        String titlePattern = "[A-Z][a-zA-Z0-9 ]*";
//        if (!title.matches(titlePattern)) {
//            throw new IllegalArgumentException("Invalid title format. Title should start with a capital letter and contain only alphanumeric characters.");
//        }
//        this.title = title;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public void setCuisine(Enums.Cuisine cuisine) {
//        if (cuisine == null) {
//            throw new IllegalArgumentException("Cuisine must be selected.");
//        }
//        this.cuisine = cuisine;
//    }
//
//    public void setDescription(String description) {
//        if (description == null || description.isEmpty()) {
//            throw new IllegalArgumentException("Description must not be null or empty.");
//        }
//        if (description.length() > 250) {
//            throw new IllegalArgumentException("Description must be less than or equal to 250 characters.");
//        }
//        this.description = description;
//    }
//
//    public void setDietaryRestrictions(Enums.DietaryRestrictions dietaryRestrictions) {
//        if (dietaryRestrictions == null) {
//            throw new IllegalArgumentException("Dietary restrictions must be selected.");
//        }
//        this.dietaryRestrictions = dietaryRestrictions;
//        this.hasDietaryRestrictions = dietaryRestrictions != Enums.DietaryRestrictions.NONE;
//    }
//
//    public void setHasDietaryRestrictions(boolean hasDietaryRestrictions) {
//        this.hasDietaryRestrictions = hasDietaryRestrictions;
//    }
//
//    public void setIngredients(List<String> ingredients) {
//        if (ingredients != null) {
//            for (String ingredient : ingredients) {
//                addIngredient(ingredient);
//            }
//        }
//    }
//
//    public void addIngredient(String ingredient) {
//        if (this.ingredients == null) {
//            this.ingredients = new ArrayList<>();
//        }
//        this.ingredients.add(ingredient);
//    }
//
//    public void setInstructions(String instructions) {
//        if (instructions == null || instructions.isEmpty()) {
//            throw new IllegalArgumentException("Instructions must not be null or empty.");
//        }
//
//        // Split the instructions into individual steps
//        String[] steps = instructions.split("\\n");
//
//        // Format the steps as a numbered list
//        StringBuilder formattedInstructions = new StringBuilder();
//        for (int i = 0; i < steps.length; i++) {
//            formattedInstructions.append((i + 1)).append(". ").append(steps[i]);
//            if (i < steps.length - 1) {
//                formattedInstructions.append("\n");
//            }
//        }
//
//        this.instructions = formattedInstructions.toString();
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        RecipeRecord that = (RecipeRecord) o;
//        return hasDietaryRestrictions == that.hasDietaryRestrictions && Objects.equals(title, that.title) && Objects.equals(id, that.id) && Objects.equals(cuisine, that.cuisine) && Objects.equals(description, that.description) && Objects.equals(dietaryRestrictions, that.dietaryRestrictions) && Objects.equals(ingredients, that.ingredients) && Objects.equals(instructions, that.instructions);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(title, id, cuisine, description, dietaryRestrictions, hasDietaryRestrictions, ingredients, instructions);
//    }
//
//    public static class Ingredient {
//        private String name;
//        private int quantity;
//        private SharedEnums.QuantityType quantityType;
//        private final String ingredientString;
//
//        public Ingredient(String name, int quantity, SharedEnums.QuantityType quantityType) {
//            String nameFormat = "[a-zA-Z0-9 ]*";
//            if (!name.matches(nameFormat)) {
//                throw new IllegalArgumentException("Invalid Ingredient format. Title contain only alphanumeric characters.");
//            }
//            if (quantity <= 0) {
//                throw new IllegalArgumentException("Ingredient quantity must be a positive value.");
//            }
//            if (quantityType == null) {
//                throw new IllegalArgumentException("Quantity type must be selected.");
//            }
//
//            this.ingredientString = name + " " + quantity + " " + quantityType;
//        }
//        public String getIngredientString() {
//            return ingredientString;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public int getQuantity() {
//            return quantity;
//        }
//
//        public SharedEnums.QuantityType getQuantityType() {
//            return quantityType;
//        }
//    }
//}
//
