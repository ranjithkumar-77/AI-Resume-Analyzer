package com.ranjith.resumeanalyzer.service;

import org.springframework.stereotype.Service;

@Service
public class ATSScoreService {

    public int calculateATS(String resumeText) {

        if (resumeText == null || resumeText.isBlank()) {
            return 0;
        }

        int score = 50;

        String text = resumeText.toLowerCase();

        if (text.contains("experience")) {
            score += 10;
        }

        if (text.contains("education")) {
            score += 10;
        }

        if (text.contains("project")) {
            score += 10;
        }

        if (text.contains("skill")) {
            score += 10;
        }

        if (text.length() > 1000) {
            score += 10;
        }

        return Math.min(score, 100);
    }
}