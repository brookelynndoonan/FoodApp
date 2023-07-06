
package com.kenzie.appserver.controller;


import com.kenzie.appserver.controller.model.RecipeCreateRequest;
import com.kenzie.appserver.controller.model.RecipeResponse;
import com.kenzie.appserver.service.RecipeService;
import com.kenzie.appserver.service.model.Recipe;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    //Retrieve Recipe by ID.
    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> getRecipeById(@PathVariable("id") String id) {
        Recipe recipe = recipeService.getRecipeById(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        RecipeResponse recipeResponse = createRecipeResponse(recipe);
        return ResponseEntity.ok(recipeResponse);
    }


    //A Post for creating and posting a recipe.
    @PostMapping
    public ResponseEntity<RecipeResponse> addNewRecipe(@RequestBody RecipeCreateRequest recipeCreateRequest){
        Recipe recipe = new Recipe(
                recipeCreateRequest.getTitle(),
                recipeCreateRequest.getId(),
                recipeCreateRequest.getCuisine(),
                recipeCreateRequest.getDescription(),
                recipeCreateRequest.getDietaryRestrictions(),
                recipeCreateRequest.getDietaryRestrictionsBool(),
                recipeCreateRequest.getIngredients(),
                recipeCreateRequest.getInstructions());

        recipeService.addNewRecipe(recipe);

        RecipeResponse recipeResponse = createRecipeResponse(recipe);

        return ResponseEntity.created(URI.create("/recipes/" + recipeResponse.getId())).body(recipeResponse);
    }

    @GetMapping("/cuisine/{cuisine}")
    public ResponseEntity<List<RecipeResponse>> getAllCuisine(
            @PathVariable("cuisine") String cuisine) {
        List<Recipe> recipes = recipeService.findAllCuisine(cuisine);
        // Returns 204, if no recipes present.
        if (recipes == null ||  recipes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        // Pulling cuisine recipes and returning list.
        List<RecipeResponse> response = new ArrayList<>();
        for (Recipe allCuisineRecipes : recipes) {
            response.add(this.createRecipeResponse(allCuisineRecipes));
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/dietaryRestrictions/{dietaryRestrictions}")
    public ResponseEntity<List<RecipeResponse>> getAllDietaryRestrictions(
            @PathVariable("dietaryRestrictions") String dietaryRestrictions) {
        List<Recipe> recipes = recipeService.findAllDietaryRestriction(dietaryRestrictions);
        // Returns 204, if no recipes present.
        if (recipes == null ||  recipes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        // Pulling Dietary Restricted recipes and creating list.
        List<RecipeResponse> response = new ArrayList<>();
        for (Recipe allDietaryRestrictionRecipes : recipes) {
            response.add(this.createRecipeResponse(allDietaryRestrictionRecipes));
        }

        return ResponseEntity.ok(response);
    }





    // Helper Method for our Recipe Response.
    private RecipeResponse createRecipeResponse(Recipe recipe) {

        RecipeResponse recipeResponse = new RecipeResponse();

        recipeResponse.setTitle(recipe.getTitle());
        recipeResponse.setId(recipe.getId());
        recipeResponse.setCuisine(recipe.getCuisine());
        recipeResponse.setDescription(recipe.getDescription());
        recipeResponse.setDietaryRestrictions(recipe.getDietaryRestrictions());
        recipeResponse.setDietaryRestrictionsBool(recipe.hasDietaryRestrictions());
        recipeResponse.setIngredients(recipe.getIngredients());
        recipeResponse.setInstructions(recipe.getInstructions());

        return recipeResponse;
    }

}
=======
package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.RecipeCreateRequest;
import com.kenzie.appserver.controller.model.RecipeResponse;
import com.kenzie.appserver.converters.RecipeMapper;
import com.kenzie.appserver.exceptions.RecipeNotFoundException;
import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.RecipeService;
import com.kenzie.appserver.service.model.Recipe;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private RecipeRepository recipeRepository;
    private RecipeMapper recipeMapper;
    private final RecipeService recipeService;
    private List<String> ingredients;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }



    @GetMapping
    public ResponseEntity<List<RecipeResponse>> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();

        List<RecipeResponse> recipeResponses = recipes.stream()
                .map(recipe -> {
                    RecipeResponse recipeResponse = new RecipeResponse();
                    recipeResponse.setId(recipe.getId());
                    recipeResponse.setTitle(recipe.getTitle());
                    recipeResponse.setCuisine(recipe.getCuisine());
                    recipeResponse.setDescription(recipe.getDescription());
                    recipeResponse.setDietaryRestrictions(recipe.getDietaryRestrictions());
                    recipeResponse.setHasDietaryRestrictions(recipe.getHasDietaryRestrictions());
                    recipeResponse.setIngredients(recipe.getIngredients());
                    recipeResponse.setInstructions(recipe.getInstructions());
                    return recipeResponse;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(recipeResponses);
    }



    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> getRecipeById(@PathVariable("id") String id) throws RecipeNotFoundException {
        RecipeRecord recipeRecord = recipeService.getRecipeById(id);
        Recipe recipe = RecipeMapper.recipeRecordtoRecipe(recipeRecord);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        RecipeResponse recipeResponse = createRecipeResponse(recipe);
        return ResponseEntity.ok(recipeResponse);
    }

    @PostMapping
    public ResponseEntity<RecipeResponse> addNewRecipe(@RequestBody RecipeCreateRequest recipeCreateRequest) {
        Recipe recipe = new Recipe(randomUUID().toString(),
                recipeCreateRequest.getTitle(),
                recipeCreateRequest.getCuisine(),
                recipeCreateRequest.getDescription(),
                recipeCreateRequest.getDietaryRestrictions(),
                recipeCreateRequest.getHasDietaryRestrictions(),
                recipeCreateRequest.getIngredients(),
                recipeCreateRequest.getInstructions());
        recipeService.addNewRecipe(recipe);

        RecipeResponse recipeResponse = new RecipeResponse();
        recipeResponse.setId(recipe.getId());
        recipeResponse.setTitle(recipe.getTitle());
        recipeResponse.setCuisine(recipe.getCuisine());
        recipeResponse.setDescription(recipe.getDescription());
        recipeResponse.setDietaryRestrictions(recipe.getDietaryRestrictions());
        recipeResponse.setHasDietaryRestrictions(recipe.getHasDietaryRestrictions());
        recipeResponse.setIngredients(recipe.getIngredients());
        recipeResponse.setInstructions(recipe.getInstructions());

        return ResponseEntity.created(URI.create("/recipe/" + recipeResponse.getId())).body(recipeResponse);

//        recipe.setId(UUID.randomUUID().toString());
//        if (recipe.getTitle() == null || recipe.getTitle().trim().isEmpty()) {
//            throw new IllegalArgumentException("Title must not be blank.");
//        }
//        String titlePattern = "[A-Z][a-zA-Z0-9 ]*";
//        if (!recipe.getTitle().matches(titlePattern)) {
//            throw new IllegalArgumentException("Invalid title format. Title should start with a capital letter and contain only alphanumeric characters.");
//        }
//        if (recipe.getCuisine() == null) {
//            throw new IllegalArgumentException("Cuisine must be selected.");
//        }
//        if (recipe.getDescription() == null || recipe.getDescription().isEmpty()) {
//            throw new IllegalArgumentException("Description must not be null or empty.");
//        }
//        if (recipe.getDescription().length() > 250) {
//            throw new IllegalArgumentException("Description must be less than or equal to 250 characters.");
//        }
//        if (recipe.getDietaryRestrictions() == null) {
//            throw new IllegalArgumentException("Dietary restrictions must be selected.");
//        }
//        if (recipe.getIngredients() != null) {
//            for (String ingredient : recipe.getIngredients()) {
//                addIngredient(RecipeRecord.Ingredient.createIngredientFromIngredientString(ingredient));
//            }
//        }
//        if (recipe.getInstructions() == null || recipe.getInstructions().isEmpty()) {
//            throw new IllegalArgumentException("Instructions must not be null or empty.");
//        }
//
//        RecipeRecord recipeRecord;
//        recipeRecord = RecipeMapper.recipeToRecipeRecord(recipe);
//        Recipe newRecipe = recipeService.addNewRecipe(recipe);
//        recipeRepository.save(recipeRecord);
//        System.out.println("RecipeController" + recipe.getId());
//        RecipeResponse recipeResponse = createRecipeResponse(recipe);
//
//
//        return ResponseEntity.created(URI.create("/create/" + recipeResponse.getId())).body(recipeResponse);

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
        recipeResponse.setHasDietaryRestrictions(recipe.getHasDietaryRestrictions());
        recipeResponse.setIngredients(recipe.getIngredients());
        recipeResponse.setInstructions(recipe.getInstructions());
        return recipeResponse;
    }

<<<<<<< HEAD
}

=======

}
>>>>>>> main
