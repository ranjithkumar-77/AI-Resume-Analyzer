package com.ranjith.resumeanalyzer.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ranjith.resumeanalyzer.dto.AnalysisResult;
import com.ranjith.resumeanalyzer.entity.JobDescription;
import com.ranjith.resumeanalyzer.entity.Resume;
import com.ranjith.resumeanalyzer.repository.JobDescriptionRepository;
import com.ranjith.resumeanalyzer.service.AIAnalyzerService;
import com.ranjith.resumeanalyzer.service.ResumeService;

@RestController
@RequestMapping("/api/recruiter")
public class RecruiterController {

    @Autowired
    private JobDescriptionRepository jdRepository;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private AIAnalyzerService aiAnalyzerService;

    // =========================
    // DTOs
    // =========================

    public static class JDCreateRequest {
        public String title;
        public String description;
        public String recruiterEmail;
    }

    public static class RankedCandidate {

        private Long resumeId;
        private String name;
        private String email;
        private String matchScore;
        private String atsScore;

        public RankedCandidate() {
        }

        public RankedCandidate(Long resumeId,
                               String name,
                               String email,
                               String matchScore,
                               String atsScore) {

            this.resumeId = resumeId;
            this.name = name;
            this.email = email;
            this.matchScore = matchScore;
            this.atsScore = atsScore;
        }

        public Long getResumeId() {
            return resumeId;
        }

        public void setResumeId(Long resumeId) {
            this.resumeId = resumeId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMatchScore() {
            return matchScore;
        }

        public void setMatchScore(String matchScore) {
            this.matchScore = matchScore;
        }

        public String getAtsScore() {
            return atsScore;
        }

        public void setAtsScore(String atsScore) {
            this.atsScore = atsScore;
        }
    }

    // ==========================================================
    // Upload Job Description & Rank Candidates
    // ==========================================================

    @PostMapping("/job-description")
    public ResponseEntity<?> createJDAndRank(@RequestBody JDCreateRequest req) {

        if (req == null || req.description == null || req.description.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Job description is required.");
        }

        JobDescription jd = new JobDescription();
        jd.setTitle(req.title);
        jd.setDescription(req.description);

        jdRepository.save(jd);

        List<RankedCandidate> rankedCandidates = rankCandidatesForJD(req.description);

        return ResponseEntity.ok(rankedCandidates);
    }

    // ==========================================================
    // Get All Job Descriptions
    // ==========================================================

    @GetMapping("/job-description")
    public ResponseEntity<List<JobDescription>> getAllJobDescriptions() {

        List<JobDescription> list = jdRepository.findAll();

        return ResponseEntity.ok(list);
    }

    // ==========================================================
    // Rank Candidates Using Existing JD
    // ==========================================================

    @GetMapping("/job-description/{id}/rank")
    public ResponseEntity<?> rankExistingJD(@PathVariable Integer id) {

        JobDescription jd = jdRepository.findById(id).orElse(null);

        if (jd == null) {
            return ResponseEntity.notFound().build();
        }

        List<RankedCandidate> rankedCandidates =
                rankCandidatesForJD(jd.getDescription());

        return ResponseEntity.ok(rankedCandidates);
    }

    // ==========================================================
    // Ranking Logic
    // ==========================================================

    private List<RankedCandidate> rankCandidatesForJD(String jdText) {

        List<Resume> resumes = resumeService.getAllResumes();

        List<RankedCandidate> rankedList = new ArrayList<>();

        for (Resume resume : resumes) {

            String resumeText = resume.getResumeText();

            AnalysisResult result =
                    aiAnalyzerService.analyzeResume(
                            resumeText == null ? "" : resumeText,
                            jdText == null ? "" : jdText,
                            null
                    );

            rankedList.add(
                    new RankedCandidate(
                            resume.getId(),
                            resume.getFullName(),
                            resume.getEmail(),
                            result.getMatchScore(),
                            result.getAtsScore()
                    )
            );
        }

        return rankedList.stream()
                .sorted(
                        Comparator.comparingInt(
                                (RankedCandidate c) ->
                                        parsePercent(c.getMatchScore())
                        ).reversed()
                )
                .collect(Collectors.toList());
    }

    // ==========================================================
    // Convert "92%" -> 92
    // ==========================================================

    private int parsePercent(String score) {

        if (score == null)
            return 0;

        try {

            return Integer.parseInt(
                    score.replace("%", "").trim());

        } catch (Exception e) {

            return 0;
        }
    }
}