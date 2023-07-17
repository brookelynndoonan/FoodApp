package com.kenzie.appserver.service;

import com.kenzie.appserver.converters.RecipeMapper;
import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.model.Recipe;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAllRecipes() {
        List<RecipeRecord> recipeRecords = recipeRepository.findAll();
        return recipeRecords.stream()
                .map(RecipeMapper::recipeRecordtoRecipe)
                .collect(Collectors.toList());
    }

    public Recipe addNewRecipe(Recipe recipe) {
        RecipeRecord recipeRecord = new RecipeRecord();
        recipeRecord.setId(recipe.getId());
        recipeRecord.setTitle(recipe.getTitle());
        recipeRecord.setCuisine(recipe.getCuisine());
        recipeRecord.setDescription(recipe.getDescription());
        recipeRecord.setDietaryRestrictions(recipe.getDietaryRestrictions());
        recipeRecord.setHasDietaryRestrictions(recipe.getHasDietaryRestrictions());
        recipeRecord.setIngredients(recipe.getIngredients());
        recipeRecord.setInstructions(recipe.getInstructions());
        recipeRepository.save(recipeRecord);
        return recipe;
    }

    public List<Recipe> getRecipesByCuisine(String cuisine) {
        List<RecipeRecord> recipeRecords = recipeRepository.findByCuisine(cuisine);
        List<Recipe> recipes = recipeRecords.stream()
                .map(RecipeMapper::recipeRecordtoRecipe)
                .collect(Collectors.toList());
        return recipes;
    }

    public List<Recipe> getRecipesByDietaryRestrictions(String dietaryRestrictions) {
        List<RecipeRecord> recipeRecords = recipeRepository.findByDietaryRestrictions(dietaryRestrictions);
        return recipeRecords.stream()
                .map(RecipeMapper::recipeRecordtoRecipe)
                .collect(Collectors.toList());
    }

    public RecipeRecord getRecipeById(String id)  {
        Optional<RecipeRecord> optionalRecipeRecord = recipeRepository.findById(id);

        return optionalRecipeRecord.orElse(null);
    }

    public List<Recipe> searchRecipes(String query, String cuisine, String dietaryRestrictions) {
        // Convert the query to lowercase
        String lowercaseQuery = query.toLowerCase();

        List<RecipeRecord> recipeRecords = recipeRepository.findAll();

        List<Recipe> recipes = recipeRecords.stream()
                .map(RecipeMapper::recipeRecordtoRecipe)
                .filter(recipe -> {
                    String title = recipe.getTitle().toLowerCase();
                    String description = recipe.getDescription().toLowerCase();
                    List<String> ingredients = recipe.getIngredients().stream()
                            .map(String::toLowerCase)
                            .collect(Collectors.toList());
                    String instructions = recipe.getInstructions().toLowerCase();

                    return title.contains(lowercaseQuery)
                            || description.contains(lowercaseQuery)
                            || ingredients.stream().anyMatch(ingredient -> ingredient.contains(lowercaseQuery))
                            || instructions.contains(lowercaseQuery);
                })
                .collect(Collectors.toList());

        if (StringUtils.isNotBlank(cuisine)) {
            recipes = recipes.stream()
                    .filter(recipe -> cuisine.equalsIgnoreCase(recipe.getCuisine()))
                    .collect(Collectors.toList());
        }

        if (StringUtils.isNotBlank(dietaryRestrictions)) {
            recipes = recipes.stream()
                    .filter(recipe -> dietaryRestrictions.equalsIgnoreCase(recipe.getDietaryRestrictions()))
                    .collect(Collectors.toList());
        }

        return recipes;
    }



    public void deleteRecipeById(String id) {
        recipeRepository.deleteById(id);
    }
}