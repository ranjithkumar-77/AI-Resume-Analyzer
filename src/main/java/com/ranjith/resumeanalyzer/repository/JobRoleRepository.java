package com.ranjith.resumeanalyzer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ranjith.resumeanalyzer.entity.JobRole;

public interface JobRoleRepository extends JpaRepository<JobRole, Long> {
    Optional<JobRole> findByNameIgnoreCase(String name);
}