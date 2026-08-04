package com.ranjith.resumeanalyzer.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ranjith.resumeanalyzer.entity.Resume;
import com.ranjith.resumeanalyzer.repository.ResumeRepository;

@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    // ================= SAVE =================

   // ================= SAVE =================

public Resume saveResume(Resume resume) {

    Optional<Resume> existing =
            resumeRepository.findByEmailAndResumeFileAndJobDescription(
                    resume.getEmail(),
                    resume.getResumeFile(),
                    resume.getJobDescription());

    if (existing.isPresent()) {

        Resume old = existing.get();

        old.setResumeText(resume.getResumeText());
        old.setAnalysis(resume.getAnalysis());
        old.setAtsScore(resume.getAtsScore());
        old.setMatchScore(resume.getMatchScore());
        old.setImprovedResume(resume.getImprovedResume());
        old.setTemplate(resume.getTemplate());
        old.setSkills(resume.getSkills());
        old.setSummary(resume.getSummary());
        old.setProjects(resume.getProjects());
        old.setExperience(resume.getExperience());
        old.setEducation(resume.getEducation());
        old.setCertifications(resume.getCertifications());
        old.setResumeStrength(resume.getResumeStrength());
        old.setRecommendedJobs(resume.getRecommendedJobs());
        old.setRecommendedCourses(resume.getRecommendedCourses());
        old.setFullName(resume.getFullName());
        old.setPhone(resume.getPhone());

        return resumeRepository.save(old);
    }

    return resumeRepository.save(resume);
}

    // ================= GET ALL =================

    public List<Resume> getAllResumes() {

        return resumeRepository.findAll();

    }

    // ================= GET USER RESUMES =================

    public List<Resume> getResumesByEmail(String email) {

        return resumeRepository.findByEmail(email);

    }

    // ================= GET BY ID =================

    public Resume getResumeById(Long id) {

        return resumeRepository.findById(id).orElse(null);

    }

    // ================= DELETE =================

    public void deleteResume(Long id) {

        resumeRepository.deleteById(id);

    }

    // ================= TOTAL COUNT =================

    public long getTotalResumes() {

        return resumeRepository.count();

    }

    // ================= HIGHEST ATS SCORE =================

    public int getHighestATSScore() {

        List<Resume> resumes = resumeRepository.findAll();

        int highest = 0;

        for (Resume resume : resumes) {

            if (resume.getAtsScore() == null)
                continue;

            try {

                int value = Integer.parseInt(
                        resume.getAtsScore()
                                .replace("/100", "")
                                .trim());

                if (value > highest) {

                    highest = value;

                }

            } catch (Exception e) {

                // Ignore invalid score

            }

        }

        return highest;

    }

    // ================= AVERAGE ATS SCORE =================

    public double getAverageATSScore() {

        List<Resume> resumes = resumeRepository.findAll();

        if (resumes.isEmpty()) {

            return 0;

        }

        int total = 0;

        int count = 0;

        for (Resume resume : resumes) {

            if (resume.getAtsScore() == null)
                continue;

            try {

                total += Integer.parseInt(
                        resume.getAtsScore()
                                .replace("/100", "")
                                .trim());

                count++;

            } catch (Exception e) {

                // Ignore invalid score

            }

        }

        if (count == 0) {

            return 0;

        }

        return (double) total / count;

    }

}