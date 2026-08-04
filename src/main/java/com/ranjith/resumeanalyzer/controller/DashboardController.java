package com.ranjith.resumeanalyzer.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ranjith.resumeanalyzer.service.ResumeService;

@RestController
@CrossOrigin("*")
public class DashboardController {

    @Autowired
    private ResumeService resumeService;

    @GetMapping("/api/dashboard")
    public Map<String, Object> getDashboard() {

        Map<String, Object> data = new HashMap<>();

        data.put("totalResumes", resumeService.getTotalResumes());

        data.put("highestATS", resumeService.getHighestATSScore());

        data.put("averageATS", resumeService.getAverageATSScore());

        return data;
    }

}