import BaseClass from "../util/baseClass";


class HomePage extends BaseClass {
    constructor() {
        super();
        this.bindClassMethods(['onGet', 'onCreate', 'renderHomePage'], this);
    }

    async mount() {
        document.getElementById("form").addEventListener("submit", this.onGet);
        document.getElementById("get-by-id-form").addEventListener("submit",this.onGet);

    }

    async renderPage() {

            // Get the head element
            // const head = document.getElementsByTagName('head')[0];
            //
            // // Create script elements for JavaScript files
            // const script1 = document.createElement('script');
            // script1.type = 'text/javascript';
            // script1.src = 'HomePage.js';
            //
            // const script2 = document.createElement('script');
            // script2.type = 'text/javascript';
            // script2.src = 'CreateRecipePage.js';
            //
            // // Append the script elements to the head
            // head.appendChild(script1);
            // head.appendChild(script2);
            //
            // // Create link element for CSS file
            // const link = document.createElement('link');
            // // link.rel = 'stylesheet';
            // // link.type = 'text/css';
            // // link.href = 'css/style.css';
            //
            // // Append the link element to the head
            // head.appendChild(link);

            // Get the body element
            const body = document.getElementsByTagName('body')[0];

            // Create header element
            const header = document.createElement('header');
            header.className = 'header';
            header.id = 'header';

            // Create div element for site title
            const siteTitle = document.createElement('div');
            siteTitle.className = 'site-title';

            // Create h1 element for title
            const h1 = document.createElement('h1');
            h1.innerText = 'Foodie';

            // Create h5 element for subtitle
            const h5 = document.createElement('h5');
            h5.innerText = 'Foodie. Where people go to find and make meals that slap';

            siteTitle.appendChild(h1);
            siteTitle.appendChild(h5);
            header.appendChild(siteTitle);
            body.appendChild(header);

            const searchCard = document.createElement('div');
            searchCard.className = 'card';

            const searchTitle = document.createElement('h2');
            searchTitle.innerText = 'Find your meal here';

            // Create form element for search bar
            const searchForm = document.createElement('form');
            searchForm.id = 'form';
            searchForm.role = 'search';

            // Create input element for search bar
            const searchBar = document.createElement('input');
            searchBar.type = 'search';
            searchBar.id = 'search-bar';
            searchBar.required = true;
            searchBar.className = 'validated-field';
            searchBar.name = 'q';
            searchBar.placeholder = 'Search...';

            // Create select element for filter dropdown
            const filterDropdown = document.createElement('select');
            filterDropdown.className = 'category-dropdown';
            filterDropdown.id = 'category-dropdown';

            // Create options for the filter dropdown
            const dietaryRestrictions = document.createElement('option');
            dietaryRestrictions.value = '';
            dietaryRestrictions.innerText = 'Dietary Restrictions';

            const dairyFree = document.createElement('option');
            dairyFree.value = 'Dairy Free';
            dairyFree.innerText = 'Dairy Free';

            const glutenFree = document.createElement('option');
            glutenFree.value = 'Gluten Free';
            glutenFree.innerText = 'Gluten Free';

            const vegan = document.createElement('option');
            vegan.value = 'Vegan';
            vegan.innerText = 'Vegan';

            // Append options to the filter dropdown
            filterDropdown.appendChild(dietaryRestrictions);
            filterDropdown.appendChild(dairyFree);
            filterDropdown.appendChild(glutenFree);
            filterDropdown.appendChild(vegan);

            // Create button element for search
            const searchButton = document.createElement('button');
            searchButton.innerText = '';
        }

    async onGet(event) {
        event.preventDefault();
        //search bar
        let searchBar = document.getElementById("search-bar").value;
        //category dropdown
        let categoryDropdown = document.getElementById("category-dropdown").value;

        const queryString = `q=${searchBar}&category=${categoryDropdown}`;
        const url = `searchEndpoint?${queryString}`;

        fetch('http://localhost:5001/api/recipes')
            .then(response => response.json())
            .then(data => {
                // Process the received data (list of recipes) from the server
                console.log(data);
                // Perform further operations with the recipe data
                // Redirect to the Recipe List Page or update the current page content
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }



    //
    // async onCreate(){
    //
    //
    // }
}
    const main = async () => {
            const homePage = new HomePage();
            homePage.mount();
        };

        window.addEventListener('DOMContentLoaded', main);
