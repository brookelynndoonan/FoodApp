package com.kenzie.appserver.controller;

import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.service.RecipeService;
import com.kenzie.appserver.service.model.Recipe;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IntegrationTest
public class GetAllDietaryRestrictionsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeController recipeController;

    private final MockNeat mockNeat = MockNeat.threadLocal();



    @Test
    public void getAllDietaryRestrictions_isSuccessful() throws Exception {
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

        recipeService.getRecipesByDietaryRestrictions(String.valueOf(recipeOne));
        recipeService.getRecipesByDietaryRestrictions(String.valueOf(recipeTwo));
        recipeService.getRecipesByDietaryRestrictions(String.valueOf(recipeThree));

        recipeController.getRecipesByDietaryRestrictions("GLUTEN FREE");


        mvc.perform(get("/recipes/dietaryRestrictions/{dietaryRestrictions}", "Gluten Free")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }
}
