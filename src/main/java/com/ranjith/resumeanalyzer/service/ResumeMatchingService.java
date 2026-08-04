package com.ranjith.resumeanalyzer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ranjith.resumeanalyzer.dto.MatchResult;
import com.ranjith.resumeanalyzer.dto.ResumeSuggestion;
import com.ranjith.resumeanalyzer.util.KeywordExtractor;
import com.ranjith.resumeanalyzer.util.ResumeHighlighter;

@Service
public class ResumeMatchingService {

    public MatchResult matchResume(String resumeText, String jobDescription) {

        if (resumeText == null) {
            resumeText = "";
        }

        if (jobDescription == null) {
            jobDescription = "";
        }

        String originalResume = resumeText;

        resumeText = resumeText.toLowerCase();
        jobDescription = jobDescription.toLowerCase();

        // Dynamic Keyword Extraction
        List<String> skills = KeywordExtractor.extractKeywords(jobDescription);

        List<String> matched = new ArrayList<>();
        List<String> missing = new ArrayList<>();

        int totalRequired = skills.size();

        for (String skill : skills) {

            if (resumeText.contains(skill.toLowerCase())) {
                matched.add(skill);
            } else {
                missing.add(skill);
            }
        }

        // Skill Score
        int skillScore = 0;

        if (totalRequired > 0) {
            skillScore = (matched.size() * 100) / totalRequired;
        }

        // Education Score
        int educationScore =
                resumeText.contains("b.e") ||
                resumeText.contains("btech") ||
                resumeText.contains("degree") ||
                resumeText.contains("bachelor") ||
                resumeText.contains("master")
                ? 90 : 50;

        // Experience Score
        int experienceScore =
                resumeText.contains("experience") ||
                resumeText.contains("internship") ||
                resumeText.contains("worked")
                ? 90 : 40;

        // Project Score
        int projectScore =
                resumeText.contains("project")
                ? 90 : 40;

        // Formatting Score
        int formattingScore =
                originalResume.length() > 300
                ? 90 : 70;

        // Overall ATS Score
        int overall =
                (skillScore
                + educationScore
                + experienceScore
                + projectScore
                + formattingScore) / 5;

        // Recommendation
        String recommendation;

        if (overall >= 80) {

            recommendation =
                    "Excellent ATS Score. Your resume is highly competitive.";

        } else if (overall >= 60) {

            recommendation =
                    "Good resume. Add the missing skills to improve ATS score.";

        } else {

            recommendation =
                    "Resume needs significant improvement.";
        }

        // Suggestions
        List<ResumeSuggestion> suggestions = new ArrayList<>();

        for (String skill : missing) {

            suggestions.add(new ResumeSuggestion(
                    "Add Skill",
                    "Include '" + skill + "' in your resume if you have experience with it."
            ));
        }

        if (!resumeText.contains("project")) {

            suggestions.add(new ResumeSuggestion(
                    "Projects",
                    "Add 2-3 real-world projects with technologies used."
            ));
        }

        if (!resumeText.contains("internship")) {

            suggestions.add(new ResumeSuggestion(
                    "Experience",
                    "Mention internship or practical training experience."
            ));
        }

        if (!resumeText.contains("github")) {

            suggestions.add(new ResumeSuggestion(
                    "GitHub",
                    "Add your GitHub profile link."
            ));
        }

        if (!resumeText.contains("linkedin")) {

            suggestions.add(new ResumeSuggestion(
                    "LinkedIn",
                    "Add your LinkedIn profile URL."
            ));
        }

        // Highlight Resume
        String highlightedResume =
                ResumeHighlighter.highlight(
                        originalResume,
                        matched,
                        missing
                );

        return new MatchResult(
                overall,
                skillScore,
                educationScore,
                experienceScore,
                projectScore,
                formattingScore,
                matched,
                missing,
                recommendation,
                suggestions,
                highlightedResume
        );
    }
}