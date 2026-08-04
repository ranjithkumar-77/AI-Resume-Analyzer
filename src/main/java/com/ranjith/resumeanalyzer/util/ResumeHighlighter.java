package com.ranjith.resumeanalyzer.util;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResumeHighlighter {

    public static String highlight(String resume,
                                   List<String> matched,
                                   List<String> missing) {

        if (resume == null || resume.isBlank()) {
            return "";
        }

        String highlighted = escapeHtml(resume);

        // Highlight longer skills first
        matched.sort(Comparator.comparingInt(String::length).reversed());

        for (String skill : matched) {

            String regex = "(?i)\\b" + Pattern.quote(skill) + "\\b";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(highlighted);

            StringBuffer sb = new StringBuffer();

            while (matcher.find()) {

                matcher.appendReplacement(
                        sb,
                        "<span style=\"background:#c8f7c5;font-weight:bold;\">"
                                + Matcher.quoteReplacement(matcher.group())
                                + "</span>"
                );
            }

            matcher.appendTail(sb);

            highlighted = sb.toString();
        }

        if (!missing.isEmpty()) {

            highlighted += "<br><br>";
            highlighted += "<b style=\"color:red;\">Missing Skills:</b><br>";

            for (String skill : missing) {
                highlighted += "&#8226; <span style=\"color:red;\">" + skill + "</span><br>";
            }
        }

        return highlighted;
    }

    private static String escapeHtml(String text) {

        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}