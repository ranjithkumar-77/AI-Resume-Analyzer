package com.ranjith.resumeanalyzer.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ranjith.resumeanalyzer.service.CompanyATSService;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyATSService companyService;

    @GetMapping("")
    public ResponseEntity<Map<String, List<String>>> listCompanies(){
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    public static class CompanyReq {
        public String key;
        public List<String> rules;
    }

    @PostMapping("")
    public ResponseEntity<?> addOrUpdateCompany(@RequestBody CompanyReq req){
        if (req == null || req.key == null || req.key.trim().isEmpty()){
            return ResponseEntity.badRequest().body("company key required");
        }
        companyService.addOrUpdateCompany(req.key, req.rules == null ? List.of() : req.rules);
        return ResponseEntity.ok().build();
    }
}
