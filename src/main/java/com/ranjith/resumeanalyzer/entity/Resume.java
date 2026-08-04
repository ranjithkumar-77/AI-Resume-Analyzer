package com.ranjith.resumeanalyzer.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "resume")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

private String fullName;

private String email;

private String phone;

@Lob
private String summary;

@Lob
private String skills;

@Lob
private String education;

@Lob
private String experience;

@Lob
private String projects;

@Lob
private String certifications;

@Lob
private String achievements;

@Lob
private String languages;
private String template;

private String resumeFile;

private String atsScore;

private String matchScore;

@Lob
private String analysis;

@Lob
private String resumeText;

@Lob
private String jobDescription;

@Lob
private String improvedResume;

@Lob
private String recommendedJobs;

@Lob
private String recommendedCourses;

@Lob
private String resumeStrength;

@CreationTimestamp
@Column(name = "created_at", updatable = false)
private LocalDateTime createdAt;

public Resume() {
}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getProjects() {
        return projects;
    }

    public void setProjects(String projects) {
        this.projects = projects;
    }

    public String getCertifications() {
        return certifications;
    }

    public void setCertifications(String certifications) {
        this.certifications = certifications;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getResumeFile() {
        return resumeFile;
    }

    public void setResumeFile(String resumeFile) {
        this.resumeFile = resumeFile;
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

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getImprovedResume() {
        return improvedResume;
    }

    public void setImprovedResume(String improvedResume) {
        this.improvedResume = improvedResume;
    }

    public String getRecommendedJobs() {
        return recommendedJobs;
    }

    public void setRecommendedJobs(String recommendedJobs) {
        this.recommendedJobs = recommendedJobs;
    }

    public String getRecommendedCourses() {
        return recommendedCourses;
    }

    public void setRecommendedCourses(String recommendedCourses) {
        this.recommendedCourses = recommendedCourses;
    }

    public String getResumeStrength() {
        return resumeStrength;
    }

    public void setResumeStrength(String resumeStrength) {
        this.resumeStrength = resumeStrength;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}