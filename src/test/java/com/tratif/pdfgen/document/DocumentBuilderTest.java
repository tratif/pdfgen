package com.tratif.pdfgen.document;

import com.tratif.pdfgen.asserts.PdfAssert;
import com.tratif.pdfgen.asserts.helpers.SimpleParameter;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DocumentBuilderTest {

    @Test
    public void generatesProperPdfFile() {
        byte[] pdfContent = Document.fromStaticHtml("<h1>hello, world!</h1>")
                .toPdf();

        PdfAssert.assertThat(pdfContent)
                .isProperPdfFile();
    }

    @Test
    public void engineBindsParameters() {
        Map<String, Object> args = new HashMap<>();
        args.put("param", new SimpleParameter("myContent"));

        DocumentBuilder db = Document.fromHtmlTemplate("<span th:text=\"${param.content}\"></span>", args);

        assertThat(db.toHtml())
                .contains("myContent");
    }

    @Test
    public void parametricTextAppearsInPdf() {
        Map<String, Object> args = new HashMap<>();
        args.put("text", new SimpleParameter("World"));
        String html = "<h1>Hello <span th:text=\"${text.content}\"></span></h1>";
        DocumentBuilder db = Document.fromHtmlTemplate(html, args);

        assertThat(db.toHtml())
                .isEqualTo("<h1>Hello <span>World</span></h1>");

        PdfAssert.assertThat(db.toPdf())
                .contains("Hello")
                .contains("World");
    }
}