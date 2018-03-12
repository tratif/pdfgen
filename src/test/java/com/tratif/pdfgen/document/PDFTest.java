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
                    .and()
                .toPdf();

        Document.fromStaticHtml("123")
                .toPdf();

        Document.withPage()
                    .fromStaticHtml("123")
                .and().toPdf();
    }
}