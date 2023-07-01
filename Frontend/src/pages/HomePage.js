import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import HomeClient from "./HomeClient";

class HomePage extends BaseClass {
    constructor() {
        super();
        this.bindClassMethods(['onGet', 'onCreate', 'renderHomePage'], this);
    }

    async mount() {
        document.getElementById().addEventListener("submit", this.onGet)

    }
}