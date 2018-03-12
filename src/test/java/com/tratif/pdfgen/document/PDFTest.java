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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import static com.tratif.pdfgen.asserts.PdfAssert.*;

public class PDFTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void savingMergedPagesIntoOneFile() throws IOException {
        PDF pdf = new PDF();
        pdf.addPage(new Page("<h1>First page</h1>"));
        pdf.addPage(new Page("<h1>Second page</h1>"));

        File temp = temporaryFolder.newFile();
        pdf.save(temp);

        assertThat(Files.readAllBytes(temp.toPath()))
                .isProperPdfFile()
                .contains("First")
                .contains("page")
                .contains("Second");
    }

    @Test
    public void throwsIllegalStateExceptionWhenTryingToSaveZeroPages() {
        PDF pdf = new PDF();

        exception.expect(IllegalStateException.class);
        exception.expectMessage("Nothing to save");

        pdf.save(new File(""));
    }

    @Test
    public void throwsIllegalStateExceptionWhenTryingToSaveToPathThatDoesntExist() {
        PDF pdf = new PDF();
        pdf.addPage(new Page("content"));

        exception.expect(IllegalStateException.class);
        exception.expectMessage("path does not exist");

        pdf.save(new File("sdfsddhdf/sdfsghazhfg/agahaf/dsfss.pdf"));
    }

    @Test
    public void savesSinglePageToPdfFile() throws IOException {
        PDF pdf = new PDF();
        pdf.addPage(new Page("content"));
        Path filepath = Files.createTempFile("pdfgen", ".pdf");

        pdf.save(filepath.toFile());

        assertThat(Files.readAllBytes(filepath))
                .isProperPdfFile()
                .contains("content");
    }

    @Test
    public void savesMultiplePagesToPdfFile() throws IOException {
        PDF pdf = new PDF();
        pdf.addPage(new Page("first"));
        pdf.addPage(new Page("second"));
        pdf.addPage(new Page("third"));
        Path filepath = Files.createTempFile("pdfgen", ".pdf");

        pdf.save(filepath.toFile());

        assertThat(Files.readAllBytes(filepath))
                .isProperPdfFile()
                .hasPagesCount(3)
                .contains("first")
                .contains("second")
                .contains("third");
    }

    //fluent builder
    public void ___() {
        Document.withPage()
                    .fromStaticHtml("<p>Hello world</p>")
                    .and()
                .withPage()
                    .fromHtmlTemplate("Template", new HashMap<>())
                    .withParameters()
                        .zoom(1.33)
                        .noBackground()
                    .and()
                .toPdf();

        Document.fromStaticHtml("123")
                .toPdf();

        Document.withPage()
                    .fromStaticHtml("123");
    }
}