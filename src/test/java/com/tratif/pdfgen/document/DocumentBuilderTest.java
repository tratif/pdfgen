package com.tratif.pdfgen.document;

import com.google.common.collect.ImmutableMap;
import com.tratif.pdfgen.asserts.helpers.SimpleParameter;
import org.assertj.core.api.AbstractCharSequenceAssert;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.tratif.pdfgen.asserts.PdfAssert.assertThat;

public class DocumentBuilderTest {

    @Test
    public void generatesProperPdfFile() {
        byte[] pdfContent = Document.fromStaticHtml("<h1>hello, world!</h1>")
                .toPdf();

        assertThat(pdfContent)
                .isProperPdfFile();
    }

    @Test
    public void engineBindsParameters() {
        Map<String, Object> args = new HashMap<>();
        args.put("param", new SimpleParameter("myContent"));

        DocumentBuilder db = Document.fromHtmlTemplate("<span th:text=\"${param.content}\"></span>", args);

        assertThatHtml(db.toHtml())
                .contains("myContent");
    }

    @Test
    public void rendersTextProvidedAsTemplateParameter() {
        Map<String, Object> args = ImmutableMap.of("text", new SimpleParameter("World"));
        String html = "<h1>Hello <span th:text=\"${text.content}\"></span></h1>";

        DocumentBuilder db = Document.fromHtmlTemplate(html, args);

        assertThatHtml(db.toHtml())
                .isEqualTo("<h1>Hello <span>World</span></h1>");

        assertThat(db.toPdf())
                .contains("Hello")
                .contains("World");
    }

    private AbstractCharSequenceAssert<?, String> assertThatHtml(String html) {
        return Assertions.assertThat(html);
    }
}