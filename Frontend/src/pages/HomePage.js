import BaseClass from "../util/baseClass";
import HomeClient from "../api/HomeClient";
import DataStore from "../util/DataStore";
import RecipeListPage from "src/pages/RecipeListPage";
class HomePage extends BaseClass {
    constructor() {
        super();
        this.client = new HomeClient();
        this.bindClassMethods(['onSearch', 'renderHomePage', 'mount'], this);
    }

    mount() {
        const searchForm = document.getElementById('search-recipe');
        searchForm.addEventListener('submit', this.onSearch);

        return new Promise(async (resolve) => {
            await this.renderHomePage();
            resolve();
        }).then(() => {
            fetch('/api/cuisineOptions')
                .then(response => response.json())
                .then(data => populateCuisineFilter(data));

            fetch('/api/dietaryRestrictionOptions')
                .then(response => response.json())
                .then(data => populateDietaryRestrictionsFilter(data));
        });
    }

    onSearch(event) {
        event.preventDefault();

        const searchQuery = document.getElementById('search-bar-input').value;
        const cuisine = document.getElementById('cuisineFilter').value;
        const dietaryRestrictions = document.getElementById('dietaryRestrictionsFilter').value;

        let url = 'http://localhost:5001/recipes/search?';

        if (searchQuery !== '') {
            url += `query=${encodeURIComponent(searchQuery)}`;
        }

        if (cuisine !== '') {
            url += `&cuisine=${encodeURIComponent(cuisine)}`;
        }

        if (dietaryRestrictions !== '') {
            url += `&dietaryRestrictions=${encodeURIComponent(dietaryRestrictions)}`;
        }

        console.log('Search URL:', url);

        // Instead of redirecting, create a new RecipeListPage instance and render the search results
        const recipeListPage = new RecipeListPage();
        recipeListPage.renderSearchResults(url);
    }

    async renderHomePage() {
        const mainElement = document.querySelector('main');
        const introContainer = document.querySelector('.intro-container');
        if (introContainer) {
            mainElement.removeChild(introContainer);
        }

        const recipes = await this.client.getAllRecipes();

        mainElement.innerHTML = '';

        recipes.forEach(recipe => {
            const recipeContainer = document.createElement('div');
            recipeContainer.className = 'recipe-container';

            const recipeTitle = document.createElement('h3');
            recipeTitle.innerText = recipe.title;

            recipeContainer.appendChild(recipeTitle);

            mainElement.appendChild(recipeContainer);
        });
    }
}

const homePage = new HomePage();
homePage.mount().catch(error => {
    console.error('Error during mount:', error);
});
