const API_URL = "/api/resumes";

let currentResumeId = 1;
// =============================
// Select Resume Template
// =============================

function selectTemplate(card, template) {

    // Remove active class from all cards
    document.querySelectorAll(".template-card").forEach(c => {
        c.classList.remove("active");
    });

    // Highlight selected card
    card.classList.add("active");

    // Save selected template
    document.getElementById("template").value = template;
    document.getElementById("selectedTemplate").textContent = template.charAt(0).toUpperCase() + template.slice(1);
}

async function loadingAnimation(){

    const text=document.getElementById("loadingText");

    const bar=document.getElementById("progressBar");

    text.innerHTML="📤 Uploading Resume...";
    bar.style.width="15%";
    await new Promise(r=>setTimeout(r,500));

    text.innerHTML="📄 Extracting PDF...";
    bar.style.width="30%";
    await new Promise(r=>setTimeout(r,500));

    text.innerHTML="🔍 Reading Resume...";
    bar.style.width="45%";
    await new Promise(r=>setTimeout(r,500));

    text.innerHTML="🧠 Matching Skills...";
    bar.style.width="65%";
    await new Promise(r=>setTimeout(r,500));

    text.innerHTML="📊 Calculating ATS...";
    bar.style.width="80%";
    await new Promise(r=>setTimeout(r,500));

    text.innerHTML="✨ Improving Resume...";
    bar.style.width="90%";
    await new Promise(r=>setTimeout(r,500));

    text.innerHTML="📄 Generating Report...";
    bar.style.width="100%";
    await new Promise(r=>setTimeout(r,500));

}
// =============================
// Analyze Resume
// =============================
async function analyzeResume() {

    const file = document.getElementById("resumeFile").files[0];
   const jobDescription = document.getElementById("jobDescription").value;
   const template = document.getElementById("template").value;
   console.log("Selected Template:", template);
   const email = localStorage.getItem("userEmail");

    if (!file) {
        alert("Please select Resume PDF");
        return;
    }

    if (jobDescription.trim() === "") {
        alert("Please Enter Job Description");
        return;
    }

    const formData = new FormData();

    formData.append("file", file);
    formData.append("jobDescription", jobDescription);
formData.append("template", template);
formData.append("email", email);
const company = document.getElementById('companySelect') ? document.getElementById('companySelect').value : '';
formData.append("company", company);
   document.getElementById("loading").style.display = "flex";

const loading = loadingAnimation();
   try {

    const response = await fetch(API_URL + "/upload", {
        method: "POST",
        body: formData
    });

    await loading;

    if (!response.ok) {
        throw new Error("Upload Failed");
    }

    const data = await response.json();
    currentResumeId = data.resumeId;

    document.getElementById("result").style.display = "block";

    const ats = document.getElementById("atsScore");
    ats.innerHTML = data.atsScore;

    const score = parseInt(data.atsScore);

    if(score >= 80){
        ats.style.color = "green";
    }else if(score >= 60){
        ats.style.color = "orange";
    }else{
        ats.style.color = "red";
    }

    const match = document.getElementById("matchScore");
    match.innerHTML = data.matchScore;

    const matchValue = parseInt(data.matchScore);

    if(matchValue >= 80){
        match.style.color = "green";
    }else if(matchValue >= 60){
        match.style.color = "orange";
    }else{
        match.style.color = "red";
    }

    document.getElementById("analysis").textContent = data.analysis;
    document.getElementById("improvedResume").textContent = data.improvedResume;
    document.querySelector(".analysis-panel").style.display = "grid";
 
    const analysis = data.analysis.toLowerCase();

// Skills Found
const foundSkills = (analysis.match(/✔/g) || []).length;
document.getElementById("skillsFound").innerHTML = foundSkills;

// Missing Skills
const missingSkills = data.missingSkills ? data.missingSkills.length : (analysis.match(/✘/g) || []).length;
document.getElementById("missingSkills").innerHTML = missingSkills;

// ===========================
// Recommended Jobs
// ===========================

let jobs = [];
if (data.recommendedJobs && data.recommendedJobs.length > 0) {
    jobs = data.recommendedJobs.map(job => `💼 ${job}`);
} else {
    jobs = ["💼 Software Developer"];
}

document.getElementById("recommendedJobs").innerHTML = jobs.join("<br>");


// ===========================
// Recommended Courses
// ===========================

let courses = [];
if (data.recommendedCourses && data.recommendedCourses.length > 0) {
    courses = data.recommendedCourses.map(course => `📚 ${course}`);
} else {
    courses = ["No additional courses needed"];
}

document.getElementById("recommendedCourses").innerHTML = courses.join("<br>");

// Recommended Courses

    await loadDashboard();
    await loadHistory();

    alert("Resume Analyzed Successfully");

} catch (e) {

    console.log(e);
    alert("Unable to Analyze Resume");

} finally {

    document.getElementById("loading").style.display = "none";

}
}
// =============================
// Copy Resume
// =============================

