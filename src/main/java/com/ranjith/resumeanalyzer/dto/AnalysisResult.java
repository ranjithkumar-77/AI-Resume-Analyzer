package com.ranjith.resumeanalyzer.dto;

import java.util.List;

public class AnalysisResult {

    private String atsScore;
    private String matchScore;
    private String analysis;
    private String resumeText;
    private String improvedResume;
    
    // NEW
    private String resumeStrength;

    // Existing
    private List<String> recommendedJobs;
    private List<String> missingSkills;
    private List<String> recommendedCourses;

    private Long resumeId;

    public AnalysisResult() {
    }

    public AnalysisResult(String atsScore,
                          String matchScore,
                          String analysis,
                          String resumeText,
                          String improvedResume,
                          String resumeStrength,
                          List<String> recommendedJobs,
                          List<String> missingSkills,
                          List<String> recommendedCourses) {

        this.atsScore = atsScore;
        this.matchScore = matchScore;
        this.analysis = analysis;
        this.resumeText = resumeText;
        this.improvedResume = improvedResume;
        this.resumeStrength = resumeStrength;
        this.recommendedJobs = recommendedJobs;
        this.missingSkills = missingSkills;
        this.recommendedCourses = recommendedCourses;
    }

    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    public String getAtsScore() {
        return atsScore;
    }

    public void setAtsScore(String atsScore) {
        this.atsScore = atsScore;
    }

    public String getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(String matchScore) {
        this.matchScore = matchScore;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getResumeText() {
        return resumeText;
    }

    public void setResumeText(String resumeText) {
        this.resumeText = resumeText;
    }

    public String getImprovedResume() {
        return improvedResume;
    }

    public void setImprovedResume(String improvedResume) {
        this.improvedResume = improvedResume;
    }

    public String getResumeStrength() {
        return resumeStrength;
    }

    public void setResumeStrength(String resumeStrength) {
        this.resumeStrength = resumeStrength;
    }

    public List<String> getRecommendedJobs() {
        return recommendedJobs;
    }

    public void setRecommendedJobs(List<String> recommendedJobs) {
        this.recommendedJobs = recommendedJobs;
    }

    public List<String> getMissingSkills() {
        return missingSkills;
    }

    public void setMissingSkills(List<String> missingSkills) {
        this.missingSkills = missingSkills;
    }

    public List<String> getRecommendedCourses() {
        return recommendedCourses;
    }

    public void setRecommendedCourses(List<String> recommendedCourses) {
        this.recommendedCourses = recommendedCourses;
    }

}