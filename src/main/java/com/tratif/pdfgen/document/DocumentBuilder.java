package com.tratif.pdfgen.document;

import com.tratif.pdfgen.helpers.CommandLineExecutor;

import java.io.File;
import java.nio.file.Files;

public class DocumentBuilder {

    private final String FILE_PREFIX = "pdfgen";

    private String content;

    DocumentBuilder(String content) {
        this.content = content;
    }

    public byte[] toPdf() {
        try {
            File html = File.createTempFile(FILE_PREFIX, ".html");
            File pdf = File.createTempFile(FILE_PREFIX, ".pdf");
            Files.write(html.toPath(), content.getBytes());

            CommandLineExecutor executor = new CommandLineExecutor();
            executor.command("wkhtmltopdf")
                    .withArgument("--encoding utf-8")
                    .withArgument(html.toPath().toString())
                    .withArgument(pdf.toPath().toString())
                    .execute()
                    .waitFor();

            byte[] bytes = Files.readAllBytes(pdf.toPath());

            html.delete();
            pdf.delete();

            return bytes;
        } catch(Exception e) {
            throw new RuntimeException("Something went wrong.");
        }
    }
}
