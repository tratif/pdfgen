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
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.tratif.pdfgen.asserts.PdfAssert.*;

public class PDFTest {

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
}