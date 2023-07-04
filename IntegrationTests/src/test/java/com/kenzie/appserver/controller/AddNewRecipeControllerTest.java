package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.RecipeCreateRequest;
import com.kenzie.appserver.repositories.model.Enums;
import com.kenzie.appserver.service.RecipeService;
import com.kenzie.appserver.service.model.Recipe;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@SpringBootTest
@AutoConfigureMockMvc
public class AddNewRecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RecipeService recipeService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    @Test
    public void addNewRecipe_createRecipe_Successful() throws Exception {
        // GIVEN
        List<String> ingredients = new ArrayList<>();
        ingredients.add("Ingredient 1 cup");
        ingredients.add("Ingredient 2 cups");
        Recipe recipe = new Recipe(
                UUID.randomUUID().toString(),
                "Sample Recipe",
                "Italian",
                "A delicious Italian dish",
                "Gluten Free",
                true,
                ingredients,
                "Step 1, Step 2, Step 3"
        );

        RecipeCreateRequest recipeCreateRequest = new RecipeCreateRequest();
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
                .andExpect(status().isCreated())
                .andExpect(jsonPath("title").value(recipe.getTitle()))
                .andExpect(jsonPath("cuisine").value(Enums.DietaryRestrictions.valueOf(recipe.getDietaryRestrictions())))
                .andExpect(jsonPath("description").value(recipe.getDescription()))
                .andExpect(jsonPath("dietaryRestrictions").value(Enums.DietaryRestrictions.valueOf(recipe.getDietaryRestrictions())))
                .andExpect(jsonPath("ingredients").isArray())
                .andExpect(jsonPath("instructions").value(recipe.getInstructions()));

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
    public void addNewRecipe_invalidInput_throwsIllegalArgumentException() {
        // GIVEN
        String title = "4invalId";
        String cuisine = "badInput";
        String description = mockNeat.strings().val();
        String dietaryRestrictions = mockNeat.strings().val();
        List<String> ingredients = Collections.singletonList(mockNeat.strings().val());
        String instructions = mockNeat.strings().val();

        // WHEN
        Recipe recipe = new Recipe(UUID.randomUUID().toString(), title, cuisine, description, dietaryRestrictions,
                false, ingredients, instructions);

        // THEN
        Assertions.assertThrows(IllegalArgumentException.class, () -> recipeService.createRecipe(recipe));
    }
}
