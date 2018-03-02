package com.tratif.pdfgen.document;

import com.tratif.pdfgen.asserts.PdfAssert;
import com.tratif.pdfgen.asserts.helpers.SimpleParameter;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DocumentTest {

    @Test
    public void pdfHasProperContentFromString() {
        String html = "<h1>Hello world</h1>";
        PdfAssert.assertThat(Document.fromStaticHtml(html).toPdf())
                .contains("Hello")
                .contains("world");
    }

    @Test
    public void pdfHasProperContentFromInputStream() {
        InputStream inputStream = new ByteArrayInputStream("<h1>Hello world</h1>".getBytes());
        PdfAssert.assertThat(Document.fromStaticHtml(inputStream).toPdf())
                .contains("Hello")
                .contains("world");
    }

    @Test
    public void pdfHasProperContentFromReader() {
        Reader reader = new StringReader("<h1>Hello world</h1>");
        PdfAssert.assertThat(Document.fromStaticHtml(reader).toPdf())
                .contains("Hello")
                .contains("world");
    }

    @Test
    public void engineBindsParameters() {
        Map<String, Object> args = new HashMap<>();
        args.put("param", new SimpleParameter("myContent"));

        DocumentBuilder db = Document.fromHtmlTemplate("<span th:text=\"${param.content}\"></span>", args);

        assertThat(db.toHtml())
                .contains("myContent");
    }
}