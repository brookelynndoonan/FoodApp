
// JavaScript code to interact with the back-end API

// Function to validate the title field
function validateTitle() {
    const titleField = document.getElementById('title');
    const titleError = document.getElementById('title-error');

    if (titleField.value.trim() === '') {
        titleError.textContent = 'Title is required.';
        return false;
    }

    titleError.textContent = '';
    return true;
}

// Function to validate the description field
function validateDescription() {
    const descriptionField = document.getElementById('description');
    const descriptionError = document.getElementById('description-error');

    if (descriptionField.value.trim() === '') {
        descriptionError.textContent = 'Description is required.';
        return false;
    }

    descriptionError.textContent = '';
    return true;
}

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
    const dietaryRestrictionsField = document.getElementById('dietary-restrictions');
    const dietaryRestrictionsError = document.getElementById('dietary-restrictions-error');

    if (dietaryRestrictionsField.value.trim() === '') {
        dietaryRestrictionsError.textContent = 'Dietary restrictions is required.';
        return false;
    }
    if (dietaryRestrictionsField.value.trim() === 'NONE' && dietaryRestrictionsField.selectedOptions.length > 1) {
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

        if (ingredientName === '' || ingredientQuantity === '' || ingredientQuantityType === '') {
            const ingredientError = document.getElementById(`ingredient-error-${i}`);
            ingredientError.textContent = 'Please enter all fields for the ingredient.';
            isValid = false;
        } else {
            const ingredientError = document.getElementById(`ingredient-error-${i}`);
            ingredientError.textContent = '';
        }
    }

    return isValid;
}

// Function to validate the instructions field
function validateInstructions() {
    const instructionsField = document.getElementById('instructions');
    const instructionsError = document.getElementById('instructions-error');

    if (instructionsField.value.trim() === '') {
        instructionsError.textContent = 'Instructions are required.';
        return false;
    }

    instructionsError.textContent = '';
    return true;
}

// Function to validate the entire form
function validateForm() {
    // Perform validation for each form field
    const isValidTitle = validateTitle();
    const isValidDescription = validateDescription();
    const isValidCuisine = validateCuisine();
    const isValidDietaryRestrictions = validateDietaryRestrictions();
    const isValidIngredient = validateIngredients();
    const isValidInstructions = validateInstructions();

    // Return true if all fields are valid
    return (isValidTitle && isValidDescription && isValidCuisine && isValidDietaryRestrictions && isValidIngredient && isValidInstructions);
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

        const ingredient = {
            name: ingredientName,
            quantity: ingredientQuantity,
            quantityType: ingredientQuantityType
        };

        ingredients.push(ingredient);
    }

    return ingredients;
}

// Function to add a new ingredient field
function addIngredientField() {
    const ingredientContainer = document.getElementById('ingredient-container');
    const ingredientRow = document.createElement('div');
    ingredientRow.className = 'ingredient-row';

    const ingredientNameInput = document.createElement('input');
    ingredientNameInput.type = 'text';
    ingredientNameInput.className = 'ingredient-name';
    ingredientNameInput.placeholder = 'Ingredient Name';

    const ingredientQuantityInput = document.createElement('input');
    ingredientQuantityInput.type = 'text';
    ingredientQuantityInput.className = 'ingredient-quantity';
    ingredientQuantityInput.placeholder = 'Quantity';
    const ingredientQuantityTypeSelect = document.createElement('select');
    ingredientQuantityTypeSelect.className = 'ingredient-quantity-type';
    // Populate the quantity type options dynamically, you can use a loop or add options manually

    const addButton = document.createElement('button');
    addButton.setAttribute('aria-label', 'Add');
    addButton.className = 'add-button';
    addButton.innerHTML = '<span class="button-text">+</span>';
    addButton.onclick = addIngredientField;

    const removeButton = document.createElement('button');
    removeButton.setAttribute('aria-label', 'Remove');
    removeButton.className = 'remove-button';
    removeButton.innerHTML = '<span class="button-text">-</span>';
    removeButton.onclick = function() {
        removeIngredientField(ingredientRow);
    };

    const errorMessage = document.createElement('span');
    errorMessage.className = 'error-message';
    errorMessage.id = `ingredient-error-${ingredientContainer.childElementCount}`;

    ingredientRow.appendChild(ingredientNameInput);
    ingredientRow.appendChild(ingredientQuantityInput);
    ingredientRow.appendChild(ingredientQuantityTypeSelect);
    ingredientRow.appendChild(addButton);
    ingredientRow.appendChild(removeButton);
    ingredientRow.appendChild(errorMessage);

    ingredientContainer.appendChild(ingredientRow);
}

