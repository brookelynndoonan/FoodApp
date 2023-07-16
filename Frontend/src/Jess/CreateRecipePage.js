document.addEventListener('DOMContentLoaded', function () {
    populateCuisineOptions();
    populateDietaryRestrictionsOptions();

    document.getElementById('recipeForm').addEventListener('submit', function (event) {
        event.preventDefault();

        // Get form data
        const formData = new FormData(event.target);
        const dietaryRestrictions = [];
        formData.getAll('dietary-restriction').forEach((option) => {
            dietaryRestrictions.push(option);
        });

        const hasDietaryRestrictions = dietaryRestrictions.length > 0;

        const ingredients = Array.from(document.querySelectorAll('#ingredients-container .ingredient-row input[name="ingredient"]')).map(input => input.value.trim()).filter(value => value !== '');

        const recipeData = {
            title: capitalizeWords(formData.get('title')),
            cuisine: capitalizeWords(formData.get('cuisine')).replace(/_/g, ' '),
            description: formData.get('description'),
            dietaryRestrictions: capitalizeWordsWithSpaces(dietaryRestrictions),
            hasDietaryRestrictions: hasDietaryRestrictions,
            ingredients: ingredients,
            instructions: formData.get('instructions')
        };

        // Send POST request to backend
        fetch('http://localhost:5001/recipes', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(recipeData)
        })
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error('Failed to create recipe');
                }
            })
            .then(data => {
                console.log('Recipe created:', data);
                // Redirect to the created recipe's page
                window.location.href = `RecipeDetailsPage.html?id=${data.id}`;
            })
            .catch(error => {
                console.error(error);
                // Handle error
            });
    });

    const ingredientsContainer = document.getElementById('ingredients-container');

    ingredientsContainer.addEventListener('keydown', function (event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            addIngredientField();
        }
    });

    ingredientsContainer.addEventListener('keydown', function (event) {
        if (event.key === 'Backspace' && event.target.value.trim() === '') {
            const ingredientRow = event.target.closest('.ingredient-row');
            const ingredients = Array.from(document.querySelectorAll('#ingredients-container .ingredient-row'));
            const index = ingredients.indexOf(ingredientRow);
            if (index > 0) {
                event.preventDefault();
                ingredientsContainer.removeChild(ingredientRow);
                ingredients[index - 1].querySelector('input').focus();
            }
        }
    });
});

// Function to populate cuisine options
function populateCuisineOptions() {
    // Make an AJAX request to fetch the cuisine options from the server
    fetch('http://localhost:5001/api/cuisineOptions')
        .then((response) => response.json())
        .then((data) => {
            const cuisineOptions = data;

            // Sort the cuisine options alphabetically
            cuisineOptions.sort();

            // Get the select element for cuisine
            const cuisineSelect = document.getElementById('cuisine');

            // Loop through the cuisine options and create an option element for each
            cuisineOptions.forEach((option) => {
                const optionElement = document.createElement('option');
                optionElement.value = option;
                optionElement.textContent = capitalizeWords(option).replace(/_/g, ' ');

                cuisineSelect.appendChild(optionElement);
            });
        })
        .catch((error) => {
            console.error('Error fetching cuisine options:', error);
        });
}

// Function to populate the dietary restriction options
function populateDietaryRestrictionsOptions() {
    const dietaryRestrictionsContainer = document.getElementById('checkbox-container');

    fetch('http://localhost:5001/api/dietaryRestrictionOptions')
        .then((response) => response.json())
        .then((data) => {
            const dietaryRestrictionsOptions = data;

            // Sort the dietary restriction options alphabetically
            dietaryRestrictionsOptions.sort();

            dietaryRestrictionsOptions.forEach((option) => {
                const formattedOption = option
                    .split('_')
                    .map((word) => capitalizeWords(word))
                    .join(' ');

                const checkbox = document.createElement('input');
                checkbox.type = 'checkbox';
                checkbox.name = 'dietary-restriction';
                checkbox.value = option;
                checkbox.id = option;

                const label = document.createElement('label');
                label.textContent = formattedOption;
                label.htmlFor = option;

                checkbox.addEventListener('change', function () {
                    updateHasDietaryRestrictions();
                });

                dietaryRestrictionsContainer.appendChild(checkbox);
                dietaryRestrictionsContainer.appendChild(label);
            });

            // Set the display style of the container to flex or grid
            dietaryRestrictionsContainer.style.display = 'flex'; // Or use 'grid' for CSS grid layout

            // Initially update the hasDietaryRestrictions checkbox
            updateHasDietaryRestrictions();
        })
        .catch((error) => {
            console.error('Error fetching dietary restrictions options:', error);
        });
}

// Helper function to capitalize the first letter of each word in a string
function capitalizeWords(str) {
    return str.toLowerCase().replace(/(^|\s)\S/g, (match) => match.toUpperCase());
}

// Helper function to capitalize words with spaces instead of underscores
function capitalizeWordsWithSpaces(options) {
    const capitalizedOptions = options.map((option) =>
        option
            .split('_')
            .map((word) => capitalizeWords(word))
            .join(' ')
    );
    return capitalizedOptions.join(', ');
}

// Helper function to update the hasDietaryRestrictions checkbox
function updateHasDietaryRestrictions() {
    const checkboxes = document.querySelectorAll('input[name="dietary-restriction"]');
    const hasDietaryRestrictionsCheckbox = document.createElement('input');
    hasDietaryRestrictionsCheckbox.type = 'hidden';
    hasDietaryRestrictionsCheckbox.name = 'hasDietaryRestrictions';
    hasDietaryRestrictionsCheckbox.value = '';

    let hasDietaryRestrictions = false;

    checkboxes.forEach((checkbox) => {
        if (checkbox.checked) {
            hasDietaryRestrictions = true;
        }
    });

    hasDietaryRestrictionsCheckbox.value = hasDietaryRestrictions;

    document.getElementById('recipeForm').appendChild(hasDietaryRestrictionsCheckbox);
}

// Function to add an ingredient field
function addIngredientField() {
    const ingredientsContainer = document.getElementById('ingredients-container');

    const ingredientRow = document.createElement('div');
    ingredientRow.classList.add('ingredient-row');

    const ingredientInput = document.createElement('input');
    ingredientInput.type = 'text';
    ingredientInput.name = 'ingredient';
    ingredientInput.required = true;

    ingredientRow.appendChild(ingredientInput);

    ingredientsContainer.appendChild(ingredientRow);

    ingredientInput.focus();
}

// Function to remove an ingredient field
function removeIngredientField(row) {
    const ingredientsContainer = document.getElementById('ingredients-container');
    ingredientsContainer.removeChild(row);
}
