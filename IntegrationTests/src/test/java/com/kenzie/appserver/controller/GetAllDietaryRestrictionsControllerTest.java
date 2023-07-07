package com.kenzie.appserver.controller;

import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.RecipeResponse;
import com.kenzie.appserver.service.RecipeService;
import com.kenzie.appserver.service.model.Recipe;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
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

    private final MockNeat mockNeat = MockNeat.threadLocal();


    //TODO -confirm this test is built correctly.
    //TODO -Will have to adjust to enums in future. Possibly?

    @Test
    public void getAllDietaryRestrictions_isSuccessful() throws Exception {
        String title = mockNeat.regex("[A-Z][a-zA-Z0-9 ]*").supplier().get();
        String id = UUID.randomUUID().toString();
        String cuisine = mockNeat.regex("[A-Z][a-zA-Z0-9 ]*").supplier().get();
        String description = mockNeat.strings().valStr();
        String dietaryRestrictions = mockNeat.strings().valStr();
        List<String> ingredients = Collections.singletonList(mockNeat.strings().valStr());
        String instructions = mockNeat.strings().valStr();
        String dietaryRestrictionForServiceCall = mockNeat.strings().valStr();


        RecipeResponse recipeOne = new RecipeResponse();
        recipeOne.setTitle(title);
        recipeOne.setId(id);
        recipeOne.setCuisine(cuisine);
        recipeOne.setDescription(description);
        recipeOne.setDietaryRestrictions(dietaryRestrictions);
        recipeOne.setHasDietaryRestrictions(false);
        recipeOne.setIngredients(ingredients);
        recipeOne.setInstructions(instructions);

        RecipeResponse recipeTwo = new RecipeResponse();
        recipeTwo.setTitle(title);
        recipeTwo.setId(id);
        recipeTwo.setCuisine(cuisine);
        recipeTwo.setDescription(description);
        recipeTwo.setDietaryRestrictions(dietaryRestrictions);
        recipeTwo.setHasDietaryRestrictions(false);
        recipeTwo.setIngredients(ingredients);
        recipeTwo.setInstructions(instructions);

        List<RecipeResponse> recipeResponseList = new ArrayList<>();
        recipeResponseList.add(recipeOne);
        recipeResponseList.add(recipeTwo);

        List<Recipe> dietaryRecipeList = recipeService.getRecipesByDietaryRestrictions(
                dietaryRestrictionForServiceCall);
        mvc.perform(get("/recipes/dietaryRestrictions/{dietaryRestrictions}", dietaryRecipeList)
                        .accept(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(status().is2xxSuccessful());

        Assertions.assertEquals(2, recipeResponseList.size());

        Assertions.assertTrue(recipeOne.getTitle().matches("[A-Z][a-zA-Z0-9 ]*"));
        Assertions.assertNotNull(recipeOne.getTitle());
        Assertions.assertNotNull(recipeOne.getCuisine());
        Assertions.assertTrue(recipeOne.getCuisine().matches("[A-Z][a-zA-Z0-9 ]*"));
        Assertions.assertNotNull(recipeOne.getDescription());
        Assertions.assertFalse(recipeOne.getDescription().isEmpty());
        Assertions.assertTrue(recipeOne.getDescription().length() < 250);
        Assertions.assertNotNull(recipeOne.getDietaryRestrictions());
        Assertions.assertNotNull(recipeOne.getInstructions());
        Assertions.assertFalse(recipeOne.getInstructions()
                .isEmpty());

        Assertions.assertTrue(recipeTwo.getTitle().matches("[A-Z][a-zA-Z0-9 ]*"));
        Assertions.assertNotNull(recipeTwo.getTitle());
        Assertions.assertNotNull(recipeTwo.getCuisine());
        Assertions.assertTrue(recipeTwo.getCuisine().matches("[A-Z][a-zA-Z0-9 ]*"));
        Assertions.assertNotNull(recipeTwo.getDescription());
        Assertions.assertFalse(recipeTwo.getDescription().isEmpty());
        Assertions.assertTrue(recipeTwo.getDescription().length() < 250);
        Assertions.assertNotNull(recipeTwo.getDietaryRestrictions());
        Assertions.assertNotNull(recipeTwo.getInstructions());
        Assertions.assertFalse(recipeTwo.getInstructions()
                .isEmpty());

    }
}
