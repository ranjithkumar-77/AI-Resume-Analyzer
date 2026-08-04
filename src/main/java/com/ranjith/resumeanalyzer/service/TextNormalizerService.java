package com.ranjith.resumeanalyzer.service;

import org.springframework.stereotype.Service;

@Service
public class TextNormalizerService {

    public boolean containsSkill(String resume, String skill) {

        if (resume == null || skill == null) {
            return false;
        }

        String resumeText = normalize(resume);
        String searchSkill = normalize(skill);

        return resumeText.contains(searchSkill);
    }

    private String normalize(String text) {

        text = text.toLowerCase();

        text = text.replace("react.js", "react");
        text = text.replace("node.js", "nodejs");
        text = text.replace("springboot", "spring boot");
        text = text.replace("java script", "javascript");
        text = text.replace("my sql", "mysql");

        text = text.replaceAll("[^a-z0-9+# ]", " ");

        text = text.replaceAll("\\s+", " ").trim();

        return text;
    }
}