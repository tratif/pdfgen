package com.tratif.pdfgen.document;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.tratif.pdfgen.asserts.PdfAssert.*;

public class PDFTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void savesPdfToFile() throws IOException {
        PDF pdf = Document.fromStaticHtml("<h1>First page</h1>")
                .and()
                .toPdfObject();

        File destination = folder.newFile();

        pdf.toFile(destination);

        assertThat(Files.readAllBytes(destination.toPath()))
                .isProperPdfFile()
                .contains("First")
                .contains("page");

        destination.delete();
    }

    @Test
    public void whenSaveToNotExistingPathThenRuntimeException() {
        PDF pdf = Document.fromStaticHtml("<h1>First page</h1>")
                .and()
                .toPdfObject();

        File destination = new File("dsfnsjkdf/sdfsdg/dfh/hdfh.pdf");

        exception.expect(RuntimeException.class);
        exception.expectMessage("path does not exist");

        pdf.toFile(destination);
    }
}