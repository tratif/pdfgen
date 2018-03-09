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

import com.tratif.pdfgen.asserts.PdfAssert;
import org.assertj.core.api.AbstractAssert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.*;

public class PageTest {

    @Test
    public void savesContentToTempFile() throws Exception {
        String content = "<p>Test content</p>";
        Page page = new Page(content);

        String retrievedFromFile = new String(Files.readAllBytes(page.getContentFile().toPath()));

        assertThat(retrievedFromFile)
                .isNotEmpty()
                .isEqualTo("<p>Test content</p>");
    }

    @Test
    public void rendersProperPdfFile() throws IOException {
        String content = "<p>Test content</p>";
        Page page = new Page(content);
        page.render();

        byte[] renderedPdf = Files.readAllBytes(page.getContentFile().toPath());

        assertThatPdf(renderedPdf)
                .isProperPdfFile()
                .contains("Test")
                .contains("content");
    }

    private PdfAssert assertThatPdf(byte[] actual) {
        return PdfAssert.assertThat(actual);
    }
}
