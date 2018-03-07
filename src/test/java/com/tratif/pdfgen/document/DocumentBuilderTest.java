/**
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    @Test
    public void rendersPdfWithAdditionalParameters() {
        DocumentBuilder db = Document.fromStaticHtml("<h1>This is first page</h1>");
        db.parameters()
                .noBackground()
                .zoom(2);

        assertThat(db.toPdf())
                .isProperPdfFile()
                .contains("This")
                .contains("first")
                .contains("page");
    }

    private AbstractCharSequenceAssert<?, String> assertThatHtml(String html) {
        return Assertions.assertThat(html);
    }
}