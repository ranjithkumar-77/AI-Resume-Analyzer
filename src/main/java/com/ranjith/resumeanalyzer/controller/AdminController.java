package com.ranjith.resumeanalyzer.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ranjith.resumeanalyzer.entity.User;
import com.ranjith.resumeanalyzer.repository.JobDescriptionRepository;
import com.ranjith.resumeanalyzer.service.JobRoleService;
import com.ranjith.resumeanalyzer.service.ResumeService;
import com.ranjith.resumeanalyzer.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private JobRoleService jobRoleService;

    @Autowired
    private UserService userService;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private JobDescriptionRepository jdRepository;

    @GetMapping("/job-roles")
    public ResponseEntity<Map<String, List<String>>> getAllJobRoles() {
        return ResponseEntity.ok(jobRoleService.getAllRoles());
    }

    public static class JobRoleRequest {
        public String roleName;
        public List<String> skills;
    }

    @PostMapping("/job-roles")
    public ResponseEntity<?> addOrUpdateRole(@RequestBody JobRoleRequest req) {
        if (req == null || req.roleName == null || req.roleName.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("roleName is required");
        }
        jobRoleService.addOrUpdateRole(req.roleName, req.skills == null ? List.of() : req.skills);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<Map<String, Object>>> getAllUsers() {

        List<Map<String, Object>> users = userService.getAllUsers().stream()
                .map(u -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", u.getId());
                    map.put("name", u.getName());
                    map.put("email", u.getEmail());
                    return map;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getSystemStats() {

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userService.getTotalUsers());
        stats.put("totalResumes", resumeService.getTotalResumes());
        stats.put("totalJobDescriptions", jdRepository.count());
        stats.put("highestATS", resumeService.getHighestATSScore());
        stats.put("averageATS", Math.round(resumeService.getAverageATSScore() * 10.0) / 10.0);
        stats.put("totalJobRoles", jobRoleService.getAllRoles().size());

        return ResponseEntity.ok(stats);
    }
}
