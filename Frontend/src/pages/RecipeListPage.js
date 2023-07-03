import BaseClass from "../util/baseClass";

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
class RecipeListPage extends BaseClass {




// Function to display recipes on the page
function
    displayRecipes(recipes) {

        // Fetch recipe data from backend endpoint
        fetch('your_backend_endpoint')
            .then(response => response.json())
            .then(data => {
                // Process the received recipe data
                displayRecipes(data);
            })
            .catch(error => {
                console.error('Error:', error);
            });

    // Get the recipe list container element
    const recipeListContainer = document.getElementById('recipe-list-container');

    // Iterate through the recipes and create HTML elements for each recipe
    recipes.forEach(recipe => {
        // Create a recipe card element
        const recipeCard = document.createElement('div');
        recipeCard.classList.add('recipe-card');

        // Create elements for recipe details (e.g., title, ingredients, instructions)
        const title = document.createElement('h2');
        title.textContent = recipe.title;

        const ingredients = document.createElement('ul');
        recipe.ingredients.forEach(ingredient => {
            const li = document.createElement('li');
            li.textContent = ingredient;
            ingredients.appendChild(li);
        });

        const instructions = document.createElement('p');
        instructions.textContent = recipe.instructions;

        // Append the recipe details to the recipe card
        recipeCard.appendChild(title);
        recipeCard.appendChild(ingredients);
        recipeCard.appendChild(instructions);

        // Append the recipe card to the recipe list container
        recipeListContainer.appendChild(recipeCard);
    });


}
}

    // Bind the search button click event to trigger the search
 //  $('#searchButton').click(performSearch);
