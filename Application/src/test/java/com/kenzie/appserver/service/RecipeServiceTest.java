package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.repositories.model.Enums.Cuisine;
import com.kenzie.appserver.repositories.model.Enums.DietaryRestrictions;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.model.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Test
    void findRecipeById() {
        // GIVEN
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("ingredients");

        RecipeRecord record = new RecipeRecord();
        record.setTitle("Title");
        record.setId("id");
        record.setInstructions("instructions");
        record.setIngredients(stringList);
        record.setDescription("description");
        record.setCuisine(Cuisine.AMERICAN);
        record.setHasDietaryRestrictions(true);
        record.setDietaryRestrictions(DietaryRestrictions.GLUTEN_FREE);

        // WHEN
        when(recipeRepository.findById(record.getId())).thenReturn(Optional.of(record));
        Recipe recipe = recipeService.getRecipeById(record.getId());

        // THEN
        Assertions.assertNotNull(recipe, "The object is returned");

        Assertions.assertEquals(record.getId(), recipe.getId(), "The id matches");

        Assertions.assertEquals(record.getTitle(), recipe.getTitle(), "The title matches");
        Assertions.assertEquals(record.getIngredients(), recipe.getIngredients(), "The ingredients matches");
        Assertions.assertEquals(record.getInstructions(), recipe.getInstructions(), "The instructions matches");
        Assertions.assertEquals(record.getCuisine().toString(), recipe.getCuisine(), "The cuisine matches");
        Assertions.assertEquals(record.getDescription(), recipe.getDescription(), "The description matches");
        Assertions.assertEquals(record.getDietaryRestrictions().toString(), recipe.getDietaryRestrictions(), "The dietary restriction string matches");
        Assertions.assertEquals(record.hasDietaryRestrictions(), recipe.hasDietaryRestrictions(), "The dietary restriction boolean matches");
    }

    @Test
    void findRecipeById_invalid() {
        // GIVEN
        String id = randomUUID().toString();

        when(recipeRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN
        Recipe recipe = recipeService.getRecipeById(id);

        // THEN
        Assertions.assertNull(recipe, "The recipe is null when not found");
    }

    @Test
    void addNewRecipe() {
        // GIVEN
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("ingredients");

        Recipe recipe = new Recipe(
                "Title",
                "",
                Cuisine.AMERICAN.toString(),
                "description",
                DietaryRestrictions.GLUTEN_FREE.toString(),
                true,
                stringList,
                "instructions");

        ArgumentCaptor<RecipeRecord> recipeRecordCaptor = ArgumentCaptor.forClass(RecipeRecord.class);

        // WHEN
        Recipe returnedRecipe = recipeService.createRecipe(recipe);

        // THEN
        Assertions.assertNotNull(returnedRecipe);

        verify(recipeRepository).save(recipeRecordCaptor.capture());

        RecipeRecord record = recipeRecordCaptor.getValue();

        Assertions.assertNotNull(recipe, "The object is returned");

        Assertions.assertEquals(record.getId(), returnedRecipe.getId(), "The id matches");

        Assertions.assertEquals(record.getTitle(), recipe.getTitle(), "The title matches");
        Assertions.assertEquals(record.getIngredients(), recipe.getIngredients(), "The ingredients matches");
        Assertions.assertEquals(record.getInstructions(), recipe.getInstructions(), "The instructions matches");
        Assertions.assertEquals(record.getCuisine().toString(), recipe.getCuisine(), "The cuisine matches");
        Assertions.assertEquals(record.getDescription(), recipe.getDescription(), "The description matches");
        Assertions.assertEquals(record.getDietaryRestrictions().toString(), recipe.getDietaryRestrictions(), "The dietary restriction string matches");
        Assertions.assertEquals(record.hasDietaryRestrictions(), recipe.hasDietaryRestrictions(), "The dietary restriction boolean matches");
    }

    @Test
    void findAllByCuisine() {
        // GIVEN
        Cuisine cuisine1 = Cuisine.AMERICAN;
        Cuisine cuisine2 = Cuisine.ITALIAN;

        RecipeRecord recipeRecord1 = new RecipeRecord();
        RecipeRecord recipeRecord2 = new RecipeRecord();
        RecipeRecord recipeRecord3 = new RecipeRecord();

        // Set values for recipeRecord1
        recipeRecord1.setTitle("Title");
        recipeRecord1.setId(UUID.randomUUID().toString());
        recipeRecord1.setInstructions("instructions");
        recipeRecord1.setIngredients(new ArrayList<>().add());
        recipeRecord1.setDescription("description");
        recipeRecord1.setCuisine(cuisine1);
        recipeRecord1.setHasDietaryRestrictions(true);
        recipeRecord1.setDietaryRestrictions(DietaryRestrictions.NONE);

        // Set values for recipeRecord2
        recipeRecord2.setTitle("Title");
        recipeRecord2.setId("id");
        recipeRecord2.setInstructions("instructions");
        recipeRecord2.setIngredients(new ArrayList<>());
        recipeRecord2.setDescription("description");
        recipeRecord2.setCuisine(cuisine2);
        recipeRecord2.setHasDietaryRestrictions(true);
        recipeRecord2.setDietaryRestrictions(DietaryRestrictions.NONE);

        // Set values for recipeRecord3
        recipeRecord3.setTitle("Title");
        recipeRecord3.setId("id");
        recipeRecord3.setInstructions("instructions");
        recipeRecord3.setIngredients(new ArrayList<>());
        recipeRecord3.setDescription("description");
        recipeRecord3.setCuisine(cuisine2);
        recipeRecord3.setHasDietaryRestrictions(true);
        recipeRecord3.setDietaryRestrictions(DietaryRestrictions.NONE);

        List<RecipeRecord> recipeRecordList = new ArrayList<>();
        recipeRecordList.add(recipeRecord1);
        recipeRecordList.add(recipeRecord2);
        recipeRecordList.add(recipeRecord3);

        when(recipeRepository.findAll()).thenReturn(recipeRecordList);

        // WHEN
        List<Recipe> recipeList1 = recipeService.getRecipesByCuisine(cuisine1);
        List<Recipe> recipeList2 = recipeService.getRecipesByCuisine(cuisine2);

        // THEN
        Assertions.assertNotNull(recipeList1, "List 1 should not be null");
        Assertions.assertNotNull(recipeList2, "List 2 should not be null");
        Assertions.assertNotEquals(recipeList1, recipeList2, "Lists should not be equal");
        Assertions.assertEquals(1, recipeList1.size(), "List should have 1 element");
        Assertions.assertEquals(2, recipeList2.size(), "List should have 2 elements");
    }

    @Test
    void findAllByDietaryRestriction() {
        // GIVEN
        DietaryRestrictions dietaryRestriction1 = DietaryRestrictions.GLUTEN_FREE;
        DietaryRestrictions dietaryRestriction2 = DietaryRestrictions.VEGAN;

        RecipeRecord recipeRecord1 = new RecipeRecord();
        RecipeRecord recipeRecord2 = new RecipeRecord();
        RecipeRecord recipeRecord3 = new RecipeRecord();

        // Set values for recipeRecord1
        recipeRecord1.setTitle("Title");
        recipeRecord1.setId("id");
        recipeRecord1.setInstructions("instructions");
        recipeRecord1.setIngredients(new ArrayList<>());
        recipeRecord1.setDescription("description");
        recipeRecord1.setCuisine(Cuisine.AMERICAN);
        recipeRecord1.setHasDietaryRestrictions(true);
        recipeRecord1.setDietaryRestrictions(dietaryRestriction1);

        // Set values for recipeRecord2
        recipeRecord2.setTitle("Title");
        recipeRecord2.setId("id");
        recipeRecord2.setInstructions("instructions");
        recipeRecord2.setIngredients(new ArrayList<>());
        recipeRecord2.setDescription("description");
        recipeRecord2.setCuisine(Cuisine.ITALIAN);
        recipeRecord2.setHasDietaryRestrictions(true);
        recipeRecord2.setDietaryRestrictions(dietaryRestriction2);

        // Set values for recipeRecord3
        recipeRecord3.setTitle("Title");
        recipeRecord3.setId("id");
        recipeRecord3.setInstructions("instructions");
        recipeRecord3.setIngredients(new ArrayList<>());
        recipeRecord3.setDescription("description");
        recipeRecord3.setCuisine(Cuisine.ITALIAN);
        recipeRecord3.setHasDietaryRestrictions(true);
        recipeRecord3.setDietaryRestrictions(dietaryRestriction2);

        List<RecipeRecord> recipeRecordList = new ArrayList<>();
        recipeRecordList.add(recipeRecord1);
        recipeRecordList.add(recipeRecord2);
        recipeRecordList.add(recipeRecord3);

        when(recipeRepository.findAll()).thenReturn(recipeRecordList);

        // WHEN
        List<Recipe> recipeList1 = recipeService.getRecipesByDietaryRestrictions(dietaryRestriction1);
        List<Recipe> recipeList2 = recipeService.getRecipesByDietaryRestrictions(dietaryRestriction2);

        // THEN
        Assertions.assertNotNull(recipeList1, "List 1 should not be null");
        Assertions.assertNotNull(recipeList2, "List 2 should not be null");
        Assertions.assertNotEquals(recipeList1, recipeList2, "Lists should not be equal");
        Assertions.assertEquals(1, recipeList1.size(), "List should have 1 element");
        Assertions.assertEquals(2, recipeList2.size(), "List should have 2 elements");
    }


    private Recipe convertToRecipe(RecipeRecord recipeRecord) {
        return new Recipe(
                recipeRecord.getTitle(),
                recipeRecord.getId(),
                recipeRecord.getCuisine().toString(),
                recipeRecord.getDescription(),
                recipeRecord.getDietaryRestrictions().toString(),
                recipeRecord.hasDietaryRestrictions(),
                recipeRecord.getIngredients(),
                recipeRecord.getInstructions()
        );
    }

    private RecipeRecord convertToRecipeRecord(Recipe recipe) {
        RecipeRecord recipeRecord = new RecipeRecord();
        recipeRecord.setTitle(recipe.getTitle());
        recipeRecord.setId(recipe.getId());
        recipeRecord.setCuisine(Cuisine.valueOf(recipe.getCuisine()));
        recipeRecord.setDescription(recipe.getDescription());
        recipeRecord.setDietaryRestrictions(DietaryRestrictions.valueOf(recipe.getDietaryRestrictions()));
        recipeRecord.setHasDietaryRestrictions(recipe.hasDietaryRestrictions());
        recipeRecord.setIngredients(recipe.getIngredients());
        recipeRecord.setInstructions(recipe.getInstructions());
        return recipeRecord;
    }
}
