import BaseClass from "../util/baseClass";
import HomeClient from "../api/HomeClient"; // Import the client class for making API requests
import DataStore from "../util/DataStore";

class HomePage extends BaseClass {
    constructor() {
        super();
        this.client = new HomeClient(); // Create an instance of the client class
        this.bindClassMethods(['onSearch', 'renderHomePage', 'mount', 'onGet'], this);
    }

    async mount() {
        // Get the search form element
        const searchForm = document.getElementById('search-recipe');

        // Add an event listener for the form submission event
        searchForm.addEventListener('submit', this.onSearch);

        // Call the renderHomePage() method to render the initial page content
        await this.renderHomePage();

        // Fetch the available cuisines and populate the cuisine filter
        fetch('http://localhost:5001/api/cuisineOptions')
            .then(response => response.json())
            .then(data => populateCuisineFilter(data));

        // Fetch the available dietary restrictions and populate the dietary restrictions filter
        fetch('http://localhost:5001/api/dietaryRestrictionOptions')
            .then(response => response.json())
            .then(data => populateDietaryRestrictionsFilter(data));
    }

    onSearch = async (event) => {
        event.preventDefault(); // Prevent the default form submission behavior

        // Retrieve the search query from the input field
        const searchQuery = document.getElementById('search-bar-input').value;

        // Perform the API request to search for recipes
        const recipes = await this.client.searchRecipes(searchQuery);

        // Render the search results in the <main> section
        const searchResultsContainer = document.getElementById('searchResultsContainer');
        searchResultsContainer.innerHTML = '';

        if (recipes.length === 0) {
            // If no recipes are found, display a message
            const noResultsMessage = document.createElement('p');
            noResultsMessage.innerText = 'No recipes found. Please try again.';
            searchResultsContainer.appendChild(noResultsMessage);
        } else {
            // Render the list of recipes
            const recipeList = document.createElement('ul');
            recipeList.className = 'recipe-list';

            recipes.forEach(recipe => {
                const recipeItem = document.createElement('li');
                recipeItem.innerText = recipe.title;

                recipeList.appendChild(recipeItem);
            });

            searchResultsContainer.appendChild(recipeList);
        }
    };

    async renderHomePage() {
        // Get the main element
        const mainElement = document.querySelector('main');

        // Check if the main element already contains the intro container
        const introContainer = document.querySelector('.intro-container');
        if (introContainer) {
            // If the intro container exists, remove it from the main element
            mainElement.removeChild(introContainer);
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
    }
}

const homePage = new HomePage();
homePage.mount();