package com.ranjith.resumeanalyzer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ranjith.resumeanalyzer.entity.JobDescription;
import com.ranjith.resumeanalyzer.repository.JobDescriptionRepository;

@Service
public class JobDescriptionService {

    @Autowired
    private JobDescriptionRepository repository;

    // Save Job Description
    public JobDescription save(JobDescription jobDescription) {
        return repository.save(jobDescription);
    }

    // Get All Job Descriptions
    public List<JobDescription> getAll() {
        return repository.findAll();
    }

    // Get Job Description By ID
    public JobDescription getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    // Delete Job Description
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}