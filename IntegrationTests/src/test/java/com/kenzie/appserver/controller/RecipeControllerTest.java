package com.kenzie.appserver.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.service.RecipeService;
import com.kenzie.appserver.service.model.Recipe;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class RecipeControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    RecipeService recipeService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getRecipeById_validId_isValid() throws Exception {
        String title = mockNeat.strings().valStr();
        String id = UUID.randomUUID().toString();
        String cuisine = mockNeat.strings().valStr();
        String description = mockNeat.strings().valStr();
        String dietaryRestrictions = mockNeat.strings().valStr();
        boolean hasDietaryRestrictions = true;
        List<String> ingredients = Collections.singletonList(mockNeat.strings().valStr());
        String instructions = mockNeat.strings().valStr();



        Recipe recipe = new Recipe(title, id, cuisine, description, dietaryRestrictions,
                hasDietaryRestrictions, ingredients,instructions);

        Recipe persistedRecipe = recipeService.addNewRecipe(recipe);
        mvc.perform(get("/recipes/{id}", persistedRecipe.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("title")
                        .value(is(title)))
                .andExpect(jsonPath("id")
                        .value(is(id)))
                .andExpect(jsonPath("cuisine")
                        .value(is(cuisine)))
                .andExpect(jsonPath("description")
                        .value(is(description)))
                .andExpect(jsonPath("dietaryRestrictions")
                        .value(is(dietaryRestrictions)))
                .andExpect(jsonPath("dietaryRestrictionsBool")
                        .value(is(hasDietaryRestrictions)))
                .andExpect(jsonPath("ingredients")
                        .value(is(ingredients)))
                .andExpect(jsonPath("instructions")
                        .value(is(instructions)))
                .andExpect(status().isOk());


    }

}
