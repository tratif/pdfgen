package com.tratif.pdfgen.document;

import java.io.File;
import java.nio.file.Files;

public class DocumentDetails {

    private String content;

    DocumentDetails(String content) {
        this.content = content;
    }

    public byte[] toPdf() {
        Runtime runtime = Runtime.getRuntime();
        try {
            String cmdTemplate = "wkhtmltopdf %s %s";

            File html = File.createTempFile("123", ".html");
            File pdf = File.createTempFile("123", ".pdf");
            Files.write(html.toPath(), content.getBytes());

            Process process = runtime.exec(String.format(cmdTemplate, html.getPath(), pdf.getPath()));
            process.waitFor();
            byte[] bytes = Files.readAllBytes(pdf.toPath());

            html.delete();
            pdf.delete();

            return bytes;
        } catch(Exception e) {
            throw new RuntimeException("Something went wrong.");
        }
    }
}
