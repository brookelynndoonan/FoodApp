package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.RecipeCreateRequest;
import com.kenzie.appserver.controller.model.RecipeResponse;
import com.kenzie.appserver.exceptions.RecipeNotFoundException;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.RecipeService;
import com.kenzie.appserver.service.model.Recipe;
import com.kenzie.appserver.service.model.RecipeMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> getRecipeById(@PathVariable("id") String id) throws RecipeNotFoundException {
        RecipeRecord recipeRecord = recipeService.getRecipeById(id);
        Recipe recipe = RecipeMapper.recipeToRecipeRecord(recipeRecord);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        RecipeResponse recipeResponse = createRecipeResponse(recipe);
        return ResponseEntity.ok(recipeResponse);
    }

    @PostMapping
    public ResponseEntity<RecipeResponse> addNewRecipe(@RequestBody RecipeCreateRequest recipeCreateRequest) {
        Recipe recipe = new Recipe(
                UUID.randomUUID().toString(),
                recipeCreateRequest.getTitle(),
                recipeCreateRequest.getCuisine(),
                recipeCreateRequest.getDescription(),
                recipeCreateRequest.getDietaryRestrictions(),
                recipeCreateRequest.getHasDietaryRestrictions(),
                recipeCreateRequest.getIngredients(),
                recipeCreateRequest.getInstructions());
        recipeService.addNewRecipe(recipe);

        RecipeResponse recipeResponse = createRecipeResponse(recipe);

        return ResponseEntity.created(URI.create("/recipe/" + recipeResponse.getId())).body(recipeResponse);
    }

//    @PostMapping
//    public ResponseEntity<ExampleResponse> addNewConcert(@RequestBody ExampleCreateRequest exampleCreateRequest) {
//        Example example = new Example(randomUUID().toString(),
//                exampleCreateRequest.getName());
//        exampleService.addNewExample(example);
//
//        ExampleResponse exampleResponse = new ExampleResponse();
//        exampleResponse.setId(example.getId());
//        exampleResponse.setName(example.getName());
//
//        return ResponseEntity.created(URI.create("/example/" + exampleResponse.getId())).body(exampleResponse);
//    }
//}

//           System.out.println("Recipe Record ID: " + recipeCreateRequest.getId());
//        System.out.println("Recipe Record Title: " + recipeCreateRequest.getTitle());
//        System.out.println("Recipe Record Cuisine: " + recipeCreateRequest.getCuisine());
//        System.out.println("Recipe Record Description: " + recipeCreateRequest.getDescription());
//        System.out.println("Recipe Record Dietary Restrictions: " + recipeCreateRequest.getDietaryRestrictions());
//        System.out.println("Recipe Record Has Dietary Restrictions: " + recipeCreateRequest.getHasDietaryRestrictions());
//        System.out.println("Recipe Record Ingredients: " + recipeCreateRequest.getIngredients());
//        System.out.println("Recipe Record Instructions: " + recipeCreateRequest.getInstructions());
//        System.out.println("After Create Recipe in RecipeController.java");


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
        recipeResponse.setHasDietaryRestrictions(recipe.getGetHasDietaryRestrictions());
        recipeResponse.setIngredients(recipe.getIngredients());
        recipeResponse.setInstructions(recipe.getInstructions());
        return recipeResponse;
    }

}