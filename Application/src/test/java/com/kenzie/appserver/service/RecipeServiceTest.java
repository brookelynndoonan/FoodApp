package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.repositories.model.Enums;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    private RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeService(recipeRepository);
    }

    @Test
    void testGetAllRecipes() {
        List<RecipeRecord> recipeRecords = createRecipeRecords();
        when(recipeRepository.findAll()).thenReturn(recipeRecords);

        List<Recipe> recipes = recipeService.getAllRecipes();

        assertEquals(recipeRecords.size(), recipes.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void testCreateRecipe() {
        Recipe recipe = createRecipe();
        RecipeRecord recipeRecord = createRecipeRecord();
        when(recipeRepository.save(any(RecipeRecord.class))).thenReturn(recipeRecord);

        Recipe createdRecipe = recipeService.createRecipe(recipe);

        assertEquals(recipe.getTitle(), createdRecipe.getTitle());
        assertEquals(recipe.getCuisine(), createdRecipe.getCuisine());
        assertEquals(recipe.getDescription(), createdRecipe.getDescription());
        assertEquals(recipe.getDietaryRestrictions(), createdRecipe.getDietaryRestrictions());
        assertEquals(recipe.hasDietaryRestrictions(), createdRecipe.hasDietaryRestrictions());
        assertEquals(recipe.getIngredients(), createdRecipe.getIngredients());
        assertEquals(recipe.getInstructions(), createdRecipe.getInstructions());
        verify(recipeRepository, times(1)).save(any(RecipeRecord.class));
    }

    @Test
    void testGetRecipesByCuisine() {
        List<RecipeRecord> recipeRecords = createRecipeRecords();
        when(recipeRepository.findByCuisine(any(Enums.Cuisine.class))).thenReturn(recipeRecords);

        List<Recipe> recipes = recipeService.getRecipesByCuisine("ITALIAN");

        assertEquals(recipeRecords.size(), recipes.size());
        verify(recipeRepository, times(1)).findByCuisine(any(Enums.Cuisine.class));
    }

    @Test
    void testGetRecipesByDietaryRestrictions() {
        List<RecipeRecord> recipeRecords = createRecipeRecords();
        when(recipeRepository.findByDietaryRestrictions(any(Enums.DietaryRestrictions.class))).thenReturn(recipeRecords);

        List<Recipe> recipes = recipeService.getRecipesByDietaryRestrictions("GLUTEN_FREE");

        assertEquals(recipeRecords.size(), recipes.size());
        verify(recipeRepository, times(1)).findByDietaryRestrictions(any(Enums.DietaryRestrictions.class));
    }

    @Test
    void testGetRecipeById() {
        RecipeRecord recipeRecord = createRecipeRecord();
        when(recipeRepository.findById(anyString())).thenReturn(Optional.of(recipeRecord));

        Optional<Recipe> recipe = recipeService.getRecipeById("12345");

        assertEquals(recipeRecord.getTitle(), recipe.get().getTitle());
        assertEquals(recipeRecord.getCuisine().toString(), recipe.get().getCuisine());
        assertEquals(recipeRecord.getDescription(), recipe.get().getDescription());
        assertEquals(recipeRecord.getDietaryRestrictions().toString(), recipe.get().getDietaryRestrictions());
        assertEquals(recipeRecord.hasDietaryRestrictions(), recipe.get().hasDietaryRestrictions());
        assertEquals(recipeRecord.getIngredients(), recipe.get().getIngredients());
        assertEquals(recipeRecord.getInstructions(), recipe.get().getInstructions());
        verify(recipeRepository, times(1)).findById(anyString());
    }

    private List<RecipeRecord> createRecipeRecords() {
        List<RecipeRecord> recipeRecords = new ArrayList<>();
        recipeRecords.add(createRecipeRecord());
        recipeRecords.add(createRecipeRecord());
        recipeRecords.add(createRecipeRecord());
        return recipeRecords;
    }

    private RecipeRecord createRecipeRecord() {
        RecipeRecord recipeRecord = new RecipeRecord();
        recipeRecord.setId(UUID.randomUUID().toString());
        recipeRecord.setTitle("Sample Recipe");
        recipeRecord.setCuisine(Enums.Cuisine.ITALIAN);
        recipeRecord.setDescription("A delicious Italian dish");
        recipeRecord.setDietaryRestrictions(Enums.DietaryRestrictions.GLUTEN_FREE);
        recipeRecord.setHasDietaryRestrictions(true);
        List<String> ingredients = new ArrayList<>();
        ingredients.add("Ingredient 1");
        ingredients.add("Ingredient 2");
        recipeRecord.setIngredients(ingredients);
        recipeRecord.setInstructions("Step 1, Step 2, Step 3");
        return recipeRecord;
    }

    private Recipe createRecipe() {
        List<String> ingredients = new ArrayList<>();
        ingredients.add("Ingredient 1");
        ingredients.add("Ingredient 2");
        return new Recipe(
                "Sample Recipe",
                null,
                "ITALIAN",
                "A delicious Italian dish",
                "GLUTEN_FREE",
                true,
                ingredients,
                "Step 1, Step 2, Step 3"
        );
    }
}
