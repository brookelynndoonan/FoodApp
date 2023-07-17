package com.kenzie.appserver.service;

import com.kenzie.appserver.converters.RecipeMapper;
import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.repositories.model.Enums;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.model.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static java.util.UUID.randomUUID;
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
        recipeRepository.saveAll(recipeRecords);
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

        Recipe createdRecipe = recipeService.addNewRecipe(recipe);

        assertEquals(recipe.getTitle(), createdRecipe.getTitle());
        assertEquals(recipe.getCuisine(), createdRecipe.getCuisine());
        assertEquals(recipe.getDescription(), createdRecipe.getDescription());
        assertEquals(recipe.getDietaryRestrictions(), createdRecipe.getDietaryRestrictions());
        assertEquals(recipe.getHasDietaryRestrictions(), createdRecipe.getHasDietaryRestrictions());
        assertEquals(recipe.getIngredients(), createdRecipe.getIngredients());

        // Check the format of the instructions
        String[] expectedSteps = recipe.getInstructions().split("\n ");
        String[] actualSteps = createdRecipe.getInstructions().split("\n");
        assertEquals(expectedSteps.length, actualSteps.length);
        for (int i = 0; i < expectedSteps.length; i++) {
            String expectedStep = expectedSteps[i];
            assertEquals(expectedStep, actualSteps[i]);
        }

    }

    @Test
    void testGetRecipesByCuisine() {
        String cuisine = "ITALIAN";
        List<RecipeRecord> recipeRecords = createRecipeRecords();
        when(recipeRepository.findByCuisine(cuisine)).thenReturn(recipeRecords);

        List<Recipe> recipes = recipeService.getRecipesByCuisine("ITALIAN");

        assertEquals(recipeRecords.size(), recipes.size());
        verify(recipeRepository, times(1)).findByCuisine(cuisine);
    }

    @Test
    void testGetRecipesByDietaryRestrictions() {
        List<RecipeRecord> recipeRecords = createRecipeRecords();
        when(recipeRepository.findByDietaryRestrictions(String.valueOf(any(Enums.DietaryRestrictions.class)))).thenReturn(recipeRecords);

        List<Recipe> recipes = recipeService.getRecipesByDietaryRestrictions("GLUTEN_FREE");

        assertEquals(recipeRecords.size(), recipes.size());
        verify(recipeRepository, times(1)).findByDietaryRestrictions(String.valueOf(any(Enums.DietaryRestrictions.class)));
    }

    @Test
    void testGetRecipeById() {
        RecipeRecord recipeRecord = createRecipeRecord();
        when(recipeRepository.findById(anyString())).thenReturn(Optional.of(recipeRecord));


            RecipeRecord recipe = recipeService.getRecipeById("12345");

            assertEquals(recipeRecord.getTitle(), recipe.getTitle());
            assertEquals(recipeRecord.getCuisine(), recipe.getCuisine());
            assertEquals(recipeRecord.getDescription(), recipe.getDescription());
            assertEquals(recipeRecord.getDietaryRestrictions(), recipe.getDietaryRestrictions());
            assertEquals(recipeRecord.getHasDietaryRestrictions(), recipe.getHasDietaryRestrictions());
            assertEquals(recipeRecord.getIngredients(), recipe.getIngredients());
            assertEquals(recipeRecord.getInstructions(), recipe.getInstructions());



        verify(recipeRepository, times(1)).findById(anyString());
    }



    @Test
    void findByRecipeId_invalid() {
        // GIVEN
        String id = randomUUID().toString();

        when(recipeRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN
        RecipeRecord recipe = recipeService.getRecipeById(id);

        // THEN
        Assertions.assertNull(recipe, "The recipe is null when not found");
    }

    @Test
    void deleteRecipeByIdTest() {
        String id = randomUUID().toString();

        recipeService.deleteRecipeById(id);

        verify(recipeRepository).deleteById(id);

    }

    @Test
    void searchRecipes_successful(){
        // GIVEN
        String query = randomUUID().toString();
        String cuisine = randomUUID().toString();
        String dietaryRestrictions = randomUUID().toString();

        List<Recipe> recipes = recipeService.searchRecipes(query, cuisine, dietaryRestrictions);

        List<RecipeRecord> recipeRecord = createRecipeRecords();

        when(recipeRepository.findAll()).thenReturn(recipeRecord);

        when(recipeService.searchRecipes(query, cuisine, dietaryRestrictions))
                .thenReturn(recipes);

        verify(recipeRepository, times(1)).findAll();


    }

    @Test
    void searchRecipes() {
        // GIVEN
        String query = "Sample";
        String cuisine = "ITALIAN";
        String dietaryRestrictions = "GLUTEN_FREE";

        // Create sample recipe records
        List<RecipeRecord> recipeRecords = createRecipeRecords();

        // Mock the repository's findAll method to return the sample recipe records
        when(recipeRepository.findAll()).thenReturn(recipeRecords);

        // WHEN
        List<Recipe> recipes = recipeService.searchRecipes(query, cuisine, dietaryRestrictions);

        // THEN
        verify(recipeRepository, times(1)).findAll();

        // Check if the correct recipes are present in the result
        assertEquals(3, recipes.size()); // Update the expected count to 3 based on your actual data

        for (Recipe recipe : recipes) {
            assertEquals("Sample Recipe", recipe.getTitle());
            assertEquals("ITALIAN", recipe.getCuisine());
            assertEquals("A delicious Italian dish", recipe.getDescription());
            assertEquals("GLUTEN_FREE", recipe.getDietaryRestrictions());
            assertEquals(true, recipe.getHasDietaryRestrictions());
            assertEquals(2, recipe.getIngredients().size()); // Adjust the expected count based on your actual data
            assertEquals("Step 1, Step 2, Step 3", recipe.getInstructions());
        }

        // Test additional branches
        // 1. Search by description
        query = "delicious";
        recipes = recipeService.searchRecipes(query, cuisine, dietaryRestrictions);
        assertEquals(3, recipes.size());

        // 2. Search by ingredient
        query = "Ingredient 1";
        recipes = recipeService.searchRecipes(query, cuisine, dietaryRestrictions);
        assertEquals(3, recipes.size());

        // 3. Search by instructions
        query = "Step 2";
        recipes = recipeService.searchRecipes(query, cuisine, dietaryRestrictions);
        assertEquals(3, recipes.size());

        // 4. Search by cuisine and dietaryRestrictions (additional branches)
        cuisine = "MEXICAN";
        dietaryRestrictions = "VEGAN";
        recipes = recipeService.searchRecipes(query, cuisine, dietaryRestrictions);
        assertEquals(0, recipes.size()); // No recipes with Mexican cuisine and Vegan dietary restrictions

        cuisine = "ITALIAN";
        dietaryRestrictions = "VEGAN";
        recipes = recipeService.searchRecipes(query, cuisine, dietaryRestrictions);
        assertEquals(0, recipes.size()); // No recipes with Italian cuisine and Vegan dietary restrictions

        cuisine = "ITALIAN";
        dietaryRestrictions = "GLUTEN_FREE";
        recipes = recipeService.searchRecipes(query, cuisine, dietaryRestrictions);
        assertEquals(3, recipes.size()); // Recipes with Italian cuisine and Gluten-Free dietary restrictions

        cuisine = "MEXICAN";
        dietaryRestrictions = "GLUTEN_FREE";
        recipes = recipeService.searchRecipes(query, cuisine, dietaryRestrictions);
        assertEquals(0, recipes.size()); // No recipes with Mexican cuisine and Gluten-Free dietary restrictions

        cuisine = "MEXICAN";
        dietaryRestrictions = "";
        recipes = recipeService.searchRecipes(query, cuisine, dietaryRestrictions);
        assertEquals(0, recipes.size()); // No recipes with Mexican cuisine and empty dietary restrictions

        cuisine = "";
        dietaryRestrictions = "VEGAN";
        recipes = recipeService.searchRecipes(query, cuisine, dietaryRestrictions);
        assertEquals(0, recipes.size()); // No recipes with empty cuisine and Vegan dietary restrictions
    }


    @Test
    void searchRecipes_EmptyAndNullDietaryRestrictions() {
        // GIVEN
        String query = "Sample";
        String cuisine = ""; // Empty cuisine
        String dietaryRestrictions = null; // Null dietaryRestrictions

        // Create sample recipe records
        List<RecipeRecord> recipeRecords = createRecipeRecords();

        // Mock the repository's findAll method to return the sample recipe records
        when(recipeRepository.findAll()).thenReturn(recipeRecords);

        // WHEN
        List<Recipe> recipes = recipeService.searchRecipes(query, cuisine, dietaryRestrictions);

        // THEN
        verify(recipeRepository, times(1)).findAll();

        // Check if the correct recipes are present in the result
        assertEquals(3, recipes.size()); // Update the expected count to 3 based on your actual data

        for (Recipe recipe : recipes) {
            assertEquals("Sample Recipe", recipe.getTitle());
            assertEquals("ITALIAN", recipe.getCuisine());
            assertEquals("A delicious Italian dish", recipe.getDescription());
            assertEquals("GLUTEN_FREE", recipe.getDietaryRestrictions());
            assertEquals(true, recipe.getHasDietaryRestrictions());
            assertEquals(2, recipe.getIngredients().size()); // Adjust the expected count based on your actual data
            assertEquals("Step 1, Step 2, Step 3", recipe.getInstructions());
        }
    }




    private List<RecipeRecord> createRecipeRecords() {
        List<RecipeRecord> recipeRecords = new ArrayList<>();
        recipeRecords.add(createRecipeRecord());
        recipeRecords.add(createRecipeRecord());
        recipeRecords.add(createRecipeRecord());
        recipeRepository.saveAll(recipeRecords);
        return recipeRecords;
    }

    private RecipeRecord createRecipeRecord() {
        RecipeRecord recipeRecord = new RecipeRecord();
        recipeRecord.setId(UUID.randomUUID().toString());
        recipeRecord.setTitle("Sample Recipe");
        recipeRecord.setCuisine(String.valueOf(Enums.Cuisine.ITALIAN));
        recipeRecord.setDescription("A delicious Italian dish");
        recipeRecord.setDietaryRestrictions(String.valueOf(Enums.DietaryRestrictions.GLUTEN_FREE));
        recipeRecord.setHasDietaryRestrictions(true);
        List<String> ingredients = new ArrayList<>();
        ingredients.add("Ingredient 1 cup"); // Update ingredient string format
        ingredients.add("Ingredient 2 cup"); // Update ingredient string format
        recipeRecord.setIngredients(ingredients);
        recipeRecord.setIngredients(ingredients);
        recipeRecord.setInstructions("Step 1\nStep 2\nStep 3");
        recipeRepository.save(recipeRecord);
        RecipeMapper.recipeToRecipeRecord(createRecipe());
        return RecipeMapper.recipeToRecipeRecord(createRecipe());
    }


    private Recipe createRecipe() {

        List<String> ingredients = new ArrayList<>();
        ingredients.add("Ingredient 1 cups");
        ingredients.add("Ingredient 2 cups");
        Recipe recipe = new Recipe(
                UUID.randomUUID().toString(),
                "Sample Recipe",
                "ITALIAN",
                "A delicious Italian dish",
                "GLUTEN_FREE",
                true,
                ingredients,
                "Step 1, Step 2, Step 3"
        );
        return recipe;
    }
}
