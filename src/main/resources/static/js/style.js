function showAlert(message) {
    var alertBox = document.getElementById('alert');
    alertBox.style.display = 'block';
    alertBox.innerText = message;

    setTimeout(function() {
        alertBox.style.opacity = '0';
    }, 2000);

    setTimeout(function() {
        alertBox.style.display = 'none';
        alertBox.style.opacity = '1';
    }, 2500);
}