package com.tratif.pdfgen.document.renderers;

import com.tratif.pdfgen.document.Document;
import com.tratif.pdfgen.document.PDF;
import com.tratif.pdfgen.document.builders.PageBuilder;
import com.tratif.pdfgen.helpers.CommandLineExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SimplePdfRenderer {

    private static final Logger log = LoggerFactory.getLogger(SimplePdfRenderer.class);

    public List<PDF> render(List<PageBuilder> pages) {
        return pages.stream()
                .map(this::render)
                .collect(toList());
    }

    public PDF render(PageBuilder page) {
        try {
            File html = File.createTempFile(Document.TEMP_FILE_PREFIX, ".html");
            File pdf = File.createTempFile(Document.TEMP_FILE_PREFIX, ".pdf");
            Files.write(html.toPath(), page.build().getBytes());

            CommandLineExecutor executor = new CommandLineExecutor();
            int exitCode = executor.command("wkhtmltopdf")
                    .withArgument("--encoding utf-8")
                    .withArguments(page.getParams().build())
                    .withArgument(html.toPath().toString())
                    .withArgument(pdf.toPath().toString())
                    .execute()
                    .waitFor();

            if (exitCode != 0) {
                throw new RuntimeException("wkhtmltopdf was terminated with exit code " + exitCode);
            }

            byte[] bytes = Files.readAllBytes(pdf.toPath());

            if (!html.delete()) {
                log.debug("{} was not deleted.", html.getPath());
            }

            if (!pdf.delete()) {
                log.debug("{} was not deleted.", pdf.getPath());
            }

            return new PDF(bytes);

        } catch (InterruptedException e) {
            log.error("There was problem executing command.");
            throw new RuntimeException("There was problem executing command.", e);
        } catch (IOException e) {
            log.error("There was a problem with the file.");
            throw new RuntimeException("There was a problem with the file.", e);
        }
    }
}
