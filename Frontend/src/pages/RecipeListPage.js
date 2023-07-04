import BaseClass from "../util/baseClass";

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
class RecipeListPage extends BaseClass {
// Function to display recipes on the page

    displayRecipes(recipes) {
        // Get the recipe list container element
        const recipeListContainer = document.getElementById('recipe-list-container');

        // Clear the existing content of the recipe list container
        recipeListContainer.innerHTML = '';

        // Iterate through the recipes and create HTML elements for each recipe
        recipes.forEach(recipe => {
            // Create a recipe card element
            const recipeCard = document.createElement('li');
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


// Fetch recipe data from backend endpoint
    fetch('/api/recipes')
.then(response => response.json())
.then(data => {
    // Process the received recipe data
    displayRecipes(data);
})
.catch(error => {
    console.error('Error:', error);
});

// Function to navigate to the home page
function goToHomePage() {
    window.location.href = 'HomePage.html';
}






}
}

    // Bind the search button click event to trigger the search
 //  $('#searchButton').click(performSearch);
