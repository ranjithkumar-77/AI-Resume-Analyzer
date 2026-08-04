package com.ranjith.resumeanalyzer.builder;

import org.springframework.stereotype.Service;
import com.ranjith.resumeanalyzer.entity.Resume;

@Service
public class ResumeBuilderService {

    public String generateResume(Resume resume) {
        return generateResume(resume, "modern");
    }

    public String generateResume(Resume resume, String template) {

        if (template == null || template.isBlank()) {
            template = "modern";
        }

        // Save selected template
        resume.setTemplate(template);

        switch (template.toLowerCase()) {

            case "classic":
                return classicTemplate(resume);

            case "executive":
                return executiveTemplate(resume);

            default:
                return modernTemplate(resume);
        }
    }

    // ======================================================
    // MODERN TEMPLATE
    // ======================================================

    private String modernTemplate(Resume r) {

        StringBuilder sb = new StringBuilder();

        sb.append("====================================================\n");
        sb.append("                 MODERN RESUME\n");
        sb.append("====================================================\n\n");

        sb.append(empty(r.getFullName())).append("\n");
        sb.append(empty(r.getEmail())).append("\n");
        sb.append(empty(r.getPhone())).append("\n\n");

        sb.append("PROFESSIONAL SUMMARY\n");
        sb.append("----------------------------------------\n");
        sb.append(empty(r.getSummary())).append("\n\n");

        sb.append("TECHNICAL SKILLS\n");
        sb.append("----------------------------------------\n");
        addBullets(sb, r.getSkills());

        sb.append("\nEDUCATION\n");
        sb.append("----------------------------------------\n");
        sb.append(empty(r.getEducation())).append("\n\n");

        sb.append("EXPERIENCE\n");
        sb.append("----------------------------------------\n");
        sb.append(empty(r.getExperience())).append("\n\n");

        sb.append("PROJECTS\n");
        sb.append("----------------------------------------\n");
        sb.append(empty(r.getProjects())).append("\n\n");

        sb.append("CERTIFICATIONS\n");
        sb.append("----------------------------------------\n");
        sb.append(empty(r.getCertifications())).append("\n\n");

        sb.append("ACHIEVEMENTS\n");
        sb.append("----------------------------------------\n");
        sb.append(empty(r.getAchievements())).append("\n\n");

        sb.append("LANGUAGES\n");
        sb.append("----------------------------------------\n");
        addBullets(sb, r.getLanguages());

        return sb.toString();
    }

    // ======================================================
    // CLASSIC TEMPLATE
    // ======================================================

    private String classicTemplate(Resume r) {

        StringBuilder sb = new StringBuilder();

        sb.append("****************************************************\n");
        sb.append("                  CLASSIC RESUME\n");
        sb.append("****************************************************\n\n");

        sb.append("Name  : ").append(empty(r.getFullName())).append("\n");
        sb.append("Email : ").append(empty(r.getEmail())).append("\n");
        sb.append("Phone : ").append(empty(r.getPhone())).append("\n\n");

        sb.append("CAREER OBJECTIVE\n");
        sb.append("----------------------------------------\n");
        sb.append(empty(r.getSummary())).append("\n\n");

        sb.append("EDUCATION\n");
        sb.append("----------------------------------------\n");
        sb.append(empty(r.getEducation())).append("\n\n");

        sb.append("TECHNICAL SKILLS\n");
        sb.append("----------------------------------------\n");
        addBullets(sb, r.getSkills());

        sb.append("\nEXPERIENCE\n");
        sb.append("----------------------------------------\n");
        sb.append(empty(r.getExperience())).append("\n\n");

        sb.append("PROJECTS\n");
        sb.append("----------------------------------------\n");
        sb.append(empty(r.getProjects())).append("\n\n");

        sb.append("CERTIFICATIONS\n");
        sb.append("----------------------------------------\n");
        sb.append(empty(r.getCertifications())).append("\n\n");

        sb.append("ACHIEVEMENTS\n");
        sb.append("----------------------------------------\n");
        sb.append(empty(r.getAchievements())).append("\n");

        return sb.toString();
    }

    // ======================================================
    // EXECUTIVE TEMPLATE
    // ======================================================

    private String executiveTemplate(Resume r) {

        StringBuilder sb = new StringBuilder();

        sb.append("####################################################\n");
        sb.append("                EXECUTIVE RESUME\n");
        sb.append("####################################################\n\n");

        sb.append(empty(r.getFullName()).toUpperCase()).append("\n");
        sb.append(empty(r.getEmail())).append(" | ");
        sb.append(empty(r.getPhone())).append("\n\n");

        sb.append("EXECUTIVE PROFILE\n");
        sb.append("----------------------------------------\n");
        sb.append(empty(r.getSummary())).append("\n\n");

        sb.append("CORE SKILLS\n");
        sb.append("----------------------------------------\n");
        addBullets(sb, r.getSkills());

        sb.append("\nPROFESSIONAL EXPERIENCE\n");
        sb.append("----------------------------------------\n");
        sb.append(empty(r.getExperience())).append("\n\n");

        sb.append("PROJECT HIGHLIGHTS\n");
        sb.append("----------------------------------------\n");
        sb.append(empty(r.getProjects())).append("\n\n");

        sb.append("EDUCATION\n");
        sb.append("----------------------------------------\n");
        sb.append(empty(r.getEducation())).append("\n\n");

        sb.append("CERTIFICATIONS\n");
        sb.append("----------------------------------------\n");
        sb.append(empty(r.getCertifications())).append("\n\n");

        sb.append("ACHIEVEMENTS\n");
        sb.append("----------------------------------------\n");
        sb.append(empty(r.getAchievements())).append("\n");

        return sb.toString();
    }

    // ======================================================
    // COMMON METHODS
    // ======================================================

    private void addBullets(StringBuilder sb, String value) {

        if (value == null || value.isBlank()) {
            sb.append("• Not Available\n");
            return;
        }

        String[] items = value.split(",");

        for (String item : items) {
            sb.append("• ").append(item.trim()).append("\n");
        }
    }

    private String empty(String text) {

        if (text == null || text.isBlank()) {
            return "Not Available";
        }

        return text.trim();
    }
}