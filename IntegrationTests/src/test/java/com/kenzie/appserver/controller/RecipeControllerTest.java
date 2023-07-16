/*
package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.RecipeCreateRequest;
import com.kenzie.appserver.service.RecipeService;
import com.kenzie.appserver.service.model.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IntegrationTest
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    public void getRecipeById_Successful() throws Exception {
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

        String id = recipe.getId();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/recipes/id/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipeCreateRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(id))
                .andReturn();
        // Add more assertions as needed for the response body

    }

    @Test
    public void getRecipesByCuisine_Successful() throws Exception {
        String cuisine = "ITALIAN";

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/recipes/cuisine/{cuisine}", cuisine)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cuisine").value(cuisine))
                .andReturn();
        // Add more assertions as needed for the response body
    }

    @Test
    public void getRecipesByDietaryRestrictions_Successful() throws Exception {
        String dietaryRestriction = "VEGAN";

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/recipes/dietaryrestrictions/{dietaryrestrictions}", dietaryRestriction)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cuisine").value(dietaryRestriction))
                .andReturn();
        // Add more assertions as needed for the response body
    }

}*/
