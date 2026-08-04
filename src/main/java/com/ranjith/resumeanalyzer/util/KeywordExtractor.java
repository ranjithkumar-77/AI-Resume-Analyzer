package com.ranjith.resumeanalyzer.util;

import java.util.ArrayList;
import java.util.List;

public class KeywordExtractor {

    public static List<String> extractKeywords(String jobDescription) {

        List<String> extractedSkills = new ArrayList<>();

        if (jobDescription == null || jobDescription.isBlank()) {
            return extractedSkills;
        }

        String jd = jobDescription.toLowerCase();

        for (String skill : SkillDictionary.SKILLS) {

            if (jd.contains(skill.toLowerCase())) {
                extractedSkills.add(skill);
            }

        }

        return extractedSkills;
    }
}