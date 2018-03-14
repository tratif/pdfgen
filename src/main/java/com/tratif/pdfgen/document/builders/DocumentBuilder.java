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
package com.tratif.pdfgen.document.builders;

import com.tratif.pdfgen.document.PDF;
import com.tratif.pdfgen.document.renderers.PdfMerger;
import com.tratif.pdfgen.document.renderers.PdfRenderer;
import com.tratif.pdfgen.document.renderers.InputStreamPdfMerger;
import com.tratif.pdfgen.document.renderers.InputStreamPdfRenderer;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DocumentBuilder {

    private final static Logger log = LoggerFactory.getLogger(DocumentBuilder.class);

    private List<PageBuilder> pages;
    private PdfMerger merger = new InputStreamPdfMerger();
    private PdfRenderer renderer = new InputStreamPdfRenderer();

    public DocumentBuilder() {
        pages = new ArrayList<>();
    }

    public PageBuilder withPage() {
        PageBuilder pageBuilder = new PageBuilder(this);
        pages.add(pageBuilder);
        return pageBuilder;
    }

    public byte[] toPdf() {
        return toPdfObject().toByteArray();
    }

    public PDF toPdfObject() {
        if (pages.size() <= 0)
            throw new IllegalStateException("Nothing to render");

        List<PDF> pdfs = renderer.render(pages);
        if (pdfs.size() == 1) {
            return pdfs.get(0);
        }

        return merger.merge(pdfs, MemoryUsageSetting.setupMainMemoryOnly(1024 * 1024 * 128));
    }
}
