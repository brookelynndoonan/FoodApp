package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.RecipeCreateRequest;
import com.kenzie.appserver.controller.model.RecipeResponse;
import com.kenzie.appserver.converters.RecipeMapper;
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

    private final RecipeService recipeService;
    private RecipeRepository recipeRepository;
    private RecipeMapper recipeMapper;
    private List<String> ingredients;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping
    public ResponseEntity<List<RecipeResponse>> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();

        List<RecipeResponse> recipeResponses = recipes.stream()
                .map(recipe -> {
                    RecipeResponse recipeResponse = createRecipeResponse(recipe);
                    return recipeResponse;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(recipeResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> getRecipeById(@PathVariable("id") String id) throws Exception {
        RecipeRecord recipeRecord = recipeService.getRecipeById(id);
        Recipe recipe = RecipeMapper.recipeRecordtoRecipe(recipeRecord);
       /* if (recipe.equals(null)) {
            return ResponseEntity.notFound().build();
        }*/
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

        RecipeResponse recipeResponse = createRecipeResponse(recipe);

        return ResponseEntity.created(URI.create("/recipe/" + recipeResponse.getId())).body(recipeResponse);

    }

    @GetMapping("/cuisine/{cuisine}")
    public ResponseEntity<List<RecipeResponse>> getRecipesByCuisine(@PathVariable("cuisine") String cuisine) {
        List<Recipe> recipes = recipeService.getRecipesByCuisine(cuisine.toUpperCase().replace(" ", "_"));
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

    //Helper Method for Recipe Response
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

}
