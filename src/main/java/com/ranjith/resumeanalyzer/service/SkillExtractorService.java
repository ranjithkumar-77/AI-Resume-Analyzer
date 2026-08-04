package com.ranjith.resumeanalyzer.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class SkillExtractorService {

    private static final String[] SKILLS = {

            // Programming Languages
            "java",
            "python",
            "c",
            "c++",
            "c#",

            // Frontend
            "html",
            "css",
            "javascript",
            "typescript",
            "bootstrap",
            "tailwind css",
            "react",
            "react js",
            "angular",
            "vue",

            // Backend
            "spring",
            "spring boot",
            "hibernate",
            "servlet",
            "jsp",
            "rest api",
            "microservices",
            "node.js",
            "express",

            // Database
            "mysql",
            "sql",
            "postgresql",
            "oracle",
            "mongodb",

            // DevOps
            "git",
            "github",
            "docker",
            "kubernetes",
            "jenkins",

            // Cloud
            "aws",
            "azure",
            "google cloud",

            // Testing
            "junit",
            "selenium",

            // Build Tools
            "maven",
            "gradle",

            // IDEs
            "eclipse",
            "intellij",
            "vs code",

            // Data / BI
            "excel",
            "power bi",

            // Automation
            "uipath",

            // AI
            "machine learning",
            "deep learning",
            "artificial intelligence",

            // Others
            "linux"
    };

    public List<String> extractSkills(String text) {

        Set<String> skills = new LinkedHashSet<>();

        if (text == null || text.isBlank()) {
            return new ArrayList<>(skills);
        }

        String lower = text.toLowerCase();

        // Exact Matching
        for (String skill : SKILLS) {

            if (lower.contains(skill.toLowerCase())) {
                skills.add(skill);
            }
        }

        // -----------------------------
        // Synonyms
        // -----------------------------

        if (lower.contains("full stack")
                || lower.contains("fullstack")
                || lower.contains("full-stack")) {

            skills.add("java");
            skills.add("spring boot");
            skills.add("mysql");
            skills.add("html");
            skills.add("css");
            skills.add("javascript");
            skills.add("react");
        }

        if (lower.contains("frontend")) {

            skills.add("html");
            skills.add("css");
            skills.add("javascript");
            skills.add("react");
        }

        if (lower.contains("backend")) {

            skills.add("java");
            skills.add("spring boot");
            skills.add("mysql");
            skills.add("rest api");
        }

        if (lower.contains("java developer")) {

            skills.add("java");
            skills.add("spring boot");
        }

        if (lower.contains("springboot")) {
            skills.add("spring boot");
        }

        if (lower.contains("reactjs")) {
            skills.add("react");
        }

        if (lower.contains("nodejs")) {
            skills.add("node.js");
        }

        if (lower.contains("expressjs")) {
            skills.add("express");
        }

        if (lower.contains("restful api")
                || lower.contains("restful")) {
            skills.add("rest api");
        }

        if (lower.contains("git hub")) {
            skills.add("github");
        }

        if (lower.contains("amazon web services")) {
            skills.add("aws");
        }

        if (lower.contains("gcp")) {
            skills.add("google cloud");
        }

        return new ArrayList<>(skills);
    }
}
