
document.addEventListener("DOMContentLoaded", function () {
    var deleteAllButton = document.getElementById("deleteAllButton");
    var tableBody = document.querySelector("tbody");

    deleteAllButton.addEventListener("click", function () {
        // Remove all rows from the table's tbody
        while (tableBody.firstChild) {
            tableBody.removeChild(tableBody.firstChild);
        }

        
         fetch('/deleteAllArrivals', {
             method: 'DELETE',
         })
         .then(response => {
             if (response.ok) {
                 console.log("successful deletion")
             } else {
            	 console.log("Deletion failed!")
             }
         })
         .catch(error => {
            console.log("network error")
         });
    });
});
