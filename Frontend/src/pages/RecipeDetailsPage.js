import BaseClass from "../util/baseClass";
import RecipeListClient from "../api/RecipeListClient.js"



class RecipeDetailsPage extends BaseClass {
    constructor() {
        super();
        this.client = new RecipeListClient();
        this.bindClassMethods([]);
    }
}
