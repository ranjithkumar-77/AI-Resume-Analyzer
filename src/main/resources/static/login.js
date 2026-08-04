const API_URL = "http://localhost:8080/api/users";

async function login() {

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    if (email.trim() === "" || password.trim() === "") {
        alert("Please enter Email and Password");
        return;
    }

    try {

        const response = await fetch(API_URL + "/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email: email,
                password: password
            })
        });

        if (!response.ok) {
            alert("Invalid Email or Password");
            return;
        }

        const user = await response.json();

        localStorage.setItem("userEmail", user.email);
        localStorage.setItem("userName", user.name || user.email);

        alert("Login Successful");

        window.location.href = "dashboard.html";

    } catch (error) {

        console.log(error);

        alert("Unable to connect to server.");
    }
}