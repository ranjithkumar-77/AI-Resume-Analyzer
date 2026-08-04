package com.ranjith.resumeanalyzer.service;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ranjith.resumeanalyzer.entity.Resume;

@Service
public class ReportService {

    @Autowired
    private TemplatePdfService templatePdfService;

    public byte[] generateReport(Resume resume) {
        return buildAtsReport(resume);
    }

    public byte[] generateResumePdf(Resume resume) {
        return buildResumePdf(resume);
    }

    private byte[] buildAtsReport(Resume resume) {

        try {

            Map<String, String> data = new HashMap<>();

            data.put("name", value(resume.getFullName()));
            data.put("email", value(resume.getEmail()));
            data.put("atsScore", value(resume.getAtsScore()));
            data.put("matchScore", value(resume.getMatchScore()));
            data.put("resumeStrength", value(resume.getResumeStrength()));
            data.put("analysis", formatPlainText(resume.getAnalysis()));
            data.put("jobDescription", formatPlainText(resume.getJobDescription()));
            data.put("recommendedJobs", formatBulletList(resume.getRecommendedJobs()));
            data.put("recommendedCourses", formatBulletList(resume.getRecommendedCourses()));

            String date = resume.getCreatedAt() != null
                    ? resume.getCreatedAt().format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
                    : "N/A";
            data.put("date", date);

            return templatePdfService.generatePdf("ats-report", data);

        } catch (Exception e) {
            throw new RuntimeException("Unable to generate ATS report", e);
        }
    }

    private byte[] buildResumePdf(Resume resume) {

        try {

            Map<String, String> data = new HashMap<>();

            data.put("name", value(resume.getFullName()));
            data.put("email", value(resume.getEmail()));
            data.put("phone", value(resume.getPhone()));

            data.put("summary", formatParagraph(resume.getSummary()));
            data.put("skills", formatList(resume.getSkills()));
            data.put("experience", formatSection(resume.getExperience()));
            data.put("projects", formatSection(resume.getProjects()));
            data.put("education", formatSection(resume.getEducation()));
            data.put("certifications", formatList(resume.getCertifications()));
            data.put("achievements", formatList(resume.getAchievements()));
            data.put("languages", formatList(resume.getLanguages()));

            String template = resume.getTemplate();

            if (template == null || template.isBlank()) {
                template = "modern";
            }

            return templatePdfService.generatePdf(template, data);

        } catch (Exception e) {
            throw new RuntimeException("Unable to generate resume PDF", e);
        }
    }

    private String escapeXml(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&apos;");
    }

    private String value(String text) {

        if (text == null || text.isBlank()) {
            return "Not Available";
        }

        return escapeXml(text.trim());
    }

    private String formatPlainText(String text) {

        if (text == null || text.isBlank()) {
            return "Not Available";
        }

        return escapeXml(text);
    }

    private String formatBulletList(String text) {

        if (text == null || text.isBlank()) {
            return "None";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<ul>");

        for (String item : text.split("\n")) {

            item = item.trim();

            if (!item.isEmpty()) {
                sb.append("<li>").append(escapeXml(item)).append("</li>");
            }
        }

        sb.append("</ul>");
        return sb.toString();
    }

    private String formatParagraph(String text) {

        if (text == null || text.isBlank()) {
            return "Not Available";
        }

        return escapeXml(text).replace("\n", "<br/>");
    }

    private String formatList(String text) {

        if (text == null || text.isBlank()) {
            return "<ul><li>Not Available</li></ul>";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<ul>");

        String[] items = text.split(",");

        for (String item : items) {

            item = item.trim();

            if (!item.isEmpty()) {
                sb.append("<li>")
                  .append(escapeXml(item))
                  .append("</li>");
            }
        }

        sb.append("</ul>");
        return sb.toString();
    }

    private String formatSection(String text) {

        if (text == null || text.isBlank()) {
            return "Not Available";
        }

        StringBuilder sb = new StringBuilder();
        String[] lines = text.split("\\r?\\n");

        for (String line : lines) {

            line = line.trim();

            if (line.isEmpty()) {
                sb.append("<br/>");
            } else {
                sb.append(escapeXml(line)).append("<br/>");
            }
        }

        return sb.toString();
    }

}
