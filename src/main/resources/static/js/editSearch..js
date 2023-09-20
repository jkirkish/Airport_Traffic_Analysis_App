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

document.getElementById("searchForm").addEventListener("submit", function(event) {
    var searchTypeInput = document.getElementById("searchtype");
    var airportInput = document.getElementById("airport");
    var startInput = document.getElementById("start");
    var endInput = document.getElementById("end");

    // Validate Search Type
    var searchType = searchTypeInput.value.trim().toLowerCase();
    if (searchType !== "arrival" && searchType !== "departure") {
        alert("Search Type must be either 'Arrival' or 'Departure'");
        event.preventDefault();
        return false;
    }

    // Validate Airport (4-letter ICAO code)
    var airportPattern = /^[A-Z]{4}$/;
    if (!airportPattern.test(airportInput.value.trim())) {
        alert("Airport must be a 4-letter ICAO code");
        event.preventDefault();
        return false;
    }

    // Validate Date and Time Format (MM-dd-yyyy HH:mm:ss)
    var dateTimePattern = /^\d{2}-\d{2}-\d{4} \d{2}:\d{2}:\d{2}$/;
    if (!dateTimePattern.test(startInput.value.trim()) || !dateTimePattern.test(endInput.value.trim())) {
        alert("Start and End Time must be in the format 'MM-dd-yyyy HH:mm:ss'");
        event.preventDefault();
        return false;
    }
    // Convert start and end times to Date objects for comparison
    var startDate = new Date(startInput.value);
    var endDate = new Date(endInput.value);

    // Calculate the time difference in milliseconds
    var timeDifference = endDate - startDate;
    var daysDifference = timeDifference / (1000 * 60 * 60 * 24);

    // Check if the time interval is more than 7 days
    if (daysDifference > 7) {
        alert("Time interval must not be more than 7 days apart");
        event.preventDefault();
        return false;
    }
});

// Function to redirect based on search type
function redirectToPage(searchType) {
    searchType = searchType.toLowerCase();
    if (searchType === 'arrival' || searchType === 'departure') {
        window.location.href = '/' + searchType + 's'; // Redirect to either arrivals or departures
    } else {
        alert("Invalid search type");
    }
}

