package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.RecipeResponse;
import com.kenzie.appserver.exceptions.RecipeNotFoundException;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.RecipeService;
import com.kenzie.appserver.service.model.Recipe;
import com.kenzie.appserver.service.model.RecipeMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> getRecipeById(@PathVariable("id") String id) throws RecipeNotFoundException {
        RecipeRecord recipeRecord = recipeService.getRecipeById(id);
        Recipe recipe = RecipeMapper.toRecipe(recipeRecord);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        RecipeResponse recipeResponse = createRecipeResponse(recipe);
        return ResponseEntity.ok(recipeResponse);
    }

    @PostMapping
    public ResponseEntity<RecipeRecord> addNewRecipe(@RequestBody RecipeRecord recipeRecord) {
        Recipe recipe = RecipeMapper.toRecipe(recipeRecord);
        // Call the service to save the recipe

        Recipe savedRecipe = recipeService.createRecipe(recipe);

        // Convert the saved recipe back to` DTO and return the response
        RecipeRecord savedRecipeRecord = RecipeMapper.toRecipeRecord(savedRecipe);
        return ResponseEntity.ok(savedRecipeRecord);
    }


    @GetMapping("/cuisine/{cuisine}")
    public ResponseEntity<List<RecipeResponse>> getRecipesByCuisine(@PathVariable("cuisine") String cuisine) {
        List<Recipe> recipes = recipeService.getRecipesByCuisine(cuisine);
        List<RecipeResponse> recipeResponses = recipes.stream()
                .map(this::createRecipeResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(recipeResponses);
    }

    @GetMapping("/dietaryRestrictions/{dietaryRestrictions}")
    public ResponseEntity<List<RecipeResponse>> getRecipesByDietaryRestrictions(@PathVariable("dietaryRestrictions") String restrictions) {
        List<Recipe> recipes = recipeService.getRecipesByDietaryRestrictions(restrictions);
        List<RecipeResponse> recipeResponses = recipes.stream()
                .map(this::createRecipeResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(recipeResponses);
    }

    private RecipeResponse createRecipeResponse(Recipe recipe) {
        RecipeResponse recipeResponse = new RecipeResponse();
        recipeResponse.setId(recipe.getId());
        recipeResponse.setTitle(recipe.getTitle());
        recipeResponse.setCuisine(recipe.getCuisine());
        recipeResponse.setDescription(recipe.getDescription());
        recipeResponse.setDietaryRestrictions(recipe.getDietaryRestrictions());
        recipeResponse.setHasDietaryRestrictions(recipe.hasDietaryRestrictions());
        recipeResponse.setIngredients(recipe.getIngredients());
        recipeResponse.setInstructions(recipe.getInstructions());
        return recipeResponse;
    }

}