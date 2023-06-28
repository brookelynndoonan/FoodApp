package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.model.Recipe;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;

public class RecipeServiceTest {

    private RecipeRepository recipeRepository;
    private RecipeService recipeService;

    @BeforeEach
    void setup() {
        recipeRepository = mock(RecipeRepository.class);
        recipeService = new RecipeService(recipeRepository);

    }

    /**
     * ------------------------------------------------------------------------
     * RecipeService.findById
     * ------------------------------------------------------------------------
     **/

    @Test
    void findByID() {
        //GIVEN
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("ingredients");

        RecipeRecord record = new RecipeRecord();

        record.setTitle("Title");
        record.setId("id");
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

        Assertions.assertEquals(record.getId(), recipe.getId(), "The id matches");

        Assertions.assertEquals(record.getTitle(), recipe.getTitle(), "The title matches");
        Assertions.assertEquals(record.getIngredients(), recipe.getIngredients(), "The ingredients matches");
        Assertions.assertEquals(record.getInstructions(), recipe.getInstructions(), "The instructions matches");
        Assertions.assertEquals(record.getCuisine(), recipe.getCuisine(), "The cuisine matches");
        Assertions.assertEquals(record.getDescription(), recipe.getDescription(), "The description matches");
        Assertions.assertEquals(record.getDietaryRestrictions(), recipe.getDietaryRestrictions(), "The dietary restriction string matches");
        Assertions.assertEquals(record.HasDietaryRestrictions(), recipe.HasDietaryRestrictions(), "The dietary restriction boolean matches");

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

    /**
     * ------------------------------------------------------------------------
     * RecipeService.addNewRecipe
     * ------------------------------------------------------------------------
     **/

    @Test
    void addNewRecipe() {
        // GIVEN

        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("ingredients");

        Recipe recipe = new Recipe(
                "Title",
                "",
                "Cuisine",
                "description",
                "dietaryRestriction",
                true,
                stringList,
                "instructions");

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
        Assertions.assertEquals(record.HasDietaryRestrictions(), recipe.HasDietaryRestrictions(), "The dietary restriction boolean matches");
    }

    /**
     * ------------------------------------------------------------------------
     * RecipeService.findById
     * ------------------------------------------------------------------------
     **/

    @Test
    void findAllByCuisine(){
        //GIVEN

        String cuisine1 = "Cuisine1";
        String cuisine2 = "Cuisine2";

        RecipeRecord recipeRecord1 = new RecipeRecord();
        RecipeRecord recipeRecord2 = new RecipeRecord();
        RecipeRecord recipeRecord3 = new RecipeRecord();

        recipeRecord1 = recordCreationHelper(recipeRecord1,cuisine1,true);
        recipeRecord2 = recordCreationHelper(recipeRecord2,cuisine2,true);
        recipeRecord3 = recordCreationHelper(recipeRecord3,cuisine2,true);

        List<RecipeRecord> recipeRecordList = new ArrayList<>();
        recipeRecordList.add(recipeRecord1);
        recipeRecordList.add(recipeRecord2);
        recipeRecordList.add(recipeRecord3);

        when(recipeRepository.findAll()).thenReturn(recipeRecordList);

        //WHEN

        List<Recipe> recipeList1 = recipeService.findAllCuisine(cuisine1);
        List<Recipe> recipeList2 = recipeService.findAllCuisine(cuisine2);

        //THEN

        Assertions.assertNotNull(recipeList1,"List 1 should not be null");
        Assertions.assertNotNull(recipeList2,"List 2 should not be null");
        Assertions.assertNotEquals(recipeList1,recipeList2, "Lists should not be equal");
        Assertions.assertEquals(recipeList1.size(),1, "List should have 1 element");
        Assertions.assertEquals(recipeList2.size(),2,"List should have 2 elements");

    }

    @Test
    void findAllByDietaryRestriction(){
        //GIVEN

        String dietaryRestriction1 = "DietaryRestriction1";
        String dietaryRestriction2 = "DietaryRestriction2";

        RecipeRecord recipeRecord1 = new RecipeRecord();
        RecipeRecord recipeRecord2 = new RecipeRecord();
        RecipeRecord recipeRecord3 = new RecipeRecord();

        recipeRecord1 = recordCreationHelper(recipeRecord1,dietaryRestriction1,false);
        recipeRecord2 = recordCreationHelper(recipeRecord2,dietaryRestriction2,false);
        recipeRecord3 = recordCreationHelper(recipeRecord3,dietaryRestriction2,false);

        List<RecipeRecord> recipeRecordList = new ArrayList<>();
        recipeRecordList.add(recipeRecord1);
        recipeRecordList.add(recipeRecord2);
        recipeRecordList.add(recipeRecord3);

        when(recipeRepository.findAll()).thenReturn(recipeRecordList);

        //WHEN

        List<Recipe> recipeList1 = recipeService.findAllDietaryRestriction(dietaryRestriction1);
        List<Recipe> recipeList2 = recipeService.findAllDietaryRestriction(dietaryRestriction2);

        //THEN

        Assertions.assertNotNull(recipeList1,"List 1 should not be null");
        Assertions.assertNotNull(recipeList2,"List 2 should not be null");
        Assertions.assertNotEquals(recipeList1,recipeList2, "Lists should not be equal");
        Assertions.assertEquals(recipeList1.size(),1, "List should have 1 element");
        Assertions.assertEquals(recipeList2.size(),2,"List should have 2 elements");


    }

    @Ignore
    RecipeRecord recordCreationHelper (RecipeRecord record,String cuisineOrDietary, boolean isCuisine){

        if(isCuisine) {
            record.setTitle("Title");
            record.setId("id");
            record.setInstructions("instructions");
            record.setIngredients(new ArrayList<>());
            record.setDescription("description");
            record.setCuisine(cuisineOrDietary);
            record.setHasDietaryRestrictions(true);
            record.setDietaryRestrictions("dietaryRestrictions");
        }
        else{
            record.setTitle("Title");
            record.setId("id");
            record.setInstructions("instructions");
            record.setIngredients(new ArrayList<>());
            record.setDescription("description");
            record.setCuisine("Cuisine");
            record.setHasDietaryRestrictions(true);
            record.setDietaryRestrictions(cuisineOrDietary);
        }
        return record;

    }
}
