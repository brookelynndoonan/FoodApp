<<<<<<< HEAD
document.addEventListener('DOMContentLoaded', function () {
    populateCuisineOptions();
    populateDietaryRestrictionsOptions();

    document.getElementById('recipeForm').addEventListener('submit', function (event) {
        event.preventDefault();
=======
// JavaScript code to interact with the back-end API
>>>>>>> e1387b8 (Removed index and examples)

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

<<<<<<< HEAD
    ingredientsContainer.addEventListener('keydown', function (event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            addIngredientField();
=======
// Function to validate the cuisine field
function validateCuisine() {
    const cuisineField = document.getElementById('cuisine');
    const cuisineError = document.getElementById('cuisine-error');

    if (cuisineField.value.trim() === '') {
        cuisineError.textContent = 'Cuisine is required.';
        return false;
    }

    cuisineError.textContent = '';
    return true;
}

// Function to validate the dietary restrictions field
function validateDietaryRestrictions() {
    const dietaryRestrictionsField = document.querySelectorAll('input[name="dietary-restriction"]:checked');
    const dietaryRestrictionsError = document.getElementById('dietary-restrictions-error');

    if (dietaryRestrictionsField.length === 0) {
        dietaryRestrictionsError.textContent = 'Dietary restrictions is required.';
        return false;
    }

    if (dietaryRestrictionsField.length === 1 && dietaryRestrictionsField[0].value === 'NONE') {
        dietaryRestrictionsError.textContent = 'Cannot select other options when "None" is selected.';
        return false;
    }

    dietaryRestrictionsError.textContent = '';
    return true;
}

// Function to validate the ingredients field
function validateIngredients() {
    const ingredientRows = document.getElementsByClassName('ingredient-row');
    let isValid = true;

    for (let i = 0; i < ingredientRows.length; i++) {
        const ingredientRow = ingredientRows[i];
        const ingredientName = ingredientRow.querySelector('.ingredient-name').value.trim();
        const ingredientQuantity = ingredientRow.querySelector('.ingredient-quantity').value.trim();
        const ingredientQuantityType = ingredientRow.querySelector('.ingredient-quantity-type').value.trim();

        if (ingredientName === '' || ingredientQuantity === '') {
            const ingredientError = document.getElementById(`ingredient-error-${i}`);
            ingredientError.textContent = 'Please enter all fields for the ingredient.';
            isValid = false;
        } else {
            const ingredientError = document.getElementById(`ingredient-error-${i}`);
            ingredientError.textContent = '';
>>>>>>> e1387b8 (Removed index and examples)
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

<<<<<<< HEAD
// Function to populate the dietary restriction options
function populateDietaryRestrictionsOptions() {
    const dietaryRestrictionsContainer = document.getElementById('checkbox-container');

    fetch('http://localhost:5001/api/dietaryRestrictionOptions')
        .then((response) => response.json())
        .then((data) => {
            const dietaryRestrictionsOptions = data;
=======
// Function to validate the instructions field
function validateInstructions() {
    const instructionsField = document.getElementById('instructions');
    const instructionsError = document.getElementById('instructions-error');
>>>>>>> e1387b8 (Removed index and examples)

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

<<<<<<< HEAD
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
=======
// Function to validate the entire form
function validateForm() {
    // Perform validation for each form field
    const isValidTitle = validateTitle();
    const isValidDescription = validateDescription();
    const isValidCuisine = validateCuisine();
    const isValidDietaryRestrictions = validateDietaryRestrictions();
    const isValidIngredients = validateIngredients();
    const isValidInstructions = validateInstructions();

    // Return true if all fields are valid
    return (
        isValidTitle &&
        isValidDescription &&
        isValidCuisine &&
        isValidDietaryRestrictions &&
        isValidIngredients &&
        isValidInstructions
    );
}

// Function to get the list of ingredients from the ingredient fields
function getIngredients() {
    const ingredientRows = document.getElementsByClassName('ingredient-row');
    const ingredients = [];

    for (let i = 0; i < ingredientRows.length; i++) {
        const ingredientRow = ingredientRows[i];
        const ingredientName = ingredientRow.querySelector('.ingredient-name').value.trim();
        const ingredientQuantity = ingredientRow.querySelector('.ingredient-quantity').value.trim();
        const ingredientQuantityType = ingredientRow.querySelector('.ingredient-quantity-type').value.trim();

        // Format the quantity type with a capital first letter
        const formattedQuantityType =
            ingredientQuantityType ? ingredientQuantityType.charAt(0).toUpperCase() + ingredientQuantityType.slice(1) : '';

        const formattedIngredient = `${ingredientName} (${ingredientQuantity} ${formattedQuantityType})`;
        ingredients.push(formattedIngredient);
    }

    return ingredients;
}

let ingredientIndex = 1;
// Function to add a new ingredient row
function addIngredientField() {
    const ingredientContainer = document.getElementById('ingredient-container');

    // Create a new ingredient row
    const newIngredientRow = document.createElement('div');
    newIngredientRow.classList.add('ingredient-row');

    // Quantity field
    const ingredientQuantityLabel = document.createElement('label');
    ingredientQuantityLabel.textContent = 'Quantity';

    const ingredientQuantityInput = document.createElement('input');
    ingredientQuantityInput.classList.add('ingredient-quantity');
    ingredientQuantityInput.placeholder = 'Quantity';
    ingredientQuantityInput.type = 'text';


    // Quantity Type field
    const ingredientQuantityTypeLabel = document.createElement('label');
    ingredientQuantityTypeLabel.textContent = 'Quantity Type';

    const ingredientQuantityTypeSelect = document.createElement('select');
    ingredientQuantityTypeSelect.classList.add('ingredient-quantity-type');
    populateQuantityTypeOptions(ingredientQuantityTypeSelect); // Populate the quantity type options


    // Ingredient Name field
    const ingredientNameLabel = document.createElement('label');
    ingredientNameLabel.textContent = 'Ingredient Name';

    const ingredientNameInput = document.createElement('input');
    ingredientNameInput.classList.add('ingredient-name');
    ingredientNameInput.placeholder = 'Ingredient Name';
    ingredientNameInput.type = 'text';


    // Plus button
    const addButton = document.createElement('button');
    addButton.setAttribute('aria-label', 'Add');
    addButton.classList.add('add-button');
    addButton.onclick = addIngredientField;

    const buttonText = document.createElement('span');
    buttonText.classList.add('button-text');
    buttonText.textContent = '+';

    addButton.appendChild(buttonText);

    // Minus button (only for rows other than the first one)
    let removeButton = null;
    if (ingredientIndex > 0) {
        removeButton = document.createElement('button');
        removeButton.setAttribute('aria-label', 'Remove');
        removeButton.classList.add('remove-button');
        removeButton.onclick = function () {
            removeIngredientField(newIngredientRow);
        };

        const removeButtonText = document.createElement('span');
        removeButtonText.classList.add('button-text');
        removeButtonText.textContent = '-';

        removeButton.appendChild(removeButtonText);
    }

    // Error message
    const errorSpan = document.createElement('span');
    errorSpan.classList.add('error-message');
    errorSpan.id = 'ingredient-error-' + ingredientIndex;

    // Append all elements to the ingredient row
    newIngredientRow.appendChild(ingredientQuantityLabel);
    newIngredientRow.appendChild(ingredientQuantityInput);
    newIngredientRow.appendChild(ingredientQuantityTypeLabel);
    newIngredientRow.appendChild(ingredientQuantityTypeSelect);
    newIngredientRow.appendChild(ingredientNameLabel);
    newIngredientRow.appendChild(ingredientNameInput);
    newIngredientRow.appendChild(addButton);
    if (removeButton) {
        newIngredientRow.appendChild(removeButton);
    }
    newIngredientRow.appendChild(errorSpan);

    // Append the new ingredient row to the ingredient container
    ingredientContainer.appendChild(newIngredientRow);

    ingredientIndex++; // Increment the ingredient index for the next row
>>>>>>> e1387b8 (Removed index and examples)
}

// Function to remove an ingredient field
function removeIngredientField(row) {
    const ingredientsContainer = document.getElementById('ingredients-container');
    ingredientsContainer.removeChild(row);
}
<<<<<<< HEAD
=======

// Function to reset all ingredient fields
function resetIngredientFields() {
    const ingredientContainer = document.getElementById('ingredient-container');
    ingredientContainer.innerHTML = ''; // Remove all ingredient fields
    addIngredientField(); // Add a single ingredient field by default

    let ingredientIndex = 0; // Reset the ingredient index
}

// Function to create a recipe
function createRecipe() {
    // Validate the form
    const isFormValid = validateForm();

    if (!isFormValid) {
        return;
    }

    // Get input values
    const title = document.getElementById('title').value;
    const cuisine = document.getElementById('cuisine').value;
    const description = document.getElementById('description').value;
    const dietaryRestrictionsField = document.querySelectorAll('input[name="dietary-restriction"]:checked');
    const dietaryRestrictions = Array.from(dietaryRestrictionsField).map((input) => input.value);
    const ingredients = getIngredients();
    const instructions = document.getElementById('instructions').value;

    // Create the recipe object
    const recipe = {
        title,
        cuisine,
        description,
        dietaryRestrictions,
        ingredients,
        instructions,
    };

    // Send a POST request to the back-end API to create the recipe
    fetch('/api/recipes/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(recipe),
    })
        .then((response) => response.json())
        .then((data) => {
            // Recipe created successfully, display success message or redirect to another page
            alert('Recipe created successfully!');

            // Clear the form fields
            document.getElementById('title').value = '';
            document.getElementById('cuisine').value = '';
            document.getElementById('description').value = '';
            document.getElementById('dietary-restrictions').value = 'NONE';
            resetIngredientFields();
            document.getElementById('instructions').value = '';
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

// Function to format an option string
function formatOptionString(option) {
    return option
        .split('_')
        .map((word) => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
        .join(' ');
}

// Function to initialize the create recipe page
function initCreateRecipePage() {
    resetIngredientFields();
    populateCuisineOptions();
    populateDietaryRestrictionsOptions();
    populateQuantityTypeOptions(); // Call the function to populate the quantity type options
}

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

            // Create the default "Select one" option
            const defaultOption = document.createElement('option');
            defaultOption.value = '';
            defaultOption.textContent = 'Select one';
            cuisineSelect.appendChild(defaultOption);

            // Loop through the cuisine options and create an option element for each
            cuisineOptions.forEach((option) => {
                const optionElement = document.createElement('option');
                optionElement.value = option;
                optionElement.textContent = option.charAt(0) + option.slice(1).toLowerCase().replace('_', ' ');

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
                    .map((word) => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
                    .join(' ');

                const checkbox = document.createElement('input');
                checkbox.type = 'checkbox';
                checkbox.name = 'dietary-restriction';
                checkbox.value = option;
                checkbox.id = option;

                const label = document.createElement('label');
                label.textContent = formattedOption;
                label.htmlFor = option;

                dietaryRestrictionsContainer.appendChild(checkbox);
                dietaryRestrictionsContainer.appendChild(label);
                dietaryRestrictionsContainer.appendChild(document.createElement('br'));
            });
        })
        .catch((error) => {
            console.error('Error fetching dietary restrictions options:', error);
        });
}

// Function to populate the quantity type options
function populateQuantityTypeOptions(selectElement) {
    // Make an AJAX request to fetch the quantity type options from the server
    fetch('http://localhost:5001/api/quantityTypeOptions')
        .then((response) => response.json())
        .then((data) => {
            const quantityTypeOptions = data;

            // Sort the quantity type options alphabetically
            quantityTypeOptions.sort();

            // Create the default "Select one" option
            const defaultOption = document.createElement('option');
            defaultOption.value = '';
            defaultOption.textContent = 'Select one';
            selectElement.appendChild(defaultOption);

            // Loop through the quantity type options and create an option element for each
            quantityTypeOptions.forEach((option) => {
                const formattedOption = option
                    .split('_')
                    .map((word) => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
                    .join(' ');

                const optionElement = document.createElement('option');
                optionElement.value = option;
                optionElement.textContent = formattedOption;
                selectElement.appendChild(optionElement);
            });
        })
        .catch((error) => {
            console.error('Error fetching quantity type options:', error);
        });
}



// Function to set the value of the hidden input field for hasDietaryRestrictions
function setHasDietaryRestrictions(hasDietaryRestrictions) {
    const hasDietaryRestrictionsInput = document.getElementById('has-dietary-restrictions-input');
    hasDietaryRestrictionsInput.value = hasDietaryRestrictions;
}
>>>>>>> e1387b8 (Removed index and examples)
