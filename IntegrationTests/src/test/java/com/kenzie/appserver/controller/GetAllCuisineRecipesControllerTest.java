package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.IntegrationTest;
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


    //Long, painstakingly, we weren't giving up until we made it work, way of finding a cuisine list.
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

        recipeService.getRecipesByCuisine(String.valueOf(recipeOne));
        recipeService.getRecipesByCuisine(String.valueOf(recipeTwo));
        recipeService.getRecipesByCuisine(String.valueOf(recipeThree));

        recipeController.getRecipesByCuisine("Italian");


        mockMvc.perform(get("/recipes/cuisine/{cuisine}", "Italian")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }


    //Fast Way to check for a cuisine list.
    @Test
    public void testGetRecipesByCuisine() throws Exception {
        String cuisine = "ITALIAN";

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/recipes/cuisine/{cuisine}", cuisine)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cuisine").value(cuisine))
                .andReturn();
        // This creates and labels a query for cuisine, curating a list in the "andExpect", minimizing our efforts
        // above, where we created our own mocked list of recipes.
    }

}