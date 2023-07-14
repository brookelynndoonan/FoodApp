package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.RecipeResponse;
import com.kenzie.appserver.converters.RecipeMapper;
import com.kenzie.appserver.service.RecipeService;
import com.kenzie.appserver.service.model.Recipe;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IntegrationTest
public class GetAllCuisineRecipesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeController recipeController;

    RecipeMapper recipeMapper;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();


    @Test//Created by Brooke, Jess, & Sha'mir
    public void getAllCuisineRecipes_isSuccessful() throws Exception {
        List<String> ingredients = new ArrayList<>();
        ingredients.add("Ingredient 1 cup");
        ingredients.add("Ingredient 2 cups");

        Recipe recipeOne = new Recipe(
                UUID.randomUUID().toString(),
                "Sample Recipe One",
                "Italian",
                "A delicious Italian dish",
                "Gluten Free",
                true,
                ingredients,
                "Step 1, Step 2, Step 3");

        Recipe recipeTwo = new Recipe(
                UUID.randomUUID().toString(),
                "Sample Recipe",
                "Indian",
                "A delicious Indian dish",
                "Vegan",
                true,
                ingredients,
                "Step 1, Step 2, Step 3");

        Recipe recipeThree = new Recipe(
                UUID.randomUUID().toString(),
                "Sample Recipe",
                "Italian",
                "A delicious Italian dish",
                "Gluten Free",
                true,
                ingredients,
                "Step 1, Step 2, Step 3");

        recipeService.addNewRecipe(recipeOne);
        recipeService.addNewRecipe(recipeTwo);
        recipeService.addNewRecipe(recipeThree);

        recipeController.getRecipesByCuisine("Italian");


        mockMvc.perform(get("/recipes/cuisine/{cuisine}", "Italian")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
               /* .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("cuisine").value("Cuisine"))*/;

        // THEN
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipeOne);
        recipes.add(recipeTwo);
        recipes.add(recipeThree);
        List<RecipeResponse> listCuisineRecipes = recipes.stream()
                .map(this::createRecipeResponse)
                .collect(Collectors.toList());
        //assertThat(listCuisineRecipes.size()).isEqualTo(2);

        // CLEANUP
        recipeService.deleteRecipeById(recipeOne.getId());
        recipeService.deleteRecipeById(recipeTwo.getId());
        recipeService.deleteRecipeById(recipeThree.getId());
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

    @Test
    public void testGetRecipesByCuisine() throws Exception {
        String cuisine = "ITALIAN";

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/recipes/cuisine/{cuisine}", cuisine)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cuisine").value(cuisine))
                .andReturn();
        // Add more assertions as needed for the response body
    }

}

