document.addEventListener('DOMContentLoaded', function () {
    const recipeId = getRecipeIdFromUrl();
    if (recipeId) {
        fetchRecipeDetails(recipeId);
    } else {
        console.error('Recipe ID not found in URL');
    }
});

function getRecipeIdFromUrl() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('id');
}

function fetchRecipeDetails(recipeId) {
    fetch(`http://localhost:5001/recipes/${recipeId}`)
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Failed to fetch recipe details');
            }
        })
        .then(data => {
            displayRecipeDetails(data);
        })
        .catch(error => {
            console.error(error);
        });
}

function displayRecipeDetails(recipe) {
    const recipeDetailsContainer = document.getElementById('recipeDetailsContainer');
    recipeDetailsContainer.innerHTML = '';

    const titleElement = document.createElement('h2');
    titleElement.textContent = recipe.title;
    recipeDetailsContainer.appendChild(titleElement);

    const cuisineElement = document.createElement('p');
    cuisineElement.textContent = `Cuisine: ${formatCuisine(recipe.cuisine)}`;
    recipeDetailsContainer.appendChild(cuisineElement);

    const descriptionElement = document.createElement('p');
    descriptionElement.textContent = `Description: ${recipe.description}`;
    recipeDetailsContainer.appendChild(descriptionElement);

    const dietaryRestrictionsElement = document.createElement('p');
    dietaryRestrictionsElement.textContent = `Dietary Restrictions: ${formatDietaryRestrictions(recipe.dietaryRestrictions)}`;
    recipeDetailsContainer.appendChild(dietaryRestrictionsElement);

    const ingredientsElement = document.createElement('div');
    const ingredientsTitle = document.createElement('p');
    ingredientsTitle.textContent = 'Ingredients:';
    ingredientsElement.appendChild(ingredientsTitle);

    const ingredientsList = document.createElement('ul');
    recipe.ingredients.forEach((ingredient) => {
        const listItem = document.createElement('li');
        listItem.textContent = ingredient;
        ingredientsList.appendChild(listItem);
    });

    ingredientsElement.appendChild(ingredientsList);
    recipeDetailsContainer.appendChild(ingredientsElement);

    const instructionsElement = document.createElement('div');
    const instructionsTitle = document.createElement('p');
    instructionsTitle.textContent = 'Instructions:';
    instructionsElement.appendChild(instructionsTitle);

    const instructionsLines = recipe.instructions.split('\n');
    instructionsLines.forEach((line) => {
        const lineElement = document.createElement('p');
        lineElement.textContent = line;
        instructionsElement.appendChild(lineElement);
    });

    recipeDetailsContainer.appendChild(instructionsElement);
}



function formatCuisine(cuisine) {
    return capitalizeWords(cuisine.replace(/_/g, ' '));
}

function formatDietaryRestrictions(dietaryRestrictions) {
    if (dietaryRestrictions) {
        return capitalizeWordsWithSpaces([dietaryRestrictions]);
    } else {
        return 'None';
    }
}

function capitalizeWords(str) {
    return str.toLowerCase().replace(/(^|\s)\S/g, (match) => match.toUpperCase());
}

function capitalizeWordsWithSpaces(options) {
    const capitalizedOptions = options.map((option) =>
        option
            .split('_')
            .map((word) => capitalizeWords(word))
            .join(' ')
    );
    return capitalizedOptions.join(', ');
}
