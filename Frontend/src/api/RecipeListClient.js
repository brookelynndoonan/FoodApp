// import BaseClass from "../util/baseClass";
// import axios from 'axios';
//
// export default class RecipeListClient extends BaseClass {
//     constructor(props = {}) {
//         super();
//         this.bindClassMethods(['fetchRecipeData'], this);
//         this.props = props;
//         this.clientLoaded(axios);
//     }
//
//     clientLoaded(client) {
//         this.client = client;
//         if (this.props.hasOwnProperty("onReady")) {
//             this.props.onReady();
//         }
//     }
//
//     async getRecipe(id, errorCallback) {
//         try {
//             const response = await this.client.get(`/recipes/${id}`);
//             return response.data;
//         } catch (error) {
//             this.handleError("getRecipe", error, errorCallback)
//         }
//     }
//
//     async fetchRecipeData() {
//         try {
//             const response = await this.client.get('/recipes/');
//             return response.data;
//         } catch (error) {
//             this.handleError("fetchRecipeData", error);
//             return null;
//         }
//     }
//
//     handleError(method, error) {
//         console.error(method + " failed - " + error);
//         if (error.response && error.response.data.message !== undefined) {
//             console.error(error.response.data.message);
//         }
//     }
// }

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
