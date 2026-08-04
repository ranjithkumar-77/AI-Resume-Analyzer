package com.ranjith.resumeanalyzer.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobRoleSkills {

    public static final Map<String, List<String>> JOB_SKILLS = new HashMap<>();

    static {

        // Java Full Stack Developer
        JOB_SKILLS.put("Java Full Stack Developer", List.of(
                "java",
                "spring",
                "spring boot",
                "hibernate",
                "jpa",
                "jdbc",
                "rest api",
                "microservices",
                "sql",
                "mysql",
                "html",
                "css",
                "javascript",
                "react",
                "git",
                "maven"
        ));

        // Python Developer
        JOB_SKILLS.put("Python Developer", List.of(
                "python",
                "django",
                "flask",
                "fastapi",
                "postgresql",
                "mysql",
                "mongodb",
                "docker",
                "git",
                "linux",
                "aws",
                "oop",
                "data structures",
                "problem solving",
                "unit testing"
        ));

        // Frontend Developer
        JOB_SKILLS.put("Frontend Developer", List.of(
                "html",
                "css",
                "bootstrap",
                "tailwind css",
                "javascript",
                "typescript",
                "react",
                "angular",
                "vue",
                "git"
        ));

        // Backend Developer
        JOB_SKILLS.put("Backend Developer", List.of(
                "java",
                "spring boot",
                "node.js",
                "express.js",
                "sql",
                "mysql",
                "mongodb",
                "rest api",
                "docker",
                "git"
        ));

        // Data Analyst
        JOB_SKILLS.put("Data Analyst", List.of(
                "sql",
                "excel",
                "power bi",
                "tableau",
                "python",
                "pandas",
                "statistics",
                "data visualization"
        ));

        // DevOps Engineer
        JOB_SKILLS.put("DevOps Engineer", List.of(
                "linux",
                "docker",
                "kubernetes",
                "jenkins",
                "aws",
                "azure",
                "git",
                "terraform"
        ));

    }

    private JobRoleSkills() {
    }
}