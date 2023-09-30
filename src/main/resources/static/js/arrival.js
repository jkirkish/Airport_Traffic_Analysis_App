 
    function goToArrivalsPage() {
        window.location.href = "/arrivals";
    }

    function deleteArrival() {
        // Retrieve the arrival ID
        var arrivalId = document.querySelector('span[data-arrival-id]').innerText;

        // Perform an AJAX request to delete the arrival
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/arrivals/delete/" + arrivalId, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onload = function () {
            // Handle the response from the server
            if (xhr.status === 200) {
                alert("Arrival deleted successfully!");
                // Redirect to the arrivals page
                window.location.href = "/arrivals";
            } else {
                alert("Failed to delete arrival. Please try again.");
            }
        };
        xhr.send();
    }
    