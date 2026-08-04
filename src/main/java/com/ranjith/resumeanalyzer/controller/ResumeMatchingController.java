package com.ranjith.resumeanalyzer.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ranjith.resumeanalyzer.dto.MatchResult;
import com.ranjith.resumeanalyzer.entity.JobDescription;
import com.ranjith.resumeanalyzer.parser.ResumeFileExtractor;
import com.ranjith.resumeanalyzer.service.JobDescriptionService;
import com.ranjith.resumeanalyzer.service.ResumeMatchingService;

@Controller
@RequestMapping("/match")
public class ResumeMatchingController {

    @Autowired
    private ResumeMatchingService matchingService;

    @Autowired
    private JobDescriptionService jobService;

    @Autowired
    private ResumeFileExtractor resumeFileExtractor;

    @GetMapping
    public String showMatchPage(Model model) {

        model.addAttribute("jobs", jobService.getAll());

        return "resume-match";
    }

    @PostMapping
    public String matchResume(
            @RequestParam("resumeFile") MultipartFile resumeFile,
            @RequestParam("jobId") Integer jobId,
            Model model) throws IOException {

        JobDescription job = jobService.getById(jobId);

        String resumeText =
                resumeFileExtractor.extractText(resumeFile);

        MatchResult result =
                matchingService.matchResume(
                        resumeText,
                        job.getDescription());

        model.addAttribute("jobs", jobService.getAll());
        model.addAttribute("result", result);

        return "resume-match";
    }
}