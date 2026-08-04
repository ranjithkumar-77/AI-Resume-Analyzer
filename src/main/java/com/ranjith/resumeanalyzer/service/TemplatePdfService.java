package com.ranjith.resumeanalyzer.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

@Service
public class TemplatePdfService {

    public byte[] generatePdf(String templateName,
                              Map<String, String> data) throws Exception {

        // Load HTML template
        ClassPathResource resource =
                new ClassPathResource("templates/" + templateName + ".html");

       String html = new String(
        resource.getInputStream().readAllBytes(),
        StandardCharsets.UTF_8);

        // Replace placeholders
        for (Map.Entry<String, String> entry : data.entrySet()) {

            String value = entry.getValue();

            if (value == null) {
                value = "";
            }

            html = html.replace("${" + entry.getKey() + "}", value);
        }

        // Convert HTML to PDF
        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        PdfRendererBuilder builder =
                new PdfRendererBuilder();

        builder.useFastMode();

        builder.withHtmlContent(html, null);

        builder.toStream(outputStream);

        builder.run();

        return outputStream.toByteArray();
    }
}