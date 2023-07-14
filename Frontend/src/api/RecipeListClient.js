import BaseClass from "../util/baseClass";
import axios from 'axios';

export default class RecipeListClient extends BaseClass {
    constructor(props = {}) {
        super();
        this.bindClassMethods(['fetchRecipeData'], this);
        this.props = props;
        this.clientLoaded(axios);
    }

    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady();
        }
    }

    async fetchRecipeData(searchBar) {
        try {
            const response = await this.client.get('/recipes', {
                params: {
                    q: searchBar
                }
            });
            return response.data;
        } catch (error) {
            this.handleError("fetchRecipeData", error);
            return null;
        }
    }

    handleError(method, error) {
        console.error(method + " failed - " + error);
        if (error.response && error.response.data.message !== undefined) {
            console.error(error.response.data.message);
        }
    }
}

// Fetch the recipe data from the API
fetch("/recipes")
    .then(response => response.json())
    .then(data => {
        // Process the received recipe data
        const recipeList = document.getElementById("recipe-list-container");
        data.forEach(recipe => {
            const listItem = document.createElement("li");
            listItem.textContent = recipe.title;
            recipeList.appendChild(listItem);
        });
    })
    .catch(error => {
        console.error("Error fetching recipe data:", error);
    });
