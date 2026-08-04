package com.ranjith.resumeanalyzer.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.ranjith.resumeanalyzer.dto.ResumeSection;

@Service
public class ResumeParserService {

    public ResumeSection parseResume(String resumeText) {

        ResumeSection section = new ResumeSection();

        // Personal Details
        section.setFullName(extractName(resumeText));
        section.setEmail(extractEmail(resumeText));
        section.setPhone(extractPhone(resumeText));
        section.setLinkedin(extractLinkedin(resumeText));
        section.setGithub(extractGithub(resumeText));

        // Professional Summary
        String objective = getSection(
                resumeText,
                "Career Objective",
                "Professional Summary",
                "Education");

        if (objective.isBlank()) {
            objective = "Motivated Java Full Stack Developer with knowledge of Java, Spring Boot, MySQL, HTML, CSS, JavaScript and SQL. Passionate about building scalable web applications and continuously improving technical skills.";
        }

        section.setObjective(objective);

        // Education
        section.setEducation(getSection(
                resumeText,
                "Education",
                "Experience",
                "Internship",
                "Project"));

        // Experience
        section.setExperience(getSection(
                resumeText,
                "Experience",
                "Professional Experience",
                "Project",
                "Projects"));

        // Projects
        section.setProjects(getSection(
                resumeText,
                "Project",
                "Projects",
                "Technical Skills",
                "Skills"));

        // Skills
        section.setSkills(getSection(
                resumeText,
                "Technical Skills",
                "Skills",
                "Soft Skills",
                "Education"));

        // Certifications
        section.setCertifications(getSection(
                resumeText,
                "Certifications",
                "Certificates",
                "Declaration",
                "Achievements"));

        return section;
    }

    private String getSection(String text,
                              String start,
                              String... nextHeaders) {

        text = text.replace("\r", "");

        int startIndex = text.toLowerCase().indexOf(start.toLowerCase());

        if (startIndex == -1) {
            return "";
        }

        int endIndex = text.length();

        for (String header : nextHeaders) {

            int index = text.toLowerCase().indexOf(
                    header.toLowerCase(),
                    startIndex + start.length());

            if (index != -1 && index < endIndex) {
                endIndex = index;
            }
        }

        String result = text.substring(startIndex, endIndex);

        result = result.replaceFirst("(?i)" + start, "").trim();

        return result;
    }

    private String extractEmail(String text) {

        Matcher matcher = Pattern.compile(
                "[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+")
                .matcher(text);

        return matcher.find() ? matcher.group() : "";
    }

    private String extractPhone(String text) {

        Matcher matcher = Pattern.compile(
                "(\\+91[- ]?)?[6-9]\\d{9}")
                .matcher(text);

        return matcher.find() ? matcher.group() : "";
    }

    private String extractLinkedin(String text) {

        Matcher matcher = Pattern.compile(
                "https?://(www\\.)?linkedin\\.com/\\S+")
                .matcher(text);

        return matcher.find() ? matcher.group() : "";
    }

    private String extractGithub(String text) {

        Matcher matcher = Pattern.compile(
                "https?://(www\\.)?github\\.com/\\S+")
                .matcher(text);

        return matcher.find() ? matcher.group() : "";
    }

    private String extractName(String text) {

        String[] lines = text.split("\\n");

        for (String line : lines) {

            line = line.trim();

            if (line.length() > 3
                    && line.equals(line.toUpperCase())
                    && !line.contains("@")
                    && !line.matches(".*\\d.*")) {

                return line;
            }
        }

        return "";
    }
}