// JavaScript code for saving changes and confirming deletion
function saveChanges() {
    if (confirm("Are you sure you want to save changes?")) {
        var formData = new FormData(document.getElementById("editForm"));
        var searchId = document.querySelector('input[name="id"]').value;
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/search/save/" + searchId, true);
        xhr.onload = function () {
            if (xhr.status === 200) {
                alert("Changes saved successfully!");
                // Optionally perform additional actions after saving changes
            } else {
                alert("Failed to save changes. Please try again.");
            }
        };
        xhr.send(formData);
    }
}

function confirmDelete() {
    if (confirm("Are you sure you want to delete this search?")) {
        var searchId = document.querySelector('input[name="id"]').value;

        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/search/delete/" + searchId, true);
        xhr.onload = function () {
            if (xhr.status === 200) {
                alert("Search deleted successfully!");
                // Optionally perform additional actions after deleting the search
            } else {
                alert("Failed to delete search. Please try again.");
            }
        };
        xhr.send();
    }
}

// JavaScript code for validating and formatting input fields
document.getElementById("searchForm").addEventListener("submit", function(event) {
    var searchTypeInput = document.getElementById("searchtype");
    var airportInput = document.getElementById("airport");
    var startInput = document.getElementById("start");
    var endInput = document.getElementById("end");

    // ... (rest of the validation code)

    // Rest of the code for checking time interval

});
