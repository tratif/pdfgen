package com.tratif.pdfgen.document;

import com.tratif.pdfgen.asserts.helpers.SimpleParameter;
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

        Assertions.assertThat(db.toHtml())
                .contains("myContent");
    }
}