function copyResume() {

    navigator.clipboard.writeText(
        document.getElementById("improvedResume").textContent
    );

    alert("Copied Successfully");

}

// =============================
// Download Resume PDF
// =============================

function downloadResume(id) {

    window.open(API_URL + "/download/" + id);

}

function downloadAnalysisReport(id) {

    window.open(API_URL + "/report/" + id);

}

function downloadReport(id) {
    downloadAnalysisReport(id);
}

// =============================
// Dashboard
// =============================
async function loadDashboard() {

    try {

        const email = localStorage.getItem("userEmail");

        const response = await fetch(
            API_URL + "/stats/" + encodeURIComponent(email)
        );
        if (!response.ok) {
    throw new Error("Unable to load dashboard");
}

         const stats = await response.json();
          const historyResponse = await fetch(
    API_URL + "/user/" + encodeURIComponent(email)
);

const history = await historyResponse.json();

if (history.length > 0) {

    currentResumeId = history[history.length - 1].id;

}
        document.getElementById("total").innerHTML = stats.totalResumes;
        document.getElementById("highest").innerHTML = stats.highestATS;
        document.getElementById("average").innerHTML = stats.averageATS;

          loadChart(history);

    } catch (e) {

        console.log(e);

    }

}

// =============================
// Resume History
// =============================
async function loadHistory() {

    const email = localStorage.getItem("userEmail");

    const response = await fetch(
    API_URL + "/user/" + encodeURIComponent(email)
);

if (!response.ok) {
    throw new Error("Unable to load history");
}

    const resumes = await response.json();

    const tbody = document.getElementById("historyBody");

    tbody.innerHTML = "";

    if(resumes.length==0){

        tbody.innerHTML=`
        <tr>
            <td colspan="6">
                No Resume Found
            </td>
        </tr>
        `;

        return;
    }

    currentResumeId = resumes[resumes.length-1].id;

    resumes.forEach((r, index) => {
        const displayIndex = index + 1;

        tbody.innerHTML += `
        <tr>
            <td>${displayIndex}</td>
            <td>${r.resumeFile}</td>
            <td>${r.atsScore}</td>
            <td>${r.matchScore}</td>
            <td>
                <button onclick="downloadReport(${r.id})">
                    Download
                </button>
            </td>
            <td>
                <button onclick="deleteResume(${r.id})">
                    Delete
                </button>
            </td>
        </tr>
        `;
    });

}
// =============================
// Delete
// =============================
async function deleteResume(id){

    if(!confirm("Delete Resume?")) return;

    await fetch(API_URL+"/"+id,{
        method:"DELETE"
    });

    await loadDashboard();

    await loadHistory();

}
// =============================
// Chart
// =============================

let chart;

function loadChart(resumes) {

    const ctx = document.getElementById("atsChart").getContext("2d");

    if (chart) {
        chart.destroy();
    }

    const labels = resumes.map((r, index) => "Resume " + (index + 1));

    const scores = resumes.map(r =>
        parseInt(r.atsScore)
    );

    chart = new Chart(ctx, {

        type: "line",

        data: {

            labels: labels,

            datasets: [{

                label: "ATS Score",

                data: scores,

                borderWidth: 3,

                tension: 0.3,

                fill: false

            }]

        },

        options: {

            responsive: true,

            scales: {

                y: {

                    beginAtZero: true,

                    max: 100

                }

            }

        }

    });

}
// =============================
// Load
// =============================

window.onload = function () {

    loadCompanies();
    loadDashboard();
    loadHistory();

};

async function loadCompanies(){
    try{
        const resp = await fetch('/api/companies');
        if (!resp.ok) return;
        const data = await resp.json();
        const select = document.getElementById('companySelect');
        select.innerHTML = '<option value="">-- None --</option>';
        for (const key of Object.keys(data)){
            const opt = document.createElement('option');
            opt.value = key;
            opt.text = key.charAt(0).toUpperCase() + key.slice(1);
            select.appendChild(opt);
        }
    }catch(e){
        console.error('Failed to load companies', e);
    }
}