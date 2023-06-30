import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import ExampleClient from "./exampleClient";
/**
 * some sort of api goes here
 */

class NewRecipePage extends BaseClass {
    constructor() {
        super();
        this.bindClassMethods(['onGet', 'onCreate', 'renderExample'], this);
    }

    async mount() {
        document.getElementById('get-name-form').addEventListener('submit', this.onGet);
        document.getElementById('get-description-form').addEventListener('submit', this.onGet);
        document.getElementById('add-ingredients-form').addEventListener('submit', this.onCreate);
        document.getElementById('add-directions-form').addEventListener('submit', this.onCreate);

        document.getElementById('create-form').addEventListener('submit', this.onCreate);
        this.client = new ExampleClient();

        this.dataStore.addChangeListener(this.renderExample)
    }


    async renderExample() {
        let resultArea = document.getElementById("result-info");

        const example = this.dataStore.get("example");

        if (example) {
            resultArea.innerHTML = `
                <div>ID: ${example.id}</div>
                <div>Name: ${example.name}</div>
            `
        } else {
            resultArea.innerHTML = "No Item";
        }
    }

    async onGet(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let id = document.getElementById("id-field").value;
        this.dataStore.set("example", null);

        let result = await this.client.getExample(id, this.errorHandler);
        this.dataStore.set("example", result);
        if (result) {
            this.showMessage(`Got ${result.name}!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }

    async onCreate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();
        this.dataStore.set("example", null);

        let name = document.getElementById("create-name-field").value;

        const createdRecipe = await this.client.createExample(name, this.errorHandler);
        this.dataStore.set("example", createdRecipe);

        if (createdRecipe) {
            this.showMessage(`Created ${createdRecipe.name}!`)
        } else {
            this.errorHandler("Error creating!  Try again...");
        }
    }

      addTextBox() {

        let container = document.getElementById("category-dropdown");
        let newTextBox = document.createElement("form");
        newTextBox.classList.add("category-dropdown");

        let input = document.createElement("input");
        input.type = "text";
        input.classList.add("textbox");
        input.placeholder = "Enter text...";

        newTextBox.appendChild(input);
        container.appendChild(newTextBox);
    }


    /* add method that submits new recipe to recipe page */

    /* additional methods here */



}