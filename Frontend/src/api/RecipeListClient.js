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

    async fetchRecipeData() {
        try {
            const response = await this.client.get('/recipes/');
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