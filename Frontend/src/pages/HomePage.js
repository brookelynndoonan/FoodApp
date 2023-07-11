import BaseClass from "../util/baseClass";
import HomeClient from "../api/HomeClient";// Import the client class for making API requests

class HomePage extends BaseClass {
    constructor() {
        super();
        this.client = new HomeClient(); // Create an instance of the client class
        this.bindClassMethods(['onSearch', 'renderHomePage'], this);
    }

    async mount() {
        // Get the search form element
        const searchForm = document.getElementById('search-recipe');

        // Add an event listener for the form submission event
        searchForm.addEventListener('submit', this.onSearch);

        // Call the renderHomePage() method to render the initial page content
        await this.renderHomePage();
    }

    onSearch = async (event) => {
        event.preventDefault(); // Prevent the default form submission behavior

        // Retrieve the search query and category from the input fields
        const searchQuery = document.getElementById('search-bar').value;
        const category = document.getElementById('category-dropdown').value;

        // Perform the API request to search for recipes
        const recipes = await this.client.searchRecipes(category, null, searchQuery);

        // Process the received recipes and perform further actions
        console.log(recipes);

        // Clear the input fields
        document.getElementById('search-bar').value = '';
        document.getElementById('category-dropdown').value = '';
    };

    async renderHomePage() {
        // Your existing code for rendering the home page

        // Mount the form submission event listener and other functionality
        this.mount();
    }
}

export default HomePage;
