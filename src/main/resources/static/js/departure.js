
function goToDeparturesPage() {
    window.location.href = "/departures";
}

function deleteDeparture() {
    // Retrieve the departure ID
    var departureId = document.querySelector('span[data-departure-id]').innerText;

    // Perform an AJAX request to delete the departure
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/departures/delete/" + departureId, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onload = function () {
        // Handle the response from the server
        if (xhr.status === 200) {
            alert("Departure deleted successfully!");
            // Redirect to the departures page
            window.location.href = "/departures";
        } else {
            alert("Failed to delete departure. Please try again.");
        }
    };
    xhr.send();
}
