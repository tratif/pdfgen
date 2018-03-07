package com.tratif.pdfgen.document;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.tratif.pdfgen.asserts.PdfAssert.*;

public class PDFTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void savingMergedPagesIntoOneFile() throws IOException {
        PDF pdf = new PDF();
        pdf.addPage(new Page("<h1>First page</h1>"));
        pdf.addPage(new Page("<h1>Second page</h1>"));

        File temp = temporaryFolder.newFile();
        pdf.save(temp);

        assertThat(Files.readAllBytes(temp.toPath()))
                .isProperPdfFile()
                .contains("First")
                .contains("page")
                .contains("Second");
    }
}