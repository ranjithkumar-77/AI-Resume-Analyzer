package com.ranjith.resumeanalyzer.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ranjith.resumeanalyzer.builder.ResumeBuilderService;
import com.ranjith.resumeanalyzer.dto.AnalysisResult;
import com.ranjith.resumeanalyzer.entity.Resume;
import com.ranjith.resumeanalyzer.parser.ResumeFileExtractor;
import com.ranjith.resumeanalyzer.service.AIAnalyzerService;
import com.ranjith.resumeanalyzer.service.ReportService;
import com.ranjith.resumeanalyzer.service.ResumeService;
import com.ranjith.resumeanalyzer.service.SkillExtractorService;

@RestController
@RequestMapping("/api/resumes")
@CrossOrigin(origins = "*")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private ResumeFileExtractor resumeFileExtractor;

    @Autowired
    private AIAnalyzerService aiAnalyzerService;

    @Autowired
    private ResumeBuilderService resumeBuilderService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private SkillExtractorService skillExtractorService;

    // ================= SAVE RESUME =================

    @PostMapping
    public Resume saveResume(@RequestBody Resume resume) {
        return resumeService.saveResume(resume);
    }

    // ================= GET ALL =================

    @GetMapping
    public List<Resume> getAllResumes() {
        return resumeService.getAllResumes();
    }

    // ================= GET USER RESUMES =================

    @GetMapping("/user/{email}")
    public List<Resume> getUserResumes(@PathVariable String email) {
        return resumeService.getResumesByEmail(email);
    }

    // ================= GET BY ID =================

    @GetMapping("/{id}")
    public Resume getResumeById(@PathVariable Long id) {
        return resumeService.getResumeById(id);
    }

    // ================= DELETE =================

    @DeleteMapping("/{id}")
    public String deleteResume(@PathVariable Long id) {
        resumeService.deleteResume(id);
        return "Resume Deleted Successfully";
    }

    // ================= DASHBOARD STATS =================

    @GetMapping("/stats/{email}")
    public Map<String, Object> getDashboardStats(@PathVariable String email) {

        List<Resume> resumes = resumeService.getResumesByEmail(email);

        int total = resumes.size();
        int highest = 0;
        double average = 0;

        for (Resume resume : resumes) {

            try {

                if (resume.getAtsScore() == null)
                    continue;

                int ats = Integer.parseInt(
                        resume.getAtsScore()
                                .replace("/100", "")
                                .trim());

                highest = Math.max(highest, ats);
                average += ats;

            } catch (Exception e) {
            }
        }

        if (total > 0) {
            average /= total;
        }

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalResumes", total);
        stats.put("highestATS", highest);
        stats.put("averageATS", Math.round(average * 10.0) / 10.0);

        return stats;
    }
    // ================= UPLOAD =================

