package com.ranjith.resumeanalyzer.dto;

import java.util.List;

public class JobRecommendation {

    private List<String> recommendedJobs;
    private List<String> missingSkills;
    private List<String> recommendedCourses;

    public JobRecommendation() {
    }

    public JobRecommendation(List<String> recommendedJobs,
                             List<String> missingSkills,
                             List<String> recommendedCourses) {
        this.recommendedJobs = recommendedJobs;
        this.missingSkills = missingSkills;
        this.recommendedCourses = recommendedCourses;
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