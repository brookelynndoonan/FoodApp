package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.RecipeCreateRequest;
import com.kenzie.appserver.service.RecipeService;
import com.kenzie.appserver.service.model.Recipe;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IntegrationTest
public class AddNewRecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecipeService recipeService;


    @Autowired
    private ObjectMapper objectMapper;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    @Test
    public void addNewRecipe_createRecipe_Successful() throws Exception {
        // GIVEN
        List<String> ingredients = new ArrayList<>();
        ingredients.add("Ingredient 1 cup");
        ingredients.add("Ingredient 2 cups");
        Recipe recipe = new Recipe(
                "Sample Recipe",
                "Italian",
                "A delicious Italian dish",
                "Gluten Free",
                true,
                ingredients,
                "Step 1, Step 2, Step 3");

        RecipeCreateRequest recipeCreateRequest = new RecipeCreateRequest();
        recipeCreateRequest.setId(recipe.getId());
        recipeCreateRequest.setTitle(recipe.getTitle());
        recipeCreateRequest.setCuisine(recipe.getCuisine().toUpperCase().replace(" ", "_"));
        recipeCreateRequest.setDescription(recipe.getDescription());
        recipeCreateRequest.setDietaryRestrictions(recipe.getDietaryRestrictions().toUpperCase().replace(" ", "_"));
        recipeCreateRequest.setIngredients(ingredients);
        recipeCreateRequest.setInstructions(recipe.getInstructions());

        // WHEN
        mockMvc.perform(post("/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipeCreateRequest)))
                .andExpect(jsonPath("id")
                        .exists())
                .andExpect(jsonPath("title")
                        .value(recipeCreateRequest.getTitle()))
                .andExpect(jsonPath("cuisine")
                        .value(recipeCreateRequest.getCuisine()))
                .andExpect(jsonPath("description")
                        .value(recipeCreateRequest.getDescription()))
                .andExpect(jsonPath("dietaryRestrictions")
                        .value(recipeCreateRequest.getDietaryRestrictions()))
                .andExpect(jsonPath("ingredients")
                        .value(recipeCreateRequest.getIngredients()))
                .andExpect(jsonPath("instructions")
                        .value(recipeCreateRequest.getInstructions()))
                .andExpect(status().isCreated());

        // THEN
        Assertions.assertNotNull(recipeCreateRequest.getTitle());
        Assertions.assertNotNull(recipeCreateRequest.getCuisine());
        Assertions.assertNotNull(recipeCreateRequest.getDescription());
        Assertions.assertNotNull(recipeCreateRequest.getDietaryRestrictions());
        Assertions.assertNotNull(recipeCreateRequest.getIngredients());
        Assertions.assertNotNull(recipeCreateRequest.getInstructions());
        Assertions.assertNotNull(recipeCreateRequest.getHasDietaryRestrictions());
        Assertions.assertEquals(Boolean.FALSE, recipeCreateRequest.getHasDietaryRestrictions());


    }

}