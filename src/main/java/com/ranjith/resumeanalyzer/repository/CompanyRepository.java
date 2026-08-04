package com.ranjith.resumeanalyzer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ranjith.resumeanalyzer.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByKeyNameIgnoreCase(String keyName);
}