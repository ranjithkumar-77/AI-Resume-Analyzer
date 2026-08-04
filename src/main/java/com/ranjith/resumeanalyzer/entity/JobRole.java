package com.ranjith.resumeanalyzer.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "job_role")
public class JobRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String skills; // comma separated

    public JobRole() {}

    public JobRole(String name, String skills) {
        this.name = name;
        this.skills = skills;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }
}