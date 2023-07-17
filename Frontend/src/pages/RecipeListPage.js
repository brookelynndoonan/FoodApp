class RecipeListPage {
    constructor() {
        this.searchResultsContainer = document.getElementById('searchResultsContainer');
    }

    async renderSearchResults() {
        // Get the search parameters from the URL query string
        const urlParams = new URLSearchParams(window.location.search);
        const searchQuery = urlParams.get('query');
        const cuisine = urlParams.get('cuisine');
        const dietaryRestrictions = urlParams.get('dietaryRestrictions');

        // Fetch the search results using the search parameters
        const searchResults = await this.fetchSearchResults(searchQuery, cuisine, dietaryRestrictions);

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
        }
    }

    fetchSearchResults(query, cuisine, dietaryRestrictions) {
        return new Promise(async (resolve, reject) => {
            try {
                // Build the URL with the search parameters
                let url = `http://localhost:5001/recipes/search?query=${encodeURIComponent(query)}`;

                // Append the optional cuisine parameter if provided
                if (cuisine) {
                    url += `&cuisine=${encodeURIComponent(cuisine)}`;
                }

                // Append the optional dietary restrictions parameter if provided
                if (dietaryRestrictions) {
                    url += `&dietaryRestrictions=${encodeURIComponent(dietaryRestrictions)}`;
                }

                // Send a GET request to the constructed URL
                const response = await fetch(url);
                if (!response.ok) {
                    throw new Error('Failed to fetch search results.');
                }
                const data = await response.json();
                resolve(data);
            } catch (error) {
                console.error(error);
                reject(error);
            }
        });
    }


    createRecipeCard(recipe) {
        // Create elements for the recipe card
        const recipeCard = document.createElement('div');
        recipeCard.className = 'recipe-card';

        const recipeTitle = document.createElement('h3');
        recipeTitle.innerText = recipe.title;

        const recipeDescription = document.createElement('p');
        recipeDescription.innerText = recipe.description;

        const recipeIngredients = document.createElement('p');
        recipeIngredients.innerText = `Ingredients: ${recipe.ingredients.join(', ')}`;

        const recipeInstructions = document.createElement('p');
        recipeInstructions.innerText = `Instructions: ${recipe.instructions}`;

        // Append the elements to the recipe card
        recipeCard.appendChild(recipeTitle);
        recipeCard.appendChild(recipeDescription);
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
