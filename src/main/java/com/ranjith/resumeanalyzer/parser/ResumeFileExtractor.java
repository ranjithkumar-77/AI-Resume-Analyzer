package com.ranjith.resumeanalyzer.parser;

import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResumeFileExtractor {

    public String extractText(MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();

        if (fileName == null) {
            return "";
        }

        fileName = fileName.toLowerCase();

        if (fileName.endsWith(".pdf")) {
            return extractPdf(file);
        }

        if (fileName.endsWith(".docx")) {
            return extractDocx(file);
        }

        if (fileName.endsWith(".doc")) {
            return extractDoc(file);
        }

        throw new IllegalArgumentException("Only PDF and DOCX files are supported.");
    }

    private String extractPdf(MultipartFile file) throws IOException {

        try (InputStream input = file.getInputStream();
             PDDocument document = PDDocument.load(input)) {

            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    private String extractDocx(MultipartFile file) throws IOException {

        try (InputStream input = file.getInputStream();
             XWPFDocument document = new XWPFDocument(input);
             XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {

            return extractor.getText();
        }
    }

    private String extractDoc(MultipartFile file) throws IOException {

        try (InputStream input = file.getInputStream();
             HWPFDocument document = new HWPFDocument(input);
             WordExtractor extractor = new WordExtractor(document)) {

            return extractor.getText();
        }
    }
}