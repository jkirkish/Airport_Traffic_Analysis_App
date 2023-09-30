
  document.addEventListener("DOMContentLoaded", function () {
    // Get a reference to the registration form and the registrationDate field
    var registrationForm = document.getElementById("registrationForm");
    var registrationDateField = registrationForm.querySelector("input[name='registrationDate']");

    // Function to set the current date
    function setCurrentDate() {
      var currentDate = new Date();
      var formattedDate = currentDate.toISOString().split("T")[0]; // Get YYYY-MM-DD format

      // Set the current date to the registrationDate field
      registrationDateField.value = formattedDate;

      // Manually submit the form
      registrationForm.submit();
    }

    // Add an event listener to the Register button
    var registrationDateField = registrationForm.querySelector("input[name='registrationDate']");
    registerButton.addEventListener("click", function (event) {
      setCurrentDate();
    });
  });
  
document.addEventListener("DOMContentLoaded", function () {
  // Get a reference to the "Registration Date" input field
  var registrationDateField = document.getElementById("registrationDate");

  // Function to set the current date
  function setCurrentDate() {
    var currentDate = new Date();
    var formattedDate = currentDate.toISOString().split("T")[0]; // Get YYYY-MM-DD format

    // Set the current date to the "Registration Date" field
    registrationDateField.value = formattedDate;
  }

  // Call the setCurrentDate function to set the initial value
  setCurrentDate();
});
