package com.ranjith.resumeanalyzer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ranjith.resumeanalyzer.dto.ResumeSection;

@Service
public class ResumeImproverService {

    @Autowired
    private SkillExtractorService skillExtractorService;

    @Autowired
    private ResumeParserService resumeParserService;

    public String improveResume(String resumeText, String jobDescription) {

        ResumeSection section = resumeParserService.parseResume(resumeText);

        List<String> jobSkills = skillExtractorService.extractSkills(jobDescription);

        StringBuilder improved = new StringBuilder();

        improved.append("=============== ATS OPTIMIZED RESUME ===============\n\n");

        // Objective
        improved.append("CAREER OBJECTIVE\n");

        improved.append(
                "Motivated software developer seeking a position where I can apply my knowledge in ");

        for (int i = 0; i < jobSkills.size(); i++) {

            improved.append(jobSkills.get(i));

            if (i != jobSkills.size() - 1) {
                improved.append(", ");
            }
        }

        improved.append(
                " while contributing to organizational success and continuously improving my technical skills.\n\n");

        // Education
        improved.append("EDUCATION\n");

        if (!section.getEducation().isBlank()) {
            improved.append(section.getEducation()).append("\n\n");
        } else {
            improved.append("Add your education details.\n\n");
        }

        // Skills
        improved.append("TECHNICAL SKILLS\n");

        if (!section.getSkills().isBlank()) {
            improved.append(section.getSkills()).append("\n");
        }

        for (String skill : jobSkills) {

            if (!section.getSkills().toLowerCase().contains(skill.toLowerCase())) {

                improved.append("• ").append(skill).append("\n");
            }
        }

        improved.append("\n");

        // Experience
        improved.append("EXPERIENCE\n");

        if (!section.getExperience().isBlank()) {

            improved.append(section.getExperience()).append("\n\n");

        } else {

            improved.append("Fresher\n\n");
        }

        // Projects
        improved.append("PROJECTS\n");

        if (!section.getProjects().isBlank()) {

            improved.append(section.getProjects()).append("\n");

        } else {

            improved.append("Add at least one relevant project.\n");
        }

        improved.append("\n");

        improved.append("Recommended Improvements\n");

        improved.append("-----------------------------------\n");

        for (String skill : jobSkills) {

            if (!resumeText.toLowerCase().contains(skill.toLowerCase())) {

                improved.append("✔ Add project using ")
                        .append(skill)
                        .append("\n");
            }
        }

        improved.append("\n");

        improved.append("This resume has been optimized according to the provided Job Description.\n");

        return improved.toString();
    }
}