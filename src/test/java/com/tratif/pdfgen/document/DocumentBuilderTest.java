package com.tratif.pdfgen.document;

import com.tratif.pdfgen.asserts.PdfAssert;
import org.junit.Test;

public class DocumentBuilderTest {

    @Test
    public void isProperPdfFile() {
        byte[] bytes = Document.fromStaticHtml("<h1>hello, world!</h1>")
                .toPdf();

        PdfAssert.assertThat(bytes)
                .isProperPdfFile();
    }

    @Test
    public void containsString() {
        byte[] bytes = Document.fromStaticHtml("<h1>hello, world!</h1>")
                .toPdf();

        PdfAssert.assertThat(bytes)
                .contains("hello")
                .contains("world");
    }
}