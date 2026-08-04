const USER_API = "/api/users";

function setFeedback(message, isError = false) {
    const feedback = document.getElementById("authFeedback");
    if (!feedback) return;
    feedback.textContent = message;
    feedback.className = "auth-feedback" + (message ? (isError ? " error" : " success") : "");
    feedback.style.display = message ? "block" : "none";
}

function parseErrorBody(body) {
    if (!body) return "An unexpected error occurred.";
    try {
        const data = JSON.parse(body);
        return data?.message || data?.error || JSON.stringify(data);
    } catch {
        return body;
    }
}

// =======================
// Register
// =======================
async function register() {

    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    if (name === "" || email === "" || password === "") {
        setFeedback("Please fill in all fields.", true);
        return;
    }

    try {
        const response = await fetch(USER_API + "/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                name: name,
                email: email,
                password: password
            })
        });

        if (!response.ok) {
            const errorText = await response.text();
            setFeedback(parseErrorBody(errorText) || "Registration failed.", true);
            return;
        }

        setFeedback("Registration successful. Redirecting to login...", false);
        setTimeout(() => window.location.href = "login.html", 900);
    } catch (e) {
        console.error(e);
        setFeedback("Unable to connect to the server.", true);
    }
}

// =======================
// Login
// =======================
async function login() {
    setFeedback("");

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    if (email === "" || password === "") {
        setFeedback("Please enter both email and password.", true);
        return;
    }

    try {
        const response = await fetch(USER_API + "/login", {
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
            const errorText = await response.text();
            setFeedback(parseErrorBody(errorText) || "Invalid email or password.", true);
            return;
        }

        const user = await response.json();
        localStorage.setItem("userEmail", user.email);
        localStorage.setItem("userName", user.name || user.email);
        setFeedback("Login successful. Redirecting to dashboard...", false);
        setTimeout(() => window.location.href = "dashboard.html", 900);
    } catch (e) {
        console.error(e);
        setFeedback("Unable to connect to the server.", true);
    }
}

// =======================
// Forgot Password
// =======================
async function forgotPassword() {
    setFeedback("");

    const email = document.getElementById("email").value.trim();
    const newPassword = document.getElementById("newPassword").value.trim();

    if (email === "" || newPassword === "") {
        setFeedback("Please enter your email and a new password.", true);
        return;
    }

    try {
        const response = await fetch(USER_API + "/forgot-password", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email: email,
                newPassword: newPassword
            })
        });

        if (!response.ok) {
            const errorText = await response.text();
            setFeedback(parseErrorBody(errorText) || "Password reset failed.", true);
            return;
        }

        setFeedback("Password reset successfully. Redirecting to login...", false);
        setTimeout(() => window.location.href = "login.html", 1200);
    } catch (e) {
        console.error(e);
        setFeedback("Unable to connect to the server.", true);
    }
}
