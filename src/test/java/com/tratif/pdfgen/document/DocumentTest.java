package com.tratif.pdfgen.document;

import com.tratif.pdfgen.asserts.PdfAssert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

public class DocumentTest {

    @Test
    public void hasProperContentFromString() {
        String html = "<h1>Hello world</h1>";
        PdfAssert.assertThat(Document.fromStaticHtml(html).toPdf())
                .contains("Hello")
                .contains("world");
    }

    @Test
    public void hasProperContentFromInputStream() {
        InputStream inputStream = new ByteArrayInputStream("<h1>Hello world</h1>".getBytes());
        PdfAssert.assertThat(Document.fromStaticHtml(inputStream).toPdf())
                .contains("Hello")
                .contains("world");
    }

    @Test
    public void hasProperContentFromReader() {
        Reader reader = new StringReader("<h1>Hello world</h1>");
        PdfAssert.assertThat(Document.fromStaticHtml(reader).toPdf())
                .contains("Hello")
                .contains("world");
    }
}