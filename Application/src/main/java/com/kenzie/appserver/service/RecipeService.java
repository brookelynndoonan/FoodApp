package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

   public Recipe findRecipeByID(String recipeID) {

        return recipeRepository
                .findById(recipeID)

                .map(recipe -> new Recipe(recipe.getTitle(), recipeID,
                        recipe.getCuisine(),
                        recipe.getDescription(),
                        recipe.getDietaryRestrictions(),
                        recipe.HasDietaryRestrictions(),
                        recipe.getIngredients(),
                        recipe.getInstructions()))
                .orElse(null);
    }       //  .orElseThrow(() -> new NotFoundException("Recipe not found with ID: " + recipeID));
            // so we don't get any null pointer exceptions --- Jess


    public Recipe addNewRecipe(Recipe recipe) {
        RecipeRecord recipeRecord = new RecipeRecord();
        Recipe recipeReturn = new Recipe(
                recipe.getTitle(),
                UUID.randomUUID().toString(),
                recipe.getCuisine(),
                recipe.getDescription(),
                recipe.getDietaryRestrictions(),
                recipe.HasDietaryRestrictions(),
                recipe.getIngredients(),
                recipe.getInstructions());

        recipeRecord.setId(recipeReturn.getId());
        recipeRecord.setCuisine(recipeReturn.getCuisine());
        recipeRecord.setDescription(recipeReturn.getDescription());
        recipeRecord.setDietaryRestrictions(recipeReturn.getDietaryRestrictions());
        recipeRecord.setIngredients(recipeReturn.getIngredients());
        recipeRecord.setInstructions(recipeReturn.getInstructions());
        recipeRecord.setTitle(recipeReturn.getTitle());
        recipeRecord.setHasDietaryRestrictions(recipeReturn.HasDietaryRestrictions());

        recipeRepository.save(recipeRecord);

        return recipeReturn;

    }
       // Will be utilizing gsi for this at some point, have already created them at the time of this comment
    public List<Recipe> findAllCuisine(String cuisine){
        List<Recipe> recipeList = new ArrayList<>();

        Iterable <RecipeRecord> recipeRecordIterable = recipeRepository.findAll();

        for (RecipeRecord recipeRecord :recipeRecordIterable
             ) {
            if(recipeRecord.getCuisine().equals(cuisine)){
                recipeList.add(recipeCreateHelper(recipeRecord));
            }
        }
        return recipeList;

    }

    public List<Recipe> findAllDietaryRestriction(String dietaryRestriction){
        List<Recipe> recipeList = new ArrayList<>();

        Iterable <RecipeRecord> recipeRecordIterable = recipeRepository.findAll();

        for (RecipeRecord recipeRecord :recipeRecordIterable
        ) {
            if(recipeRecord.getDietaryRestrictions().equals(dietaryRestriction)){
                recipeList.add(recipeCreateHelper(recipeRecord));
            }
        }
        return recipeList;

    }

    private Recipe recipeCreateHelper(RecipeRecord recipeRecord){
        Recipe recipe = new Recipe(recipeRecord.getTitle(), recipeRecord.getId(),
                                   recipeRecord.getCuisine(), recipeRecord.getDescription(),
                                   recipeRecord.getDietaryRestrictions(),
                                   recipeRecord.HasDietaryRestrictions(),
                                   recipeRecord.getIngredients(), recipeRecord.getCuisine());
        return recipe;
    }


}
