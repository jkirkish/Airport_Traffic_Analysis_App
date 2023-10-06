document.addEventListener("DOMContentLoaded", function () {
    var deleteAllButton = document.getElementById("deleteAllButton"); // Assuming you have a button with id "deleteAllButton"
    var tableBody = document.querySelector("tbody"); // Assuming your search requests are displayed in a table within a tbody element

    deleteAllButton.addEventListener("click", function () {
        // Remove all rows from the table's tbody
        while (tableBody.firstChild) {
            tableBody.removeChild(tableBody.firstChild);
        }

        fetch('/deleteAllRequests', { 
            method: 'DELETE',
        })
        .then(response => {
            if (response.ok) {
                console.log("Successful deletion of search requests");
            } else {
                console.log("Deletion of search requests failed!");
            }
        })
        .catch(error => {
            console.log("Network error");
        });
    });
});
