package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.service.model.Example;
import com.kenzie.appserver.service.model.Recipe;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RecipeService {

    private  RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }

    public Recipe findRecipeByID (String recipeID){

        Recipe recipeFromBackEndService = recipeRepository
                .findById(recipeID)
                .map(recipe -> new Recipe(recipe.getTitle(),recipeID,
                                          recipe.getCuisine(),recipe.getDescription(),
                                          recipe.getDietaryRestrictions(),recipe.isHasDietaryRestrictions(),
                                          recipe.getIngredients(),recipe.getInstructions()))
                .orElse(null);

        return recipeFromBackEndService;
    }

    public Recipe addNewRecipe (Recipe recipe){

        RecipeRecord recipeRecord = new RecipeRecord();

        Recipe recipeReturn = new Recipe(recipe.getTitle(), UUID.randomUUID().toString(),
                                         recipe.getCuisine(),
                                         recipe.getDescription(), recipe.getDietaryRestrictions(),
                                         recipe.isDietaryRestrictionsBool(), recipe.getIngredients(),
                                         recipe.getInstructions());

        recipeRecord.setId(recipeReturn.getId());
        recipeRecord.setCuisine(recipeReturn.getCuisine());
        recipeRecord.setDescription(recipeReturn.getDescription());
        recipeRecord.setDietaryRestrictions(recipeReturn.getDietaryRestrictions());
        recipeRecord.setIngredients(recipeReturn.getIngredients());
        recipeRecord.setInstructions(recipeReturn.getInstructions());
        recipeRecord.setTitle(recipeReturn.getTitle());
        recipeRecord.setHasDietaryRestrictions(recipeReturn.isDietaryRestrictionsBool());


        recipeRepository.save(recipeRecord);

        return recipeReturn;
    }


}
