package com.ranjith.resumeanalyzer.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import com.ranjith.resumeanalyzer.entity.Company;
import com.ranjith.resumeanalyzer.repository.CompanyRepository;

@Service
public class CompanyATSService {

    @Autowired
    private CompanyRepository companyRepository;

    // Seed defaults on startup
    @PostConstruct
    public void seedDefaults() {
        if (companyRepository.count() > 0) return;
        addOrUpdateCompany("tcs", List.of("core java","spring","hibernate"));
        addOrUpdateCompany("infosys", List.of("java","spring boot","sql"));
        addOrUpdateCompany("accenture", List.of("java","microservices","aws"));
        addOrUpdateCompany("wipro", List.of("java","spring","rest api"));
        addOrUpdateCompany("amazon", List.of("aws","distributed systems","scalability"));
        addOrUpdateCompany("google", List.of("distributed systems","cloud","kubernetes"));
        addOrUpdateCompany("ibm", List.of("java","cloud","mainframe"));
    }

    public Map<String, List<String>> getAllCompanies() {
        return companyRepository.findAll().stream().collect(Collectors.toMap(
                Company::getKeyName,
                c -> Arrays.stream(c.getRules() == null ? new String[0] : c.getRules().split(",")).map(String::trim).filter(s->!s.isEmpty()).collect(Collectors.toList())
        ));
    }

    public List<String> getCompanyRules(String companyKey) {
        if (companyKey == null) return new ArrayList<>();
        Company c = companyRepository.findByKeyNameIgnoreCase(companyKey).orElse(null);
        if (c == null) return new ArrayList<>();
        return Arrays.stream(c.getRules() == null ? new String[0] : c.getRules().split(",")).map(String::trim).filter(s->!s.isEmpty()).collect(Collectors.toList());
    }

    public void addOrUpdateCompany(String key, List<String> rules) {
        if (key == null || key.trim().isEmpty()) return;
        String normalized = key.trim();
        Company existing = companyRepository.findByKeyNameIgnoreCase(normalized).orElse(null);
        String rulesStr = String.join(",", rules == null ? List.of() : rules);
        if (existing == null) {
            Company c = new Company(normalized, rulesStr);
            companyRepository.save(c);
        } else {
            existing.setRules(rulesStr);
            companyRepository.save(existing);
        }
    }
}
