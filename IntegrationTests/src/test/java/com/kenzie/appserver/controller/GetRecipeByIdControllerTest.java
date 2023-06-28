package com.kenzie.appserver.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import org.springframework.web.server.ResponseStatusException;


import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class GetRecipeByIdControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    RecipeService recipeService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getRecipeById_validId_isValid() throws Exception {
        // GIVEN
        String title = mockNeat.regex("[A-Z][a-zA-Z0-9 ]*").supplier().get(); //Jess figured this out.
        String id = UUID.randomUUID().toString();
        String cuisine = mockNeat.regex("[A-Z][a-zA-Z0-9 ]*").supplier().get(); //Jess figured this out.
        String description = mockNeat.strings().valStr();
        String dietaryRestrictions = mockNeat.strings().valStr();
        boolean hasDietaryRestrictions = false;
        List<String> ingredients = Collections.singletonList(mockNeat.strings().valStr());
        String instructions = mockNeat.strings().valStr();

        // WHEN
        Recipe recipe = new Recipe(title, id, cuisine, description, dietaryRestrictions,
                hasDietaryRestrictions, ingredients,instructions);

        Recipe persistedRecipe = recipeService.addNewRecipe(recipe);
        mvc.perform(get("/recipes/{id}", persistedRecipe.getTitle())
                .accept(MediaType.APPLICATION_JSON))
                // THEN
                .andExpect(jsonPath("id")
                        .exists())
                .andExpect(status().isOk());

        Assertions.assertTrue(title.matches("[A-Z][a-zA-Z0-9 ]*"));
        Assertions.assertNotNull(title);
        Assertions.assertNotNull(cuisine);
        Assertions.assertTrue(cuisine.matches("[A-Z][a-zA-Z0-9 ]*"));
        Assertions.assertNotNull(description);
        Assertions.assertFalse(description.isEmpty());
        Assertions.assertTrue(description.length() < 250);
        Assertions.assertNotNull(dietaryRestrictions);
        Assertions.assertNotNull(instructions);
        Assertions.assertFalse(instructions.isEmpty());
    }

    @Test
    public void findRecipeById_noId_IsNull(){
        String recipeId = null;

        assertThrows(IllegalArgumentException.class,
                () -> recipeService.findRecipeByID(recipeId));

    }

}
