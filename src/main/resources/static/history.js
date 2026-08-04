const API_URL = "/api/resumes";

loadHistory();

async function loadHistory() {

    const email = localStorage.getItem("userEmail");

    const response = await fetch(
        API_URL + "/user/" + encodeURIComponent(email)
    );

    const resumes = await response.json();

    let html = "";

    resumes.forEach((resume, index) => {
        const displayIndex = index + 1;

        html += `
        <tr>
            <td>${displayIndex}</td>
            <td>${resume.resumeFile}</td>
            <td>${resume.atsScore}</td>
            <td>${resume.matchScore}</td>
            <td>
                <button onclick="downloadResume(${resume.id})">
                    Resume
                </button>
                <button onclick="downloadReport(${resume.id})">
                    Report
                </button>
            </td>
        </tr>
        `;

    });

    document.getElementById("historyTable").innerHTML = html;

}

function downloadResume(id) {

    window.open(API_URL + "/download/" + id);

}

function downloadReport(id) {

    window.open(API_URL + "/report/" + id);

}

function goDashboard() {

    window.location.href = "dashboard.html";

}