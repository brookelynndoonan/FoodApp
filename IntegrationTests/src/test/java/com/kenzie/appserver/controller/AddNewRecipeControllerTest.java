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

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class AddNewRecipeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    RecipeService recipeService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();


    @Test
    public void addNewRecipe_createRecipe_Successful() throws Exception {
        String title = "Sample Recipe"; //Jess figured this out.
        String id = UUID.randomUUID().toString();
        String cuisine = mockNeat.regex("[A-Z][a-zA-Z0-9 ]*").supplier().get(); //Jess figured this out.
        String description = mockNeat.strings().valStr();
        String dietaryRestrictions = mockNeat.strings().valStr();
        List<String> ingredients = Collections.singletonList(mockNeat.strings().valStr());
        String instructions = mockNeat.strings().valStr();


        RecipeCreateRequest recipeCreateRequest = new RecipeCreateRequest();
        recipeCreateRequest.setTitle(title);
        recipeCreateRequest.setId(id);
        recipeCreateRequest.setDietaryRestrictionsBool(false);
        recipeCreateRequest.setCuisine(cuisine);
        recipeCreateRequest.setDescription(description);
        recipeCreateRequest.setDietaryRestrictions(dietaryRestrictions);
        recipeCreateRequest.setInstructions(instructions);
        recipeCreateRequest.setIngredients(ingredients);



        mapper.registerModule(new JavaTimeModule());

        mvc.perform(post("/recipes")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(recipeCreateRequest)))
                .andExpect(jsonPath("id")
                        .exists())
                .andExpect(jsonPath("title")
                        .value(recipeCreateRequest.getTitle()))
                .andExpect(status().isCreated());

        Assertions.assertTrue(recipeCreateRequest.getTitle().matches("[A-Z][a-zA-Z0-9 ]*"));
        Assertions.assertNotNull(recipeCreateRequest.getTitle());
        Assertions.assertNotNull(recipeCreateRequest.getCuisine());
        Assertions.assertTrue(recipeCreateRequest.getCuisine().matches("[A-Z][a-zA-Z0-9 ]*"));
        Assertions.assertNotNull(recipeCreateRequest.getDescription());
        Assertions.assertFalse(recipeCreateRequest.getDescription().isEmpty());
        Assertions.assertTrue(recipeCreateRequest.getDescription().length() < 250);
        Assertions.assertNotNull(recipeCreateRequest.getDietaryRestrictions());
        Assertions.assertNotNull(recipeCreateRequest.getInstructions());
        Assertions.assertFalse(recipeCreateRequest.getInstructions()
                .isEmpty());

    }

    @Test
    public void addNewRecipe_invalidInput_throwsIllegalArgumentException(){

        String title = "4invalId";
        String id = UUID.randomUUID().toString();
        String cuisine = "badInput";
        String description = mockNeat.strings().valStr();
        String dietaryRestrictions = mockNeat.strings().valStr();
        boolean hasDietaryRestrictions = false;
        List<String> ingredients = Collections.singletonList(mockNeat.strings().valStr());
        String instructions = mockNeat.strings().valStr();

        // WHEN
        Recipe recipe = new Recipe(title, id, cuisine, description, dietaryRestrictions,
                hasDietaryRestrictions, ingredients,instructions);


        assertThrows(IllegalArgumentException.class,
                () -> recipeService.createRecipe(recipe));

    }

    @Test
    public void addNewRecipe_titleNull_throwsIllegalArgumentException(){

        String title = null;
        String id = UUID.randomUUID().toString();
        String cuisine = "Cuisine";
        String description = mockNeat.strings().valStr();
        String dietaryRestrictions = mockNeat.strings().valStr();
        boolean hasDietaryRestrictions = false;
        List<String> ingredients = Collections.singletonList(mockNeat.strings().valStr());
        String instructions = mockNeat.strings().valStr();

        // WHEN
        Recipe recipe = new Recipe(id, title, cuisine, description, dietaryRestrictions,
                hasDietaryRestrictions, ingredients,instructions);

        Assertions.assertNull(recipe.getTitle());

        assertThrows(IllegalArgumentException.class,
                () -> recipeService.createRecipe(recipe));
    }

    @Test
    public void addNewRecipe_cuisineNull_throwsIllegalArgumentException(){

        String title = "Title";
        String id = UUID.randomUUID().toString();
        String cuisine = null;
        String description = mockNeat.strings().valStr();
        String dietaryRestrictions = mockNeat.strings().valStr();
        boolean hasDietaryRestrictions = false;
        List<String> ingredients = Collections.singletonList(mockNeat.strings().valStr());
        String instructions = mockNeat.strings().valStr();

        // WHEN
        Recipe recipe = new Recipe(title, id, cuisine, description, dietaryRestrictions,
                hasDietaryRestrictions, ingredients,instructions);

        Assertions.assertNull(recipe.getCuisine());

        assertThrows(IllegalArgumentException.class,
                () -> recipeService.createRecipe(recipe));
    }

    @Test
    public void addNewRecipe_descriptionNull_throwsIllegalArgumentException(){

        String title = "Title";
        String id = UUID.randomUUID().toString();
        String cuisine = "Cuisine";
        String description = null;
        String dietaryRestrictions = mockNeat.strings().valStr();
        boolean hasDietaryRestrictions = false;
        List<String> ingredients = Collections.singletonList(mockNeat.strings().valStr());
        String instructions = mockNeat.strings().valStr();

        // WHEN
        Recipe recipe = new Recipe(title, id, cuisine, description, dietaryRestrictions,
                hasDietaryRestrictions, ingredients,instructions);

        Assertions.assertNull(recipe.getDescription());

        assertThrows(IllegalArgumentException.class,
                () -> recipeService.createRecipe(recipe));
    }

    @Test
    public void addNewRecipe_descriptionLongerThan250Char_throwsIllegalArgumentException(){

        String title = "Title";
        String id = UUID.randomUUID().toString();
        String cuisine = "Cuisine";
        String description = "\"This description is longer than 250 characters.\" +\n" +
                "                \" Since it is longer than 250 characters, it will throw an IllegalArgumentException.\" +\n" +
                "                \" Because of that, the user will have to shorten their description with less words, in order\" +\n" +
                "                \" for the recipe to be added to the database. This description is longer than 250 characters.\";";
        String dietaryRestrictions = mockNeat.strings().valStr();
        boolean hasDietaryRestrictions = false;
        List<String> ingredients = Collections.singletonList(mockNeat.strings().valStr());
        String instructions = mockNeat.strings().valStr();

        // WHEN
        Recipe recipe = new Recipe(title, id, cuisine, description, dietaryRestrictions,
                hasDietaryRestrictions, ingredients,instructions);


        Assertions.assertTrue(recipe.getDescription().length() > 250);

        assertThrows(IllegalArgumentException.class,
                () -> recipeService.createRecipe(recipe));
    }

    @Test
    public void addNewRecipe_dietaryRestrictionsNull_throwsIllegalArgumentException(){

        String title = "Title";
        String id = UUID.randomUUID().toString();
        String cuisine = "Cuisine";
        String description = mockNeat.strings().valStr();
        String dietaryRestrictions = null;
        boolean hasDietaryRestrictions = false;
        List<String> ingredients = Collections.singletonList(mockNeat.strings().valStr());
        String instructions = mockNeat.strings().valStr();

        // WHEN
        Recipe recipe = new Recipe(title, id, cuisine, description, dietaryRestrictions,
                hasDietaryRestrictions, ingredients,instructions);

        Assertions.assertNull(recipe.getDietaryRestrictions());

        assertThrows(IllegalArgumentException.class,
                () -> recipeService.createRecipe(recipe));
    }

    @Test
    public void addNewRecipe_instructionsNull_throwsIllegalArgumentException(){

        String title = "Title";
        String id = UUID.randomUUID().toString();
        String cuisine = "Cuisine";
        String description = mockNeat.strings().valStr();
        String dietaryRestrictions = mockNeat.strings().valStr();
        boolean hasDietaryRestrictions = false;
        List<String> ingredients = Collections.singletonList(mockNeat.strings().valStr());
        String instructions = null;

        // WHEN
        Recipe recipe = new Recipe(title, id, cuisine, description, dietaryRestrictions,
                hasDietaryRestrictions, ingredients,instructions);

        Assertions.assertNull(recipe.getInstructions());

        assertThrows(IllegalArgumentException.class,
                () -> recipeService.createRecipe(recipe));
    }



}
