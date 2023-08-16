function deleteUser() {
    if (confirm('Are you sure you want to delete this user?')) {
        const userId = document.getElementById('userId').value;
        
        fetch(`/users/${userId}/delete`, {
            method: 'POST'
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to delete user');
            }
            alert('User deleted successfully');
            // Redirect to the user list page or perform any other desired action
        })
        .catch(error => {
            console.error(error);
            alert('Failed to delete user');
        });
    }
}
