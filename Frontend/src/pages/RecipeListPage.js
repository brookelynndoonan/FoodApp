import axios from "axios";

class RecipeListPage {
    constructor() {
        this.searchResultsContainer = document.getElementById('searchResultsContainer');
    }

    async renderSearchResults() {
        try {
            // Get the search parameters from the URL query string
            const urlParams = new URLSearchParams(window.location.search);
            const searchQuery = urlParams.get('query');
            const cuisine = urlParams.get('cuisine');
            const dietaryRestrictions = urlParams.get('dietaryRestrictions');

            // Build the URL with the search parameters
            let url = `http://localhost:5001/recipes/search?`;

            if (searchQuery) {
                url += `query=${encodeURIComponent(searchQuery)}`;
            }

            if (cuisine) {
                url += `&cuisine=${encodeURIComponent(cuisine)}`;
            }

            if (dietaryRestrictions) {
                url += `&dietaryRestrictions=${encodeURIComponent(dietaryRestrictions)}`;
            }

            // Fetch the search results using the constructed URL
            const searchResults = await this.fetchSearchResults(url);

            // Clear the existing content in the search results container
            this.searchResultsContainer.innerHTML = '';

            // Check if search results were found
            if (searchResults && searchResults.length > 0) {
                // Iterate over the search results and create recipe cards
                searchResults.forEach(recipe => {
                    const recipeCard = this.createRecipeCard(recipe);
                    this.searchResultsContainer.appendChild(recipeCard);
                });
            } else {
                // Display a message when no search results are found
                const noResultsMessage = document.createElement('p');
                noResultsMessage.innerText = 'No results found.';
                this.searchResultsContainer.appendChild(noResultsMessage);
            }console.log('Search Results:', searchResults);
        } catch (error) {
            console.error(error);
            // Handle any errors that occur during rendering
        }
    }



    async fetchSearchResults(url) {
        try {
            const response = await axios.get(url);
            return response.data;
        } catch (error) {
            console.error(error);
            throw new Error('Failed to fetch search results.');
        }
    }

    createRecipeCard(recipe) {
        const recipeCard = document.createElement('div');
        recipeCard.className = 'recipe-card';

        const recipeTitle = document.createElement('h3');
        recipeTitle.innerText = recipe.title;

        const recipeCuisine = document.createElement('p');
        recipeCuisine.innerText = `Cuisine: ${recipe.cuisine}`;

        const recipeDescription = document.createElement('p');
        recipeDescription.innerText = recipe.description;

        const recipeDietaryRestrictions = document.createElement('p');
        recipeDietaryRestrictions.innerText = `Dietary Restrictions: ${recipe.dietaryRestrictions}`;

        const recipeHasDietaryRestrictions = document.createElement('p');
        recipeHasDietaryRestrictions.innerText = `Has Dietary Restrictions: ${recipe.hasDietaryRestrictions}`;

        const recipeIngredients = document.createElement('p');
        recipeIngredients.innerText = `Ingredients: ${recipe.ingredients.join(', ')}`;

        const recipeInstructions = document.createElement('p');
        recipeInstructions.innerText = `Instructions: ${recipe.instructions}`;

        recipeCard.appendChild(recipeTitle);
        recipeCard.appendChild(recipeCuisine);
        recipeCard.appendChild(recipeDescription);
        recipeCard.appendChild(recipeDietaryRestrictions);
        recipeCard.appendChild(recipeHasDietaryRestrictions);
        recipeCard.appendChild(recipeIngredients);
        recipeCard.appendChild(recipeInstructions);

        return recipeCard;
    }
}

document.addEventListener('DOMContentLoaded', async () => {
    const recipeListPage = new RecipeListPage();
    try {
        await recipeListPage.renderSearchResults();
    } catch (error) {
        // Handle any errors that occur during rendering
        console.error(error);
    }
});
