import BaseClass from "../util/baseClass";
import HomeClient from "../api/HomeClient";// Import the client class for making API requests
import DataStore from "../util/DataStore";

class HomePage extends BaseClass {
    constructor() {
        super();
        this.client = new HomeClient(); // Create an instance of the client class
        this.bindClassMethods(['onSearch', 'renderHomePage','mount', 'onGet'], this);
    }
    //
    // async mount() {
    //     // Get the search form element
    //     const searchForm = document.getElementById('search-recipe');
    //
    //     // Add an event listener for the form submission event
    //     searchForm.addEventListener('submit', this.onSearch);
    //
    //     // Call the renderHomePage() method to render the initial page content
    //     await this.renderHomePage();
    //
    //
    // }

    async mount() {
        // Get the search form element
        const searchForm = document.getElementById('search-recipe-input');

        // Add an event listener for the form submission event
        searchForm.addEventListener('submit', function(event) {
            event.preventDefault(); // Prevent the default form submission behavior

            // Perform your search or other actions here
            // You can retrieve the search query from the input field
            const searchQuery = document.getElementById('search-bar-input').value;

            // Perform the necessary actions with the search query
            // For example, make an AJAX request or navigate to a new page

            // Clear the input field
            document.getElementById('search-bar-input').value = '';
        });


// Call the mount() method when the document has finished loading
        document.addEventListener('DOMContentLoaded', function() {
            mount();
        });

    }


    onSearch = async (event) => {
        event.preventDefault(); // Prevent the default form submission behavior

        // Retrieve the search query from the input field
        const searchQuery = document.getElementById('search-bar-input').value;

        // Perform the API request to search for recipes
        const recipes = await this.client.searchRecipes(searchQuery);

        // Render the search results in the <main> section
        const recipeListElement = document.getElementById('recipe-list');
        recipeListElement.innerHTML = '';

        if (recipes.length === 0) {
            // If no recipes are found, display a message
            const noResultsMessage = document.createElement('p');
            noResultsMessage.innerText = 'No recipes found. Please try again.';
            recipeListElement.appendChild(noResultsMessage);
        } else {
            // Render the list of recipes
            const recipeList = document.createElement('ul');
            recipeList.className = 'recipe-list';

            recipes.forEach(recipe => {
                const recipeItem = document.createElement('li');
                recipeItem.innerText = recipe.title;

                recipeList.appendChild(recipeItem);
            });

            recipeListElement.appendChild(recipeList);
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


    // async renderHomePage() {
    //     // Get the head element
    //     let head = document.getElementsByTagName('head')[0];
    //
    //     // Create script elements for JavaScript files
    //     let script1 = document.createElement('script');
    //     script1.type = 'text/javascript';
    //     script1.src = 'HomePage.js';
    //
    //     let script2 = document.createElement('script');
    //     script2.type = 'text/javascript';
    //     script2.src = 'CreateRecipePage.js';
    //
    //     // Append the script elements to the head
    //     head.appendChild(script1);
    //     head.appendChild(script2);
    //
    //     // Create link element for CSS file
    //     let link = document.createElement('link');
    //     // link.rel = 'stylesheet';
    //     // link.type = 'text/css';
    //     // link.href = 'css/style.css';
    //
    //     // Append the link element to the head
    //     head.appendChild(link);
    //
    //     // Get the body element
    //     let body = document.getElementsByTagName('body')[0];
    //
    //     // Create header element
    //     let header = document.createElement('header');
    //     header.className = 'header';
    //     header.id = 'header';
    //
    //     // Create div element for site title
    //     let siteTitle = document.createElement('div');
    //     siteTitle.className = 'site-title';
    //
    //     // Create h1 element for title
    //     let h1 = document.createElement('h1');
    //     h1.innerText = 'Foodie';
    //
    //     // Create h5 element for subtitle
    //     let h5 = document.createElement('h5');
    //     h5.innerText = 'Foodie. Where people go to find and make meals that slap';
    //
    //     siteTitle.appendChild(h1);
    //     siteTitle.appendChild(h5);
    //     header.appendChild(siteTitle);
    //     body.appendChild(header);
    //
    //     let searchCard = document.createElement('div');
    //     searchCard.className = 'card';
    //
    //     let searchTitle = document.createElement('h2');
    //     searchTitle.innerText = 'Find your meal here';
    //
    //     // Create form element for search bar
    //     let searchForm = document.createElement('form');
    //     searchForm.id = 'form';
    //     searchForm.role = 'search';
    //
    //     // Create input element for search bar
    //     let searchBar = document.createElement('input');
    //     searchBar.type = 'search';
    //     searchBar.id = 'search-bar';
    //     searchBar.required = true;
    //     searchBar.className = 'validated-field';
    //     searchBar.name = 'q';
    //     searchBar.placeholder = 'Search...';
    //
    //     // Create select element for filter dropdown
    //     let filterDropdown = document.createElement('select');
    //     filterDropdown.className = 'category-dropdown';
    //     filterDropdown.id = 'category-dropdown';
    //
    //     // Create options for the filter dropdown
    //     let dietaryRestrictions = document.createElement('option');
    //     dietaryRestrictions.value = '';
    //     dietaryRestrictions.innerText = 'Dietary Restrictions';
    //
    //     let dairyFree = document.createElement('option');
    //     dairyFree.value = 'Dairy Free';
    //     dairyFree.innerText = 'Dairy Free';
    //
    //     let glutenFree = document.createElement('option');
    //     glutenFree.value = 'Gluten Free';
    //     glutenFree.innerText = 'Gluten Free';
    //
    //     let vegan = document.createElement('option');
    //     vegan.value = 'Vegan';
    //     vegan.innerText = 'Vegan';
    //
    //     // Append options to the filter dropdown
    //     filterDropdown.appendChild(dietaryRestrictions);
    //     filterDropdown.appendChild(dairyFree);
    //     filterDropdown.appendChild(glutenFree);
    //     filterDropdown.appendChild(vegan);
    //
    //     searchBar.addEventListener("submit", function (event) {event.preventDefault();
    //     })
    //
    //     // Create button element for search
    //     let searchButton = document.createElement('button');
    //     searchButton.innerText = '';
    //
    //     this.mount();
    //
    //
    // }

    async onGet(event) {
        //prevents page refresh on submit
        event.preventDefault();
        // Get the search bar and category dropdown values
        let searchBar = document.getElementById("search-bar-input").value;
      //  let categoryDropdown = document.getElementById("category-dropdown").value;

        let queryString = `q=${searchBar}`;
        let url = `http://localhost:5001/api/recipes${queryString}`;

        let result = await this.client.getRecipe(searchBar, this.errorHandler);
        this.dataStore.set("recipes", result);
        if (result) {
            this.showMessage(`Got ${result.name}!`);
        } else {
            this.errorHandler("Error doing GET! Try again...");
        }


        fetch(url)
            .then(response => response.json())
            .then(data => {
                // Process the received data (list of recipes) from the server
                console.log(data);
                // Perform further operations with the recipe data
                // Redirect to the Recipe List Page or update the current page content
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

}
const main = async () => {
    const homePage = new HomePage();
    homePage.mount();
};

$('#searchButton').click(performSearch);
window.addEventListener('DOMContentLoaded', main);


//export default HomePage;
