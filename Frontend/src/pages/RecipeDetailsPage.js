import BaseClass from "../util/baseClass";
import RecipeListClient from "../api/RecipeListClient";



class RecipeDetailsPage extends BaseClass {
    constructor() {
        super();
        this.client = new RecipeListClient();
        this.bindClassMethods([]);
    }
}
