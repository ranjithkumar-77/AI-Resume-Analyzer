package com.ranjith.resumeanalyzer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResumeRewriteService {

    @Autowired
    private SkillExtractorService skillExtractorService;

    public String rewriteResume(String resumeText, String jobDescription) {

        List<String> skills =
                skillExtractorService.extractSkills(jobDescription);

        StringBuilder sb = new StringBuilder();

        sb.append("=============================================\n");
        sb.append("      AI OPTIMIZED RESUME\n");
        sb.append("=============================================\n\n");

        sb.append("PROFESSIONAL SUMMARY\n\n");

        sb.append("Highly motivated software engineer with strong technical knowledge and a passion for learning new technologies. Seeking an opportunity where I can contribute to organizational success while continuously improving my professional skills.\n\n");

        sb.append("RECOMMENDED TECHNICAL SKILLS\n\n");

        for(String skill : skills){

            sb.append("• ").append(skill).append("\n");

        }

        sb.append("\n");

        sb.append("PROJECT IMPROVEMENTS\n\n");

        sb.append("• Mention measurable achievements.\n");
        sb.append("• Add keywords from Job Description.\n");
        sb.append("• Mention REST APIs if applicable.\n");
        sb.append("• Mention database technologies.\n");
        sb.append("• Add GitHub repository links.\n");

        sb.append("\n");

        sb.append("CERTIFICATION SUGGESTIONS\n\n");

        if(skills.contains("AWS"))
            sb.append("• AWS Cloud Practitioner\n");

        if(skills.contains("Docker"))
            sb.append("• Docker Essentials\n");

        if(skills.contains("Python"))
            sb.append("• Python Programming\n");

        if(skills.contains("Java"))
            sb.append("• Oracle Java Certification\n");

        if(skills.contains("Power BI"))
            sb.append("• Microsoft Power BI\n");

        sb.append("\n");

        sb.append("FINAL ATS RECOMMENDATION\n\n");

        sb.append("Include all relevant skills naturally inside your experience and project descriptions. Avoid keyword stuffing. Quantify achievements wherever possible.");

        return sb.toString();

    }

}