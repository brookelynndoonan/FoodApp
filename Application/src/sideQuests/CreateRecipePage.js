
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

function validateIngredient() {
    const ingredientInputs = document.getElementsByClassName('ingredient-input');
    let isValid = true;

    for (let i = 0; i < ingredientInputs.length; i++) {
        const ingredientInput = ingredientInputs[i];
        const ingredientValue = ingredientInput.value.trim();

        if (ingredientValue === '') {
            const ingredientError = document.getElementById(`ingredient-error-${i}`);
            ingredientError.textContent = 'Ingredient is required.';
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
    const isValidIngredient = validateIngredient();
    const isValidInstructions = validateInstructions();

    // Return true if all fields are valid
    return (isValidTitle && isValidDescription && isValidCuisine && isValidDietaryRestrictions && isValidIngredient && isValidInstructions);
}

// Function to get the list of ingredients from the ingredient fields
function getIngredients() {
    const ingredientFields = document.getElementsByClassName('ingredient-input');
    const ingredients = [];

    for (let i = 0; i < ingredientFields.length; i++) {
        const ingredient = ingredientFields[i].value.trim();

        if (ingredient !== '') {
            ingredients.push(ingredient);
        }
    }

    return ingredients;
}

// Function to add a new ingredient field
function addIngredientField() {
    const ingredientContainer = document.getElementById('ingredient-container');
    const ingredientRow = document.createElement('div');
    ingredientRow.className = 'ingredient-row';

    const ingredientInput = document.createElement('input');
    ingredientInput.type = 'text';
    ingredientInput.className = 'ingredient-input';
    ingredientInput.placeholder = 'Enter ingredient';

    const ingredientError = document.createElement('span');
    ingredientError.className = 'error-message';
    ingredientError.id = `ingredient-error-${ingredientContainer.children.length}`;

    const addButton = document.createElement('button');
    addButton.className = 'add-button';
    addButton.textContent = '+';
    addButton.onclick = addIngredientField;

    ingredientRow.appendChild(ingredientInput);
    ingredientRow.appendChild(ingredientError);

    // Only add the remove button for ingredient fields after the first one
    if (ingredientContainer.children.length > 0) {
        const removeButton = document.createElement('button');
        removeButton.className = 'remove-button';
        removeButton.textContent = '-';
        removeButton.onclick = function () {
            removeIngredientField(this);
        };
        ingredientRow.appendChild(removeButton);
    }

    ingredientRow.appendChild(addButton);
    ingredientContainer.appendChild(ingredientRow);
}

// Function to remove an ingredient field
function removeIngredientField(button) {
    const ingredientRow = button.parentNode;
    const ingredientContainer = ingredientRow.parentNode;
    ingredientContainer.removeChild(ingredientRow);

    // Update the ingredient error messages
    const ingredientErrors = document.getElementsByClassName('error-message');
    for (let i = 0; i < ingredientErrors.length; i++) {
        ingredientErrors[i].id = `ingredient-error-${i}`;
    }
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
    fetch('/api/recipes', {
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
    const dietaryRestrictions = ['Vegetarian', 'Vegan', 'Gluten-free'];

    for (let i = 0; i < dietaryRestrictions.length; i++) {
        const option = dietaryRestrictions[i];

        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.name = 'restriction';
        checkbox.value = option.toLowerCase();

        const label = document.createElement('label');
        label.htmlFor = 'chk-' + option.toLowerCase();
        label.textContent = option;

        const checkboxContainer = document.createElement('div');
        checkboxContainer.className = 'checkbox-container';
        checkboxContainer.appendChild(checkbox);
        checkboxContainer.appendChild(label);

        dietaryRestrictionsContainer.appendChild(checkboxContainer);
    }

    // Add an event listener to the form submit button
    const submitButton = document.getElementById('submit-button');
    submitButton.addEventListener('click', validateForm);

}
