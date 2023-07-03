//package com.kenzie.appserver.controller;
//
//
//import com.kenzie.appserver.controller.model.RecipeCreateRequest;
//import com.kenzie.appserver.controller.model.RecipeResponse;
//import com.kenzie.appserver.service.RecipeService;
//import com.kenzie.appserver.service.model.Recipe;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("/recipes")
//public class RecipeController {
//
//    private final RecipeService recipeService;
//
//    public RecipeController(RecipeService recipeService) {
//        this.recipeService = recipeService;
//    }
//
//    //Retrieve Recipe by ID.
//    @GetMapping("/{id}")
//    public ResponseEntity<RecipeResponse> getRecipeById(@PathVariable("id") String id) {
//        Recipe recipe = recipeService.getRecipeById(id);
//        if (recipe == null) {
//            return ResponseEntity.notFound().build();
//        }
//        RecipeResponse recipeResponse = createRecipeResponse(recipe);
//        return ResponseEntity.ok(recipeResponse);
//    }
//
//
//    //A Post for creating and posting a recipe.
//    @PostMapping
//    public ResponseEntity<RecipeResponse> addNewRecipe(@RequestBody RecipeCreateRequest recipeCreateRequest){
//        Recipe recipe = new Recipe(
//                recipeCreateRequest.getTitle(),
//                recipeCreateRequest.getId(),
//                recipeCreateRequest.getCuisine(),
//                recipeCreateRequest.getDescription(),
//                recipeCreateRequest.getDietaryRestrictions(),
//                recipeCreateRequest.getDietaryRestrictionsBool(),
//                recipeCreateRequest.getIngredients(),
//                recipeCreateRequest.getInstructions());
//
//        recipeService.addNewRecipe(recipe);
//
//        RecipeResponse recipeResponse = createRecipeResponse(recipe);
//
//        return ResponseEntity.created(URI.create("/recipes/" + recipeResponse.getId())).body(recipeResponse);
//    }
//
//    @GetMapping("/cuisine/{cuisine}")
//    public ResponseEntity<List<RecipeResponse>> getAllCuisine(
//            @PathVariable("cuisine") String cuisine) {
//        List<Recipe> recipes = recipeService.findAllCuisine(cuisine);
//        // Returns 204, if no recipes present.
//        if (recipes == null ||  recipes.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        // Pulling cuisine recipes and returning list.
//        List<RecipeResponse> response = new ArrayList<>();
//        for (Recipe allCuisineRecipes : recipes) {
//            response.add(this.createRecipeResponse(allCuisineRecipes));
//        }
//
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/dietaryRestrictions/{dietaryRestrictions}")
//    public ResponseEntity<List<RecipeResponse>> getAllDietaryRestrictions(
//            @PathVariable("dietaryRestrictions") String dietaryRestrictions) {
//        List<Recipe> recipes = recipeService.findAllDietaryRestriction(dietaryRestrictions);
//        // Returns 204, if no recipes present.
//        if (recipes == null ||  recipes.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        // Pulling Dietary Restricted recipes and creating list.
//        List<RecipeResponse> response = new ArrayList<>();
//        for (Recipe allDietaryRestrictionRecipes : recipes) {
//            response.add(this.createRecipeResponse(allDietaryRestrictionRecipes));
//        }
//
//        return ResponseEntity.ok(response);
//    }
//
//
//
//
//
//    // Helper Method for our Recipe Response.
//    private RecipeResponse createRecipeResponse(Recipe recipe) {
//
//        RecipeResponse recipeResponse = new RecipeResponse();
//
//        recipeResponse.setTitle(recipe.getTitle());
//        recipeResponse.setId(recipe.getId());
//        recipeResponse.setCuisine(recipe.getCuisine());
//        recipeResponse.setDescription(recipe.getDescription());
//        recipeResponse.setDietaryRestrictions(recipe.getDietaryRestrictions());
//        recipeResponse.setDietaryRestrictionsBool(recipe.hasDietaryRestrictions());
//        recipeResponse.setIngredients(recipe.getIngredients());
//        recipeResponse.setInstructions(recipe.getInstructions());
//
//        return recipeResponse;
//    }
//
//}
