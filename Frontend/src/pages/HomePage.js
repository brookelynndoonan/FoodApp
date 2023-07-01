import BaseClass from "../util/baseClass";


class HomePage extends BaseClass {
    constructor() {
        super();
        this.bindClassMethods(['onGet', 'onCreate', 'renderHomePage'], this);
    }

    async mount() {
        document.getElementById().addEventListener("submit", this.onGet)

    }
}