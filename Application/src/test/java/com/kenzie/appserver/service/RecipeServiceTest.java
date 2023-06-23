package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.model.Example;
import com.kenzie.appserver.service.model.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;

public class RecipeServiceTest {

    private RecipeRepository recipeRepository;
    private RecipeService recipeService;

    @BeforeEach
    void setup(){
        recipeRepository = mock(RecipeRepository.class);
        recipeService = new RecipeService(recipeRepository);

    }

    /** ------------------------------------------------------------------------
     *  RecipeService.findById
     *  ------------------------------------------------------------------------ **/

    @Test
    void findByID(){
        //GIVEN
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("ingredients");

        RecipeRecord record = new RecipeRecord();

        record.setTitle("Title");
        record.setId("1");
        record.setInstructions("instructions");
        record.setIngredients(stringList);
        record.setDescription("description");
        record.setCuisine("Cuisine");
        record.setHasDietaryRestrictions(true);
        record.setDietaryRestrictions("dietaryRestrictions");

        // WHEN
        when(recipeRepository.findById(record.getId())).thenReturn(Optional.of(record));
        Recipe recipe = recipeService.findRecipeByID(record.getId());

        // THEN
        Assertions.assertNotNull(recipe, "The object is returned");

        Assertions.assertEquals(record.getId(), recipe.getId(), "The id matches" );

        Assertions.assertEquals(record.getTitle(), recipe.getTitle(), "The title matches");
        Assertions.assertEquals(record.getIngredients(), recipe.getIngredients(), "The ingredients matches");
        Assertions.assertEquals(record.getInstructions(), recipe.getInstructions(), "The instructions matches");
        Assertions.assertEquals(record.getCuisine(), recipe.getCuisine(), "The cuisine matches");
        Assertions.assertEquals(record.getDescription(), recipe.getDescription(), "The description matches");
        Assertions.assertEquals(record.getDietaryRestrictions(), recipe.getDietaryRestrictions(), "The dietary restriction string matches");
        Assertions.assertEquals(record.isHasDietaryRestrictions(), recipe.isDietaryRestrictionsBool(), "The dietary restriction boolean matches");

    }

    @Test
    void findByConcertId_invalid() {
        // GIVEN
        String id = randomUUID().toString();

        when(recipeRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN
        Recipe recipe = recipeService.findRecipeByID(id);

        // THEN
        Assertions.assertNull(recipe, "The recipe is null when not found");
    }

    /** ------------------------------------------------------------------------
     *  RecipeService.addNewRecipe
     *  ------------------------------------------------------------------------ **/

    @Test
    void addNewRecipe(){
        // GIVEN

        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("ingredients");

        Recipe recipe = new Recipe("Title", "",

                                 "Cuisine","description",
                         "dietaryRestriction",true,
                                        stringList,"instructions");

       ArgumentCaptor<RecipeRecord> recipeRecordCaptor = ArgumentCaptor.forClass(RecipeRecord.class);

        // WHEN
        Recipe returnedRecipe = recipeService.addNewRecipe(recipe);

        // THEN
        Assertions.assertNotNull(returnedRecipe);

        verify(recipeRepository).save(recipeRecordCaptor.capture());

        RecipeRecord record = recipeRecordCaptor.getValue();

        Assertions.assertNotNull(recipe, "The object is returned");

        Assertions.assertEquals(record.getId(), returnedRecipe.getId(), "The id matches");

        Assertions.assertEquals(record.getTitle(), recipe.getTitle(), "The title matches");
        Assertions.assertEquals(record.getIngredients(), recipe.getIngredients(), "The ingredients matches");
        Assertions.assertEquals(record.getInstructions(), recipe.getInstructions(), "The instructions matches");
        Assertions.assertEquals(record.getCuisine(), recipe.getCuisine(), "The cuisine matches");
        Assertions.assertEquals(record.getDescription(), recipe.getDescription(), "The description matches");
        Assertions.assertEquals(record.getDietaryRestrictions(), recipe.getDietaryRestrictions(), "The dietary restriction string matches");
        Assertions.assertEquals(record.isHasDietaryRestrictions(), recipe.isDietaryRestrictionsBool(), "The dietary restriction boolean matches");



    }



}
