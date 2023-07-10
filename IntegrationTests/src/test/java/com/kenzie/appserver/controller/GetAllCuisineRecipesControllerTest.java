package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.service.RecipeService;
import com.kenzie.appserver.service.model.Recipe;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IntegrationTest
public class GetAllCuisineRecipesControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeController recipeController;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();


    //TODO -confirm this test is built correctly.
    //TODO -Will have to adjust to enums in future. Possibly?
    @Test
    public void getAllCuisineRecipes_isSuccessful() throws Exception {
        String title = mockNeat.strings().valStr();
        String id = UUID.randomUUID().toString();
        String cuisine = mockNeat.strings().valStr();
        String description = mockNeat.strings().valStr();
        String dietaryRestrictions = mockNeat.strings().valStr();
        boolean hasDietaryRestrictions = false;
        List<String> ingredients = Collections.singletonList(mockNeat.strings().valStr());
        String instructions = mockNeat.strings().valStr();

        Recipe recipe = new Recipe(title, id, cuisine, description, dietaryRestrictions,
                hasDietaryRestrictions, ingredients,instructions);

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

        //RecipeController recipeCuisineOne = recipeService.getRecipesByCuisine(String.valueOf(recipeOne));
        Recipe recipeCuisineTwo = (Recipe) recipeService.getRecipesByCuisine(String.valueOf(recipeTwo));

        List<Recipe> recipeList = new ArrayList<>();
       // recipeList.add(recipeCuisineOne);
        recipeList.add(recipeCuisineTwo);
        // WHEN
        Recipe persistedRecipe = recipeService.addNewRecipe(recipe);

        ResultActions actions = mvc.perform(get("/cuisine/{cuisine}", persistedRecipe.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
               /* .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("cuisine").value("Cuisine"))*/;

        mapper.registerModule(new JavaTimeModule());
        // THEN
        //String responseBody = actions.andReturn().getResponse().getContentAsString();
        //List<RecipeResponse> listCuisineRecipes = mapper.readValue(responseBody, new TypeReference<List<RecipeResponse>>(){});
        assertThat(recipeList.size()).isEqualTo(2);
    }
}
