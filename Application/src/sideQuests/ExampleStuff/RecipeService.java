package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.model.ExampleRecord;
import com.kenzie.appserver.repositories.ExampleRepository;
import com.kenzie.appserver.service.model.Example;

import org.springframework.stereotype.Service;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipRepository;
    }

    public Recipe findRecipeById(String id) {
        Recipe recipeFromBackend = recipeRepository
                .findById(id)
                .map(recipe -> new Recipe(recipe.getTitle(), recipeID,
                        recipe.getCuisine().toString(),
                        recipe.getDescription(),
                        recipe.getDietaryRestrictions().toString(),
                        recipe.hasDietaryRestrictions(),
                        recipe.getIngredients(),
                        recipe.getInstructions()))
                .orElse(null);

        return recipeFromBackend;
    }

    public Example addNewExample(Example example) {
        ExampleRecord exampleRecord = new ExampleRecord();
        exampleRecord.setId(example.getId());
        exampleRecord.setName(example.getName());
        exampleRepository.save(exampleRecord);
        return example;
    }

}