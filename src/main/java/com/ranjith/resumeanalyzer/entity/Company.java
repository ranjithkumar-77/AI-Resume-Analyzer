package com.ranjith.resumeanalyzer.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "company_ats")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String keyName; // e.g., tcs, infosys

    @Column(columnDefinition = "TEXT")
    private String rules; // comma separated preferred terms

    public Company() {}

    public Company(String keyName, String rules) {
        this.keyName = keyName;
        this.rules = rules;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getKeyName() { return keyName; }
    public void setKeyName(String keyName) { this.keyName = keyName; }
    public String getRules() { return rules; }
    public void setRules(String rules) { this.rules = rules; }
}