// Get references to the form inputs
 var searchTypeInput = document.getElementById('searchType');
var airportInput = document.getElementById('airport');
var startTimeInput = document.getElementById('startTime');
var endTimeInput = document.getElementById('endTime');
 
 // Event listener to handle form submission
document.getElementById('searchForm').addEventListener('submit', function (event) {
    event.preventDefault(); // Prevent the default form submission
    // Get the values from the form inputs
    var searchTypeValue = searchTypeInput.value;
    var airportValue = airportInput.value;
    var startTimeValue = startTimeInput.value;
    var endTimeValue = endTimeInput.value;
     
    
    
    // Function to format a date as MM-dd-yyyy HH:mm:ss
function formatDateTime(date) {
    var month = String(date.getMonth() + 1).padStart(2, '0'); // Month is zero-based
    var day = String(date.getDate()).padStart(2, '0');
    var year = date.getFullYear();
    var hours = String(date.getHours()).padStart(2, '0');
    var minutes = String(date.getMinutes()).padStart(2, '0');
    var seconds = String(date.getSeconds()).padStart(2, '0');

    // Create the formatted date and time string
    var formattedDateTime = month + '-' + day + '-' + year + ' ' + hours + ':' + minutes + ':' + seconds;

    return formattedDateTime;
}

    // Parse the values as Date objects
    var startTime = new Date(startTimeValue);
    var endTime = new Date(endTimeValue);

    // Format the dates as MM-dd-yyyy HH:mm:ss
    var formattedStartTime = formatDateTime(startTime);
    var formattedEndTime = formatDateTime(endTime);

    // Log the formatted dates
    console.log('Formatted Start Time:', formattedStartTime);
    console.log('Formatted End Time:', formattedEndTime);

    // Create a data object to send to the server
  // Create a data object to send to the server
var data = {
	searchType: searchTypeValue,
    airport: airportValue,
    startTime: formattedStartTime,
    endTime: formattedEndTime
    
};
console.log('Data to be sent:', data);

// Send the data to the server using a fetch request
fetch('/airportArrivalSearch', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
    },
    body: JSON.stringify(data),
})
    .then(response => {
        // Check if the response is successful (status code 200)
        if (response.ok) {
			if (response.status === 404) {
                // Redirect to the custom error page
                window.location.href = "/404error.html";
                event.preventDefault(); // Prevent the form from submitting
            }if (response.status === 502) {
                // Redirect to the custom error page
                alert("502 error Bad gateway")
                event.preventDefault(); // Prevent the form from submitting
            }
            // Parse the response as JSON
            return response.json();
        } else {
            // Handle non-JSON response (e.g., HTML or other content)
            return response.text(); // Use text() to read the response body
        }
    })
    .then(data => {
        if (typeof data === 'object') {
            // Handle JSON response data here
            console.log('Server Response (JSON):', data);
        } else {
            // Handle non-JSON response here
            console.log('Server Response (Text/HTML):', data);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
 });
// Add an event listener to the airport input
var airportInput = document.getElementById("airport");
airportInput.addEventListener("input", function() {
	// Convert the input value to uppercase
	this.value = this.value.toUpperCase();
});

// Add an event listener to the form for validation and submission
document.getElementById("searchForm").addEventListener("submit", function(event) {
	var airportInput = document.getElementById("airport");
	var startInput = document.getElementById("startTime"); // Update to use the date input field
	var endInput = document.getElementById("endTime");     // Update to use the date input field

	// Validate Airport (4-letter ICAO code)
	var airportPattern = /^[A-Z]{4}$/;
	if (!airportPattern.test(airportInput.value.trim())) {
		alert("Airport must be a 4-letter ICAO code all in uppercase.");
		event.preventDefault();
		return false;
	}

	// Convert start and end dates to Date objects for comparison
	var startDate = new Date(startInput.value);
	var endDate = new Date(endInput.value);

	// Check if the end date is before the start date
	if (endDate <= startDate) {
		alert("End Date must be after Start Date.");
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
// Function to update the progress bar
    function updateProgressBar(percentage) {
        const progressBar = document.querySelector('.progress-bar');
        progressBar.style.width = percentage + '%';
        progressBar.textContent = percentage + '%';
    }

    // Add an event listener for the form submission
    document.getElementById('searchForm').addEventListener('submit', function (event) {
        event.preventDefault(); // Prevent the form from submitting immediately

        // Show the progress bar
        const progressBar = document.querySelector('.progress');
        progressBar.style.display = 'block';

        // Simulate loading progress (you should replace this with your actual data loading logic)
        let progress = 0;
        const interval = setInterval(function () {
            if (progress >= 100) {
                clearInterval(interval);
                progressBar.style.display = 'none'; // Hide the progress bar when done
                return;
            }
            progress += 10; // Simulated progress increment
            updateProgressBar(progress);
        }, 500); // Adjust the interval as needed for your data loading time
    });
 



