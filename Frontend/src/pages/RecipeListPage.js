// import BaseClass from "../util/baseClass";
// import $ from 'jquery';
// import RecipeListClient from "../api/RecipeListClient";
//
// class RecipeListPage extends BaseClass {
// // Function to display recipes on the page
// constructor() {
//     super();
//     this.client = new RecipeListClient();
//     this.bindClassMethods(['displayRecipes', 'onGet', 'renderListPage'], this);
// }
//
//
//     displayRecipes(recipes) {
// // Fetch recipe data from backend endpoint
//         fetch('/recipes')
//             .then(response => response.json())
//             .then(data => {
//                 // Process the received recipe data
//                 displayRecipes(data);
//             })
//             .catch(error => {
//                 console.error('Error:', error);
//             });
//         this.displayRecipes();
//
//         // Get the recipe list container element
//         let recipeListContainer = document.getElementById('recipe-list-container');
//
//         // Clear the existing content of the recipe list container
//         recipeListContainer.innerHTML = '';
//
//         // Iterate through the recipes and create HTML elements for each recipe
//         recipes.forEach(recipe => {
//             // Create a recipe card element
//             let recipeCard = document.createElement('li');
//             recipeCard.classList.add('recipe-card');
//
//             // Create elements for recipe details (e.g., title, ingredients, instructions)
//             let title = document.createElement('h2');
//             title.textContent = recipe.title;
//
//             let ingredients = document.createElement('ul');
//             recipe.ingredients.forEach(ingredient => {
//                 const li = document.createElement('li');
//                 li.textContent = ingredient;
//                 ingredients.appendChild(li);
//             });
//
//             let instructions = document.createElement('p');
//             instructions.textContent = recipe.instructions;
//
//             // Append the recipe details to the recipe card
//             recipeCard.appendChild(title);
//             recipeCard.appendChild(ingredients);
//             recipeCard.appendChild(instructions);
//
//             // Append the recipe card to the recipe list container
//             recipeListContainer.appendChild(recipeCard);
//         });
// // // Function to navigate to the home page
// // function goToHomePage() {
// //     window.location.href = 'HomePage.html';
// // }
//     }
//
//     async onGet(event) {
//         //prevents page refresh on submit
//         event.preventDefault();
//         // Get the search bar and category dropdown values
//         let searchBar = document.getElementById("search-bar-input").value;
//         //  let categoryDropdown = document.getElementById("category-dropdown").value;
//
//         let queryString = `q=${searchBar}`;
//         let url = `http://localhost:5001/api/recipes${queryString}`;
//
//         let result = await this.client.getRecipe(searchBar, this.errorHandler);
//         this.dataStore.set("recipes", result);
//         if (result) {
//             this.showMessage(`Got ${result.name}!`);
//         } else {
//             this.errorHandler("Error doing GET! Try again...");
//         }
//
//
//         fetch(url)
//             .then(response => response.json())
//             .then(data => {
//                 // Process the received data (list of recipes) from the server
//                 console.log(data);
//                 // Perform further operations with the recipe data
//                 // Redirect to the Recipe List Page or update the current page content
//             })
//             .catch(error => {
//                 console.error('Error:', error);
//             });
//     }
//
// }
//
// // Bind the search button click event to trigger the search
// $('#searchButton').click(performSearch);

import BaseClass from "../util/baseClass";
import $ from 'jquery';
import RecipeListClient from "../api/RecipeListClient";

class RecipeListPage extends BaseClass {
    constructor() {
        super();
        this.client = new RecipeListClient();
        this.bindClassMethods(['displayRecipes', 'onGet', 'renderListPage'], this);
    }



    displayRecipes(recipes) {
        // Get the recipe list container element
        let recipeListContainer = document.getElementById('recipe-list-container');

        // Clear the existing content of the recipe list container
        recipeListContainer.innerHTML = '';

        // Iterate through the recipes and create HTML elements for each recipe
        recipes.forEach(recipe => {
            // Create a recipe card element
            let recipeCard = document.createElement('li');
            recipeCard.classList.add('recipe-card');

            // Create elements for recipe details (e.g., title, ingredients, instructions)
            let title = document.createElement('h2');
            title.textContent = recipe.title;

            let ingredients = document.createElement('ul');
            recipe.ingredients.forEach(ingredient => {
                const li = document.createElement('li');
                li.textContent = ingredient;
                ingredients.appendChild(li);
            });

            let instructions = document.createElement('p');
            instructions.textContent = recipe.instructions;

            // Append the recipe details to the recipe card
            recipeCard.appendChild(title);
            recipeCard.appendChild(ingredients);
            recipeCard.appendChild(instructions);

            // Append the recipe card to the recipe list container
            recipeListContainer.appendChild(recipeCard);
        });
    }




    async onGet(event) {
        // Prevent page refresh on submit
        event.preventDefault();

        // Get the search bar value
        let searchBar = document.getElementById("search-bar-input").value;

        // Call the backend API to fetch recipes based on the search query
        try {
            let recipes = await this.client.fetchRecipeData(searchBar, this.errorHandler);
            this.displayRecipes(recipes);
        } catch (error) {
            console.error('Error:', error);
        }

        const recipeList = document.getElementById("recipe-list-container");
        // const AWS = require('/recipes');
        // const dynamoDb = new AWS.DynamoDbClient;
        //
// Fetch the recipe data from the API
        fetch("/recipes")
            .then(response => response.json())
            .then(data => {
                // Process the received recipe data
                data.forEach(recipe => {
                    const listItem = document.createElement("li");
                    listItem.textContent = recipe.name;

                    listItem.addEventListener("click", () => {
                        window.open(recipe.url, "_blank");
                    });

                    recipeList.appendChild(listItem);
                });
            })
            .catch(error => {
                console.error("Error fetching recipe data:", error);
            });

    }
}

// Create an instance of the RecipeListPage class
const recipeListPage = new RecipeListPage();

// Bind the search button click event to trigger the search
$('#search-recipe').on('submit', (event) => {
    recipeListPage.onGet(event);
});
