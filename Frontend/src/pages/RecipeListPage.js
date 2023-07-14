import $ from 'jquery';

$(document).ready(() => {
    // Fetch the recipe list using the RecipeListClient.js file
    $.getScript("js/RecipeListClient.js");
});
