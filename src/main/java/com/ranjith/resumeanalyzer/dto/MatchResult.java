package com.ranjith.resumeanalyzer.dto;

import java.util.List;

public class MatchResult {

    private int matchPercentage;

    private int skillScore;
    private int educationScore;
    private int experienceScore;
    private int projectScore;
    private int formattingScore;

    private List<String> matchedSkills;
    private List<String> missingSkills;

    private String recommendation;

    private List<ResumeSuggestion> suggestions;

    // NEW
    private String highlightedResume;

    public MatchResult() {
    }

    public MatchResult(int matchPercentage,
                       int skillScore,
                       int educationScore,
                       int experienceScore,
                       int projectScore,
                       int formattingScore,
                       List<String> matchedSkills,
                       List<String> missingSkills,
                       String recommendation,
                       List<ResumeSuggestion> suggestions,
                       String highlightedResume) {

        this.matchPercentage = matchPercentage;
        this.skillScore = skillScore;
        this.educationScore = educationScore;
        this.experienceScore = experienceScore;
        this.projectScore = projectScore;
        this.formattingScore = formattingScore;
        this.matchedSkills = matchedSkills;
        this.missingSkills = missingSkills;
        this.recommendation = recommendation;
        this.suggestions = suggestions;
        this.highlightedResume = highlightedResume;
    }

    public int getMatchPercentage() {
        return matchPercentage;
    }

    public void setMatchPercentage(int matchPercentage) {
        this.matchPercentage = matchPercentage;
    }

    public int getSkillScore() {
        return skillScore;
    }

    public void setSkillScore(int skillScore) {
        this.skillScore = skillScore;
    }

    public int getEducationScore() {
        return educationScore;
    }

    public void setEducationScore(int educationScore) {
        this.educationScore = educationScore;
    }

    public int getExperienceScore() {
        return experienceScore;
    }

    public void setExperienceScore(int experienceScore) {
        this.experienceScore = experienceScore;
    }

    public int getProjectScore() {
        return projectScore;
    }

    public void setProjectScore(int projectScore) {
        this.projectScore = projectScore;
    }

    public int getFormattingScore() {
        return formattingScore;
    }

    public void setFormattingScore(int formattingScore) {
        this.formattingScore = formattingScore;
    }

    public List<String> getMatchedSkills() {
        return matchedSkills;
    }

    public void setMatchedSkills(List<String> matchedSkills) {
        this.matchedSkills = matchedSkills;
    }

    public List<String> getMissingSkills() {
        return missingSkills;
    }

    public void setMissingSkills(List<String> missingSkills) {
        this.missingSkills = missingSkills;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public List<ResumeSuggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<ResumeSuggestion> suggestions) {
        this.suggestions = suggestions;
    }

    public String getHighlightedResume() {
        return highlightedResume;
    }

    public void setHighlightedResume(String highlightedResume) {
        this.highlightedResume = highlightedResume;
    }
}