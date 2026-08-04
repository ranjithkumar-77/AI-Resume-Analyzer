package com.ranjith.resumeanalyzer.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ranjith.resumeanalyzer.dto.AnalysisResult;
import com.ranjith.resumeanalyzer.dto.JobRecommendation;

@Service
public class AIAnalyzerService {

    @Autowired
    private SkillExtractorService skillExtractorService;

    @Autowired
    private ResumeImproverService resumeImproverService;

    @Autowired
    private TextNormalizerService textNormalizerService;

    @Autowired
    private JobRecommendationService jobRecommendationService;

    @Autowired
    private CompanyATSService companyATSService;
    @Autowired
private JobRoleService jobRoleService;

    public AnalysisResult analyzeResume(String resumeText, String jobDescription) {

        return analyzeResume(resumeText, jobDescription, null);

    }

    // Overload with optional company-specific ATS adjustments
    public AnalysisResult analyzeResume(String resumeText, String jobDescription, String companyKey) {

        // ==========================
        // Extract Job Skills
        // ==========================

       // ==========================
// Extract Skills
// ==========================

List<String> jobSkills = jobRoleService.getSkills(jobDescription);

if (jobSkills.isEmpty()) {
    jobSkills = skillExtractorService.extractSkills(jobDescription);
}

List<String> resumeSkills =
        skillExtractorService.extractSkills(resumeText);
        int matched = 0;

        Set<String> missingSkills = new HashSet<>();

        StringBuilder analysis = new StringBuilder();

        analysis.append("=========== AI Resume Analysis ===========\n\n");

        // ==========================
        // Detected Skills
        // ==========================

       analysis.append("Resume Skills\n");

if (resumeSkills.isEmpty()) {

    analysis.append("No technical skills detected.\n");

} else {

    for (String skill : resumeSkills) {

        analysis.append("✔ ").append(skill).append("\n");

    }

}

analysis.append("\n");

analysis.append("Job Description Skills\n");

if (jobSkills.isEmpty()) {

    analysis.append("No job skills found.\n");

} else {

    for (String skill : jobSkills) {

        analysis.append("• ").append(skill).append("\n");

    }

}

analysis.append("\n");

        // ==========================
        // Matched Skills
        // ==========================

        analysis.append("Matched Skills\n");
        for (String skill : jobSkills) {

    boolean found = textNormalizerService.containsSkill(resumeText, skill);

    System.out.println("----------------------------");
    System.out.println("Skill : " + skill);
    System.out.println("Found : " + found);

    if (found) {

        matched++;

        analysis.append("✔ ").append(skill).append("\n");

    } else {

        missingSkills.add(skill);

    }

}
        analysis.append("\n");

        // ==========================
        // Missing Skills
        // ==========================

        analysis.append("Missing Skills\n");

        if (missingSkills.isEmpty()) {

            analysis.append("None\n");

        } else {

            for (String skill : missingSkills) {

                analysis.append("✘ ").append(skill).append("\n");

            }

        }

        // ==========================
        // Match Score
        // ==========================

        int matchScore = 0;

        if (!jobSkills.isEmpty()) {

            matchScore = matched * 100 / jobSkills.size();

        }

     // ==========================
// ATS Score
// ==========================

int atsScore = 0;

String resume = resumeText.toLowerCase();

// -------------------------
// 1. Skill Match (40 Marks)
// -------------------------

atsScore += (matchScore * 40) / 100;

// -------------------------
// 2. Education (10)
// -------------------------

if (resume.contains("education")) {
    atsScore += 10;
}

// -------------------------
// 3. Experience (15)
// -------------------------

if (resume.contains("experience") ||
    resume.contains("internship")) {

    atsScore += 15;
}

// -------------------------
// 4. Projects (10)
// -------------------------

if (resume.contains("project")) {
    atsScore += 10;
}

// -------------------------
// 5. Certifications (5)
// -------------------------

if (resume.contains("certification")) {
    atsScore += 5;
}

// -------------------------
// 6. Contact Details (5)
// -------------------------

if (resume.contains("@")) {
    atsScore += 2;
}

if (resume.matches("(?s).*\\d{10}.*")) {
    atsScore += 3;
}

// -------------------------
// 7. Resume Length (5)
// -------------------------

int words = resume.split("\\s+").length;

if (words >= 300) {
    atsScore += 5;
}

// -------------------------
// 8. GitHub / LinkedIn (5)
// -------------------------

if (resume.contains("github") ||
    resume.contains("linkedin")) {

    atsScore += 5;
}

// -------------------------
// 9. Company Bonus (5)
// -------------------------

if (companyKey != null && !companyKey.isBlank()) {

    try {

        List<String> rules =
                companyATSService.getCompanyRules(companyKey);

        for (String rule : rules) {

            if (resume.contains(rule.toLowerCase())) {

                atsScore += 5;
                break;
            }
        }

    } catch (Exception e) {

        e.printStackTrace();

    }

}


if (atsScore > 100) {
    atsScore = 100;
}
// ==========================
// Resume Strength
// ==========================

String resumeStrength;

if (atsScore >= 90) {
    resumeStrength = "★★★★★ Outstanding";
} else if (atsScore >= 80) {
    resumeStrength = "★★★★☆ Strong";
} else if (atsScore >= 70) {
    resumeStrength = "★★★☆☆ Good";
} else if (atsScore >= 60) {
    resumeStrength = "★★☆☆☆ Needs Improvement";
} else {
    resumeStrength = "★☆☆☆☆ Poor";
}
System.out.println("========== ATS DEBUG ==========");
System.out.println("Match Score : " + matchScore);
System.out.println("ATS Score   : " + atsScore);
System.out.println("================================");
// Show Scores in Analysis
analysis.append("\nATS Score : ")
        .append(atsScore)
        .append("/100");

analysis.append("\nJob Match Score : ")
        .append(matchScore)
        .append("%");
        analysis.append("\nResume Strength : ")
        .append(resumeStrength);

// ==========================
// Suggestions
// ==========================

analysis.append("\n\nSuggestions\n");
        if (missingSkills.isEmpty()) {

            analysis.append("Excellent! Your resume matches the Job Description.\n");

        } else {

            for (String skill : missingSkills) {

                analysis.append("• Add ")
                        .append(skill)
                        .append(" to your resume.\n");

            }

        }

        // ==========================
        // Improved Resume
        // ==========================

        String improvedResume =
                resumeImproverService.improveResume(
                        resumeText,
                        jobDescription);

        // ==========================
// Job Recommendation
// ==========================
JobRecommendation recommendation =
        jobRecommendationService.recommendJobs(
                resumeText,
                jobDescription
        );
List<String> recommendedJobs =
        recommendation.getRecommendedJobs();

List<String> recommendedCourses =
        recommendation.getRecommendedCourses();

List<String> missingSkillsList =
        new ArrayList<>(missingSkills);

for (String skill : recommendation.getMissingSkills()) {

    if (!missingSkillsList.contains(skill)) {

        missingSkillsList.add(skill);

    }

}

        // ==========================
        // Return Result
        // ==========================
return new AnalysisResult(

        atsScore + "/100",

        matchScore + "%",

        analysis.toString(),

        resumeText,

        improvedResume,

        resumeStrength,

        recommendedJobs,

        missingSkillsList,

        recommendedCourses

);
    }
}