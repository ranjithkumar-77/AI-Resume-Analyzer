package com.ranjith.resumeanalyzer.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ranjith.resumeanalyzer.dto.JobRecommendation;

@Service
public class JobRecommendationService {

    @Autowired
    private SkillExtractorService skillExtractorService;

    @Autowired
    private JobRoleService jobRoleService;

    public JobRecommendation recommendJobs(String resumeText,
                                           String jobDescription) {

        if (resumeText == null) {
            resumeText = "";
        }

        if (jobDescription == null) {
            jobDescription = "";
        }

        List<String> resumeSkills =
                skillExtractorService.extractSkills(resumeText);

        List<String> jobSkills =
                jobRoleService.getSkills(jobDescription);

        String jd = jobDescription.toLowerCase();

        Set<String> jobs = new LinkedHashSet<>();
        Set<String> missingSkills = new LinkedHashSet<>();
        Set<String> courses = new LinkedHashSet<>();

        // =====================================================
        // JOB RECOMMENDATIONS
        // =====================================================

        if (!jd.isBlank()) {

            if (jd.contains("java full stack") || jd.contains("full stack")) {

                jobs.add("Java Full Stack Developer");
                jobs.add("Software Engineer");
                jobs.add("Backend Developer");

            } else if (jd.contains("java developer")) {

                jobs.add("Java Developer");
                jobs.add("Software Engineer");

            } else if (jd.contains("backend")) {

                jobs.add("Backend Developer");
                jobs.add("Java Backend Developer");

            } else if (jd.contains("frontend")) {

                jobs.add("Frontend Developer");
                jobs.add("UI Developer");

            } else if (jd.contains("data analyst")
                    || jd.contains("data analysis")) {

                jobs.add("Data Analyst");
                jobs.add("Business Analyst");
                jobs.add("BI Developer");
                jobs.add("SQL Developer");

            } else if (jd.contains("data scientist")) {

                jobs.add("Data Scientist");
                jobs.add("Machine Learning Engineer");
                jobs.add("AI Engineer");

            } else if (jd.contains("devops")) {

                jobs.add("DevOps Engineer");
                jobs.add("Cloud Engineer");

            } else {

                jobs.add("Software Engineer");
                jobs.add("Software Developer");
            }

        }

        // =====================================================
        // FALLBACK USING RESUME SKILLS
        // =====================================================

        if (jobs.isEmpty()) {

            if (resumeSkills.contains("java")) {

                jobs.add("Java Developer");
                jobs.add("Software Engineer");

            }

            if (resumeSkills.contains("spring boot")) {

                jobs.add("Spring Boot Developer");

            }

            if (resumeSkills.contains("react")) {

                jobs.add("Frontend Developer");

            }

            if (resumeSkills.contains("python")) {

                jobs.add("Python Developer");

            }

            if (resumeSkills.contains("power bi")) {

                jobs.add("Data Analyst");

            }

            if (resumeSkills.contains("docker")) {

                jobs.add("DevOps Engineer");

            }

            if (jobs.isEmpty()) {

                jobs.add("Software Developer");

            }
        }

        // =====================================================
        // MISSING SKILLS & COURSES
        // =====================================================

        for (String skill : jobSkills) {

            boolean found = false;

            for (String resumeSkill : resumeSkills) {

                if (resumeSkill.equalsIgnoreCase(skill)) {
                    found = true;
                    break;
                }
            }

            if (!found) {

                missingSkills.add(skill);

                switch (skill.toLowerCase()) {

                    case "java":
                        courses.add("Java Programming");
                        break;

                    case "spring":
                    case "spring boot":
                        courses.add("Spring Boot Masterclass");
                        break;

                    case "hibernate":
                        courses.add("Hibernate & JPA");
                        break;

                    case "mysql":
                    case "sql":
                        courses.add("MySQL & SQL");
                        break;

                    case "html":
                        courses.add("HTML");
                        break;

                    case "css":
                        courses.add("CSS");
                        break;

                    case "javascript":
                        courses.add("JavaScript");
                        break;

                    case "react":
                        courses.add("React JS");
                        break;

                    case "git":
                        courses.add("Git & GitHub");
                        break;

                    case "docker":
                        courses.add("Docker");
                        break;

                    case "excel":
                        courses.add("Microsoft Excel");
                        break;

                    case "power bi":
                        courses.add("Power BI");
                        break;

                    case "python":
                        courses.add("Python Programming");
                        break;

                    case "tableau":
                        courses.add("Tableau");
                        break;

                    case "statistics":
                        courses.add("Statistics for Data Analysis");
                        break;

                    case "machine learning":
                        courses.add("Machine Learning");
                        break;

                    case "deep learning":
                        courses.add("Deep Learning");
                        break;

                    default:
                        courses.add(skill + " Course");
                }
            }
        }

        return new JobRecommendation(
                new ArrayList<>(jobs),
                new ArrayList<>(missingSkills),
                new ArrayList<>(courses)
        );
    }
}