import BaseClass from "../util/baseClass";
import HomeClient from "../api/HomeClient"; // Import the client class for making API requests
// import DataStore from "../util/DataStore";
// import RecipeListPage from "src/pages/RecipeListPage";

class HomePage extends BaseClass {
    constructor() {
        super();
        this.client = new HomeClient(); // Create an instance of the client class
        this.bindClassMethods(['onSearch', 'renderHomePage', 'mount'], this);
    }

    async mount() {
        // Get the search form element
        const searchForm = document.getElementById('search-recipe');

        // Add an event listener for the form submission event
        searchForm.addEventListener('submit', this.onSearch);

        // Call the renderHomePage() method to render the initial page content
        await this.renderHomePage();

        // Fetch the available cuisines and populate the cuisine filter
        fetch('/api/cuisineOptions')
            .then(response => response.json())
            .then(data => populateCuisineFilter(data));

        // Fetch the available dietary restrictions and populate the dietary restrictions filter
        fetch('/api/dietaryRestrictionOptions')
            .then(response => response.json())
            .then(data => populateDietaryRestrictionsFilter(data));
        console.log('Testing console log')
    }

    onSearch(event) {
        event.preventDefault(); // Prevent the default form submission behavior

        // Retrieve the search query, cuisine, and dietary restrictions from the input fields
        const searchQuery = document.getElementById('search-bar-input').value;
        const cuisine = document.getElementById('cuisineFilter').value;
        const dietaryRestrictions = document.getElementById('dietaryRestrictionsFilter').value;

        // Construct the base URL for RecipeListPage.html
        let url = 'RecipeListPage.html?';

        // Add the search query parameter
        if (searchQuery !== '') {
            url += `query=${encodeURIComponent(searchQuery)}`;
        }

        // Add the cuisine parameter if selected
        if (cuisine !== '') {
            url += `&cuisine=${encodeURIComponent(cuisine)}`;
        }

        // Add the dietary restrictions parameter if selected
        if (dietaryRestrictions !== '') {
            url += `&dietaryRestrictions=${encodeURIComponent(dietaryRestrictions)}`;
        }

        // Log the constructed URL
        console.log('Search URL:', url);

        // Redirect to the constructed URL
        window.location.href = url;
    }


    async renderHomePage() {
        // Get the main element
        const mainElement = document.querySelector('main');

        // Check if the main element already contains the intro container
        const introContainer = document.querySelector('.intro-container');
        if (introContainer) {
            // If the intro container exists, remove it from the main element
            mainElement.removeChild(introContainer);
            console.log('Testing console log')
        }

        // Fetch all recipes from the API
        const recipes = await this.client.getAllRecipes();

        // Clear the existing content of the main element
        mainElement.innerHTML = '';

        // Iterate over the recipes and create HTML elements dynamically
        recipes.forEach(recipe => {
            const recipeContainer = document.createElement('div');
            recipeContainer.className = 'recipe-container';

            const recipeTitle = document.createElement('h3');
            recipeTitle.innerText = recipe.title;

            // Append the recipe title to the recipe container
            recipeContainer.appendChild(recipeTitle);

            // Append the recipe container to the main element
            mainElement.appendChild(recipeContainer);
        });
        console.log('Testing console log')
    }

}

const homePage = new HomePage();
homePage.mount().catch(error => {
    console.error('Error during mount:', error);
});
