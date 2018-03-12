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
import com.tratif.pdfgen.asserts.PdfAssert;
import com.tratif.pdfgen.asserts.helpers.SimpleParameter;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

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
    public void parametersAreBoundToHtmlTemplate() {
        Map<String, Object> args = ImmutableMap.of("text", new SimpleParameter("myContent"));
        String htmlTemplate = "<h1>First page</h1><p th:text=\"${text.content}\"></p>";

        assertThat(Document.fromHtmlTemplate(htmlTemplate, args))
                .isEqualTo("<h1>First page</h1><p>myContent</p>");
    }
}