// Function to remove an ingredient field
function removeIngredientField(row) {
    const ingredientContainer = document.getElementById('ingredient-container');
    ingredientContainer.removeChild(row);
}

// Function to reset all ingredient fields
function resetIngredientFields() {
    const ingredientContainer = document.getElementById('ingredient-container');
    ingredientContainer.innerHTML = ''; // Remove all ingredient fields
    addIngredientField(); // Add a single ingredient field by default
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
    const dietaryRestrictions = document.getElementById('dietary-restrictions').value;
    const ingredients = getIngredients();
    const instructions = document.getElementById('instructions').value;

    // Create the recipe object
    const recipe = {
        title, cuisine, description, dietaryRestrictions, ingredients, instructions
    };

    // Send a POST request to the back-end API to create the recipe
    fetch('/api/recipes/create', {
        method: 'POST', headers: {
            'Content-Type': 'application/json'
        }, body: JSON.stringify(recipe)
    })
        .then(response => response.json())
        .then(data => {
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
        .catch(error => {
            console.error('Error:', error);
        });
}

// Function to initialize the create recipe page
function initCreateRecipePage() {
    resetIngredientFields();
    populateCuisineOptions();
    populateDietaryRestrictionsOptions();


}

// Function to populate cuisine options
function populateCuisineOptions() {
    // Make an AJAX request to fetch the cuisine options from the server
    fetch('http://localhost:5001/api/cuisineOptions')
        .then(response => response.json())
        .then(data => {
            const cuisineOptions = data;

            // Get the select element for cuisine
            const cuisineSelect = document.getElementById('cuisine');

            // Create the default "Select one" option
            const defaultOption = document.createElement('option');
            defaultOption.value = '';
            defaultOption.textContent = 'Select one';
            cuisineSelect.appendChild(defaultOption);

            // Loop through the cuisine options and create an option element for each
            cuisineOptions.forEach(option => {
                const optionElement = document.createElement('option');
                optionElement.value = option;
                optionElement.textContent = option.charAt(0) + option.slice(1).toLowerCase().replace('_', ' ');

                cuisineSelect.appendChild(optionElement);
            });
        })
        .catch(error => {
            console.error('Error fetching cuisine options:', error);
        });
}

// Function to populate the dietary restriction options
function populateDietaryRestrictionsOptions() {
    const dietaryRestrictionsContainer = document.getElementById('checkbox-container');

    fetch('http://localhost:5001/api/dietaryRestrictionsOptions') // Update the endpoint to match your backend API
        .then(response => response.json())
        .then(data => {
            for (const option of data) {
                const formattedOption = option.charAt(0).toUpperCase() + option.slice(1).toLowerCase().replace('_', ' ');

                const checkbox = document.createElement('input');
                checkbox.type = 'checkbox';
                checkbox.name = 'restriction';
                checkbox.value = option.toLowerCase();

                const label = document.createElement('label');
                label.htmlFor = 'chk-' + option.toLowerCase();
                label.textContent = formattedOption;

                const checkboxContainer = document.createElement('div');
                checkboxContainer.className = 'checkbox-container';
                checkboxContainer.appendChild(checkbox);
                checkboxContainer.appendChild(label);

                dietaryRestrictionsContainer.appendChild(checkboxContainer);
            }

            // Add event listener to "None" option
            const noneCheckbox = document.querySelector('input[value="none"]');
            noneCheckbox.addEventListener('change', () => {
                if (noneCheckbox.checked) {
                    setHasDietaryRestrictions(false);
                }
            });
        })
        .catch(error => {
            console.error('Error fetching dietary restrictions options:', error);
        });

    // Add an event listener to the form submit button
    const submitButton = document.getElementById('submit-button');
    submitButton.addEventListener('click', validateForm);
}

function setHasDietaryRestrictions(hasDietaryRestrictions) {
    const hasDietaryRestrictionsInput = document.getElementById('has-dietary-restrictions-input');
    hasDietaryRestrictionsInput.value = hasDietaryRestrictions;
}

