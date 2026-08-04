package com.ranjith.resumeanalyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ranjith.resumeanalyzer.entity.JobDescription;
import com.ranjith.resumeanalyzer.service.JobDescriptionService;

@Controller
@RequestMapping("/jobs")
public class JobDescriptionController {

    @Autowired
    private JobDescriptionService service;

    // Show Job Description Page
    @GetMapping
    public String jobsPage(Model model) {

        model.addAttribute("job", new JobDescription());
        model.addAttribute("jobs", service.getAll());

        return "job-description";
    }

    // Save Job Description
    @PostMapping("/save")
    public String save(@ModelAttribute JobDescription jobDescription) {

        service.save(jobDescription);

        return "redirect:/jobs";
    }

    // Delete Job Description
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {

        service.delete(id);

        return "redirect:/jobs";
    }
}