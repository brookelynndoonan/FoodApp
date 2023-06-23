package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.service.model.Example;
import com.kenzie.appserver.service.model.Recipe;

import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    private  RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }

    public Recipe findRecipeByID (String recipeID){

        Recipe recipeFromBackEndService = recipeRepository
                .findById(recipeID)
                .map(recipe -> new Recipe(recipe.getTitle(),
                                          recipe.getCuisine(),recipe.getDescription(),
                                          recipe.getDietaryRestrictions(),recipe.isHasDietaryRestrictions(),
                                          recipe.getIngredients(),recipe.getInstructions()))
                .orElse(null);

        return recipeFromBackEndService;
    }

    public Recipe addNewRecipe (Recipe recipe){
        RecipeRecord recipeRecord = new RecipeRecord();

        recipeRecord.setCuisine(recipe.getCuisine());
        recipeRecord.setDescription(recipe.getDescription());
        recipeRecord.setDietaryRestrictions(recipe.getDietaryRestrictions());
        recipeRecord.setIngredients(recipe.getIngredients());
        recipeRecord.setInstructions(recipe.getInstructions());
        recipeRecord.setTitle(recipe.getTitle());
        recipeRecord.setHasDietaryRestrictions(recipe.isDietaryRestrictionsBool());

        recipeRepository.save(recipeRecord);
        return recipe;
    }


}
