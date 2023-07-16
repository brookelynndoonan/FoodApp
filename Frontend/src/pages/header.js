document.addEventListener("DOMContentLoaded", function () {
    // Fetch the header content
    fetch("header.html")
        .then(response => response.text())
        .then(data => {
            // Set the header content in the header container element
            const headerContainer = document.getElementById("header-container");
            headerContainer.innerHTML = data;
        })
        .catch(error => {
            console.log("Error fetching header content:", error);
        });
});
