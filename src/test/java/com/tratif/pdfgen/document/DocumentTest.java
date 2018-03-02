package com.tratif.pdfgen.document;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

public class DocumentTest {

    @Rule
    ExpectedException exception = ExpectedException.none();

    @Test
    public void hasProperContentFromString() {
        assertThat(Document.fromStaticHtml("<h1>Hello world</h1>").getContent())
                .isEqualTo("<h1>Hello world</h1>");
    }

    @Test
    public void hasProperContentFromInputStream() {
        InputStream inputStream = new ByteArrayInputStream("<h1>Hello world</h1>".getBytes());
        assertThat(Document.fromStaticHtml(inputStream).getContent())
                .isEqualTo("<h1>Hello world</h1>");
    }

    @Test
    public void hasProperContentFromReader() {
        Reader reader = new StringReader("<h1>Hello world</h1>");
        assertThat(Document.fromStaticHtml(reader).getContent())
                .isEqualTo("<h1>Hello world</h1>");
    }
}