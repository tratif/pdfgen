package com.tratif.pdfgen.document;

import com.tratif.pdfgen.helpers.CommandLineExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DocumentBuilder {

    private final static Logger LOG = LoggerFactory.getLogger(DocumentBuilder.class);
    private final String TEMP_FILE_PREFIX = "pdfgen";

    private String content;

    DocumentBuilder(String content) {
        this.content = content;
    }

    public byte[] toPdf() {
        try {
            File html = File.createTempFile(TEMP_FILE_PREFIX, ".html");
            File pdf = File.createTempFile(TEMP_FILE_PREFIX, ".pdf");
            Files.write(html.toPath(), content.getBytes());

            CommandLineExecutor executor = new CommandLineExecutor();
            executor.command("wkhtmltopdf")
                    .withArgument("--encoding utf-8")
                    .withArgument(html.toPath().toString())
                    .withArgument(pdf.toPath().toString())
                    .execute()
                    .waitFor();

            byte[] bytes = Files.readAllBytes(pdf.toPath());

            if(!html.delete())
                LOG.warn(html.getPath() + " was not deleted.");

            if(!pdf.delete())
                LOG.warn(pdf.getPath() + " was not deleted.");

            return bytes;

        } catch (InterruptedException e) {
            LOG.error("There was problem executing command.");
            throw new RuntimeException("There was problem executing command.", e);
        } catch (IOException e) {
            LOG.error("There was a problem with the file.");
            throw new RuntimeException("There was a problem with the file.", e);
        }
    }
}
