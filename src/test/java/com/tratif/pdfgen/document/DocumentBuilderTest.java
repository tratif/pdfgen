package com.tratif.pdfgen.document;

import org.junit.Test;

import static com.tratif.pdfgen.asserts.PdfAssert.assertThat;

public class DocumentBuilderTest {

    @Test
    public void generatesProperPdfFile() {
        byte[] pdfContent = Document.fromStaticHtml("<h1>hello, world!</h1>")
                .toPdf();

        assertThat(pdfContent)
                .isProperPdfFile();
    }
}