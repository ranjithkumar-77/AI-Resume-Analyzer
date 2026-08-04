package com.ranjith.resumeanalyzer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ranjith.resumeanalyzer.entity.Resume;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    List<Resume> findByEmail(String email);

    Optional<Resume> findByEmailAndResumeFileAndJobDescription(
            String email,
            String resumeFile,
            String jobDescription
    );

}