
document.getElementById("searchForm").addEventListener("submit", function(event) {
    var airportInput = document.getElementById("airport");
    var startInput = document.getElementById("start");
    var endInput = document.getElementById("end");

    // Validate Airport (4-letter ICAO code)
    var airportPattern = /^[A-Z]{4}$/;
    if (!airportPattern.test(airportInput.value.trim())) {
        alert("Airport must be a 4-letter ICAO code all in uppercase.");
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

    // Check if the end date is before the start date
    if (endDate <= startDate) {
        alert("End Time must be after Start Time.");
        event.preventDefault();
        return false;
    }

    // Calculate the time difference in milliseconds
    var timeDifference = endDate - startDate;
    var daysDifference = timeDifference / (1000 * 60 * 60 * 24);

    // Check if the time interval is more than 7 days
    if (daysDifference > 7) {
        alert("Time interval must not be more than 7 days apart.");
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
document.getElementById("searchForm").addEventListener("submit", function(event) {
    // ... (your existing validation and form handling code)

    fetch("/editSearch", {
        method: "POST",
        body: new FormData(event.target),
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/x-www-form-urlencoded"
        }
    })
    .then(response => {
        if (!response.ok) {
            if (response.status === 404) {
                // Redirect to the custom error page
                window.location.href = "/404error.html";
                event.preventDefault(); // Prevent the form from submitting
            } else {
                // Handle other errors (optional)
                console.error("HTTP error:", response.status, response.statusText);
            }
        } else {
            // Process the successful response (if needed)
        }
    })
    .catch(error => {
        console.error("Fetch error:", error);
    });
});

