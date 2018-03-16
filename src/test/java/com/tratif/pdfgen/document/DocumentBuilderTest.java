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

    @Test
    public void rendersPdfWithAdditionalParameters() {
        byte[] pdf = Document.fromStaticHtml("<h1>This is first page</h1>")
                .withParameters()
                    .noBackground()
                    .zoom(2)
                .toPdf();

        assertThat(pdf)
                .contains("This")
                .contains("first")
                .contains("page");
    }

    @Test
    public void rendersPdfWithMultiplePages() {
        byte[] pdf = Document.withPage()
                                .fromStaticHtml("<h1>Title</h1>")
                                .and()
                             .withPage()
                                .fromStaticHtml("<p>Second page</p>")
                            .and().toPdf();

        assertThat(pdf)
                .hasPagesCount(2)
                .contains("Title")
                .contains("Second")
                .contains("page");
    }

    @Test
    public void rendersPdfWithMultiplePagesAndParameters() {
        byte[] pdf = Document.withPage()
                    .fromStaticHtml("<h1>Title</h1>")
                    .withParameters()
                        .noBackground()
                        .a4()
                        .and()
                    .and()
                .withPage()
                    .fromStaticHtml("<p>Second page</p>")
                    .withParameters()
                        .noBackground()
                        .disableSmartShrinking()
                        .and()
                    .and()
                .toPdf();

        assertThat(pdf)
                .hasPagesCount(2)
                .contains("Title")
                .contains("Second")
                .contains("page");
    }
}