function populateCuisineFilter(cuisines) {
    const cuisineFilter = document.getElementById('cuisineFilter');

    // Remove the existing options from the cuisine filter
    cuisineFilter.innerHTML = '';

    // Add the default option for all cuisines
    const defaultOption = document.createElement('option');
    defaultOption.value = '';
    defaultOption.innerText = 'All Cuisines';
    cuisineFilter.appendChild(defaultOption);

    // Add the cuisine options
    cuisines.forEach(cuisine => {
        const option = document.createElement('option');
        option.value = cuisine;
        option.innerText = formatLabel(cuisine);
        cuisineFilter.appendChild(option);
    });
}

function populateDietaryRestrictionsFilter(dietaryRestrictions) {
    const dietaryRestrictionsFilter = document.getElementById('dietaryRestrictionsFilter');

    // Remove the existing options from the dietary restrictions filter
    dietaryRestrictionsFilter.innerHTML = '';

    // Add the default option for all dietary restrictions
    const defaultOption = document.createElement('option');
    defaultOption.value = '';
    defaultOption.innerText = 'All Dietary Restrictions';
    dietaryRestrictionsFilter.appendChild(defaultOption);

    // Add the dietary restriction options
    dietaryRestrictions.forEach(restriction => {
        const option = document.createElement('option');
        option.value = restriction;
        option.innerText = formatLabel(restriction);
        dietaryRestrictionsFilter.appendChild(option);
    });
}

function formatLabel(text) {
    // Replace underscores with spaces
    const formattedText = text.replace(/_/g, ' ');

    // Capitalize the first letter of each word and convert the rest to lowercase
    const words = formattedText.split(' ');
    const capitalizedWords = words.map(word => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase());
    const finalText = capitalizedWords.join(' ');

    return finalText;
}



function searchRecipes() {
    const searchInput = document.getElementById('searchInput');
    const cuisineFilter = document.getElementById('cuisineFilter');
    const dietaryRestrictionsFilter = document.getElementById('dietaryRestrictionsFilter');

    const searchQuery = searchInput.value.trim();
    const cuisine = cuisineFilter.value;
    const dietaryRestrictions = dietaryRestrictionsFilter.value;

    if (searchQuery !== '') {
        // Perform search logic here, including the cuisine and dietary restrictions filters
        // You can send a request to the backend API with the search query, cuisine, and dietary restrictions
        // and display the search results in the "searchResultsContainer" element.
        // Example:
        // fetch('/api/search?query=' + encodeURIComponent(searchQuery) + '&cuisine=' + encodeURIComponent(cuisine) + '&dietaryRestrictions=' + encodeURIComponent(dietaryRestrictions))
        //     .then(response => response.json())
        //     .then(data => displaySearchResults(data));

        // For now, let's just display the search query, cuisine, and dietary restrictions in the container
        const searchResultsContainer = document.getElementById('searchResultsContainer');
        searchResultsContainer.innerHTML = `
            <p>Search query: ${searchQuery}</p>
            <p>Cuisine filter: ${cuisine}</p>
            <p>Dietary restrictions filter: ${dietaryRestrictions}</p>
        `;
    } else {
        // Clear the search results container if the search query is empty
        const searchResultsContainer = document.getElementById('searchResultsContainer');
        searchResultsContainer.innerHTML = '';
    }
}

document.addEventListener('DOMContentLoaded', function () {
    // Fetch the available cuisines and populate the cuisine filter
    fetch('http://localhost:5001/api/cuisineOptions')
        .then(response => response.json())
        .then(data => populateCuisineFilter(data));

    // Fetch the available dietary restrictions and populate the dietary restrictions filter
    fetch('http://localhost:5001/api/dietaryRestrictionOptions')
        .then(response => response.json())
        .then(data => populateDietaryRestrictionsFilter(data));
});