@PostMapping("/upload")
public AnalysisResult uploadResume(

        @RequestParam("file") MultipartFile file,

        @RequestParam("jobDescription") String jobDescription,

        @RequestParam("template") String template,

        @RequestParam("email") String email,

        @RequestParam(value = "company", required = false) String company) {

    try {

        String uploadPath =
                System.getProperty("user.dir")
                        + File.separator
                        + "uploads";

        File folder = new File(uploadPath);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        String fileName = file.getOriginalFilename();

        if (fileName == null ||
                (!fileName.toLowerCase().endsWith(".pdf")
                        && !fileName.toLowerCase().endsWith(".docx"))) {

            throw new IllegalArgumentException(
                    "Only PDF and DOCX files are supported.");
        }

        File savedFile = new File(folder, fileName);

        String resumeText =
                resumeFileExtractor.extractText(file);

        System.out.println("========== RESUME ==========");
        System.out.println(resumeText);
        System.out.println("============================");

        file.transferTo(savedFile);

        AnalysisResult result =
                aiAnalyzerService.analyzeResume(
                        resumeText,
                        jobDescription,
                        company);

        // ===============================
        // Create Resume Entity
        // ===============================

        Resume resume = new Resume();

        resume.setEmail(email);
        resume.setResumeFile(fileName);

        resume.setResumeText(result.getResumeText());
        resume.setAnalysis(result.getAnalysis());

        resume.setAtsScore(result.getAtsScore());
        resume.setMatchScore(result.getMatchScore());

        resume.setJobDescription(jobDescription);
        resume.setTemplate(template);

        // Basic Information

        resume.setFullName(extractName(resumeText));
        resume.setPhone(extractPhone(resumeText));

        // Summary

        String summary = extractSection(
                resumeText,
                "Career Objective",
                "Education");

        if (summary.isBlank()) {

            summary =
                    "Motivated Software Developer with strong technical skills and passion for problem solving.";

        }

        resume.setSummary(summary);

        // Education

        resume.setEducation(
                extractSection(
                        resumeText,
                        "Education",
                        "Experience"));

        // Experience

        resume.setExperience(
                extractSection(
                        resumeText,
                        "Experience",
                        "Project"));
                                // Projects

        resume.setProjects(
                extractSection(
                        resumeText,
                        "Project",
                        "Technical Skills"));

        // Skills

        List<String> skills =
                skillExtractorService.extractSkills(resumeText);

        resume.setSkills(String.join(",", skills));

        // Certifications

        resume.setCertifications(
                extractSection(
                        resumeText,
                        "Certification",
                        "Declaration"));

        // Default Values

        resume.setAchievements("Not Available");
        resume.setLanguages("English");

        // ===============================
        // Generate Resume
        // ===============================

        String generatedResume =
                resumeBuilderService.generateResume(
                        resume,
                        template);

        resume.setImprovedResume(generatedResume);

        resume.setResumeStrength(result.getResumeStrength());
        resume.setRecommendedJobs(String.join("\n", result.getRecommendedJobs()));
        resume.setRecommendedCourses(String.join("\n", result.getRecommendedCourses()));

        Resume saved = resumeService.saveResume(resume);

        result.setImprovedResume(generatedResume);
        result.setResumeId(saved.getId());

        return result;

    } catch (Exception e) {

        e.printStackTrace();

        return new AnalysisResult(
                "0/100",
                "0%",
                "Error : " + e.getMessage(),
                "",
                "",
                "Error",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());
    }
}
// ================= DOWNLOAD REPORT =================

@GetMapping("/report/{id}")
public ResponseEntity<byte[]> downloadReport(@PathVariable Long id) {

    Resume resume = resumeService.getResumeById(id);

    byte[] pdf = reportService.generateReport(resume);

    return ResponseEntity.ok()
            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=AI_Resume_Report.pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdf);
}

// ================= DOWNLOAD RESUME =================

@GetMapping("/download/{id}")
public ResponseEntity<byte[]> downloadResume(@PathVariable Long id) {

    Resume resume = resumeService.getResumeById(id);

    byte[] pdf = reportService.generateResumePdf(resume);

    return ResponseEntity.ok()
            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=Resume.pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdf);
}

// ================= GENERATE RESUME =================

@PostMapping("/generate")
public String generateResume(@RequestBody Resume resume) {

    return resumeBuilderService.generateResume(resume);
}

// ================= HELPER METHODS =================

private String extractName(String text) {

    String[] lines = text.split("\\r?\\n");

    for (String line : lines) {

        line = line.trim();

        if (line.isEmpty())
            continue;

        if (line.contains("@"))
            continue;

        if (line.matches(".*\\d.*"))
            continue;

        if (line.length() < 4)
            continue;

        return line.toUpperCase();
    }

    return "UNKNOWN";
}

private String extractPhone(String text) {

    java.util.regex.Matcher matcher =
            java.util.regex.Pattern
                    .compile("\\b\\d{10}\\b")
                    .matcher(text);

    if (matcher.find()) {
        return matcher.group();
    }

    return "";
}

private String extractSection(String text,
                              String start,
                              String end) {

    String lower = text.toLowerCase();

    int s = lower.indexOf(start.toLowerCase());

    if (s == -1)
        return "";

    int e = lower.indexOf(end.toLowerCase(), s + start.length());

    if (e == -1)
        e = text.length();

    return text.substring(s, e)
            .replaceFirst("(?i)" + start, "")
            .trim();
}
}