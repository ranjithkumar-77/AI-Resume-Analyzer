package com.ranjith.resumeanalyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ranjith.resumeanalyzer.entity.JobDescription;

public interface JobDescriptionRepository extends JpaRepository<JobDescription, Integer> {

}