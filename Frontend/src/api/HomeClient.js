import BaseClass from "../util/baseClass";
import axios from 'axios';

export default class HomeClient extends BaseClass {
    constructor(props = {}) {
        super();
        this.bindClassMethods(['fetchHomeData'], this);
        this.props = props;
        this.clientLoaded(axios);
    }

    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady();
        }
    }

    async fetchHomeData() {
        try {
            const response = await this.client.get('/home');
            return response.data;
        } catch (error) {
            this.handleError("fetchHomeData", error);
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
