package com.tratif.pdfgen.document.renderers;

import com.tratif.pdfgen.document.Document;
import com.tratif.pdfgen.document.PDF;
import com.tratif.pdfgen.document.Page;
import com.tratif.pdfgen.helpers.CommandLineExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SimplePdfRenderer {

    private static final Logger log = LoggerFactory.getLogger(SimplePdfRenderer.class);

    public SimplePdfRenderer() {

    }

    public PDF render(Page page) {
        try {
            File html = File.createTempFile(Document.TEMP_FILE_PREFIX, ".html");
            File pdf = File.createTempFile(Document.TEMP_FILE_PREFIX, ".pdf");
            Files.write(html.toPath(), page.getContent().getBytes());

            CommandLineExecutor executor = new CommandLineExecutor();
            executor.command("wkhtmltopdf")
                    .withArgument("--encoding utf-8")
                    .withArguments(page.getParams())
                    .withArgument(html.toPath().toString())
                    .withArgument(pdf.toPath().toString())
                    .execute()
                    .waitFor();

            byte[] bytes = Files.readAllBytes(pdf.toPath());

            if (!html.delete())
                log.warn("{} was not deleted.", html.getPath());

            if (!pdf.delete())
                log.warn("{} was not deleted.", pdf.getPath());

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
