package com.ranjith.resumeanalyzer.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ranjith.resumeanalyzer.entity.JobRole;
import com.ranjith.resumeanalyzer.repository.JobRoleRepository;
import jakarta.annotation.PostConstruct;
@Service
public class JobRoleService {
  @Autowired
    private JobRoleRepository jobRoleRepository;

    // On startup ensure some common roles exist (seed if empty)
@PostConstruct
public void seedDefaults() {
        if (jobRoleRepository.count() > 0) return;

        addOrUpdateRole("Java Full Stack Developer", Arrays.asList(
                "java","spring","spring boot","hibernate","mysql","sql","git","github","rest api","html","css","javascript"));

        addOrUpdateRole("Java Developer", Arrays.asList(
                "java","spring","spring boot","hibernate","mysql","sql","git","rest api"));

        addOrUpdateRole("Frontend Developer", Arrays.asList(
                "html","css","javascript","react","bootstrap"));

        addOrUpdateRole("Backend Developer", Arrays.asList(
                "java","spring boot","mysql","hibernate","rest api"));

        addOrUpdateRole("Data Scientist", Arrays.asList(
                "python","machine learning","deep learning","pandas","numpy","tensorflow","sql","power bi"));

        addOrUpdateRole("DevOps Engineer", Arrays.asList(
                "docker","kubernetes","jenkins","aws","terraform","linux","git"));

        addOrUpdateRole("AI Engineer", Arrays.asList(
                "python","machine learning","deep learning","tensorflow","pytorch","llm","nlp"));

        addOrUpdateRole("Software Engineer", Arrays.asList(
                "java","oops","sql","git","problem solving"));
    }

    // Return skills for a job description by matching known role names contained in JD
    public List<String> getSkills(String jobDescription) {

    if (jobDescription == null || jobDescription.isBlank()) {
        return new ArrayList<>();
    }

    String jd = jobDescription.toLowerCase();
    System.out.println("Job Description = " + jd);

    // -----------------------------
    // Common Job Title Aliases
    // -----------------------------

  if (jd.contains("full stack")) {

    System.out.println("Matched Full Stack");

    return Arrays.asList(
                "java",
                "spring boot",
                "mysql",
                "html",
                "css",
                "javascript",
                "react",
                "git");
    }
    if (jd.contains("data analyst")
        || jd.contains("data analysis")) {

    System.out.println("Matched Data Analyst");

    return Arrays.asList(
            "python",
            "sql",
            "excel",
            "power bi",
            "tableau",
            "statistics");
}

    if (jd.contains("java developer")) {
        return Arrays.asList(
                "java",
                "spring boot",
                "hibernate",
                "mysql",
                "sql",
                "rest api",
                "git");
    }

    if (jd.contains("backend")) {
        return Arrays.asList(
                "java",
                "spring boot",
                "mysql",
                "hibernate",
                "rest api");
    }

    if (jd.contains("frontend")) {
        return Arrays.asList(
                "html",
                "css",
                "javascript",
                "react",
                "bootstrap");
    }

    if (jd.contains("data scientist")) {

        return Arrays.asList(
                "python",
                "machine learning",
                "deep learning",
                "sql",
                "power bi");
    }

    if (jd.contains("devops")) {

        return Arrays.asList(
                "docker",
                "kubernetes",
                "jenkins",
                "aws",
                "linux",
                "git");
    }

    // -----------------------------
    // Database Roles
    // -----------------------------

    List<JobRole> roles = jobRoleRepository.findAll();

    for (JobRole role : roles) {

        if (jd.contains(role.getName().toLowerCase())) {

            String skills = role.getSkills();

            if (skills == null || skills.isBlank()) {
                return new ArrayList<>();
            }

            return Arrays.stream(skills.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
        }
    }

    return new ArrayList<>();
}
    // Return all roles and their skill lists
    public Map<String, List<String>> getAllRoles() {
        List<JobRole> roles = jobRoleRepository.findAll();
        return roles.stream().collect(Collectors.toMap(
                r -> r.getName().toLowerCase(),
                r -> Arrays.stream(r.getSkills() == null ? new String[0] : r.getSkills().split(",")).map(String::trim).filter(s->!s.isEmpty()).collect(Collectors.toList())
        ));
    }

    // Add or update a role mapping at runtime
    public synchronized void addOrUpdateRole(String roleName, List<String> skills) {
        if (roleName == null || roleName.trim().isEmpty()) {
            return;
        }
        String normalized = roleName.trim();
        JobRole existing = jobRoleRepository.findByNameIgnoreCase(normalized).orElse(null);
        String skillsStr = String.join(",", skills == null ? List.of() : skills);
        if (existing == null) {
            JobRole r = new JobRole(normalized, skillsStr);
            jobRoleRepository.save(r);
        } else {
            existing.setSkills(skillsStr);
            jobRoleRepository.save(existing);
        }
    }
}
