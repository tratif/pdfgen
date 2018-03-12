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

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class PDF implements AutoCloseable {

    private final static Logger log = LoggerFactory.getLogger(PDF.class);

    private List<Page> pages;

    public PDF() {
        pages = new ArrayList<>();
    }

    public void addPage(Page page) {
        pages.add(page);
    }

    public void addPages(Collection<Page> pages) {
        this.pages.addAll(pages);
    }

    public void addPages(Page ...pages) {
        this.pages.addAll(Arrays.asList(pages));
    }

    public void save(File file) {
        if(pages.size() <= 0)
            throw new IllegalStateException("Nothing to save.");

        if(!file.toPath().getParent().toFile().exists())
            throw new IllegalStateException("Given path does not exist.");

        log.info("Indexing {} pages...", pages.size());

        PDFMergerUtility merger = new PDFMergerUtility();
        pages.forEach(page -> {
            try {
                merger.addSource(page.getContentFile());
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Critical error: temp file was not found.", e);
            }
        });

        log.info("Done.");
        log.info("Rendering pages...");

        pages.forEach(Page::render);

        log.info("Done.");
        log.info("Merging files...");

        merger.setDestinationFileName(file.toString());
        try {
            merger.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
        } catch (IOException e) {
            throw new RuntimeException("Critical error: failed to merge to final file.", e);
        }

        log.info("Done.");
        log.info("PDF was successfully created.");
    }

    @Override
    public void close() {
        pages.forEach(page -> page.getContentFile().delete());
    }
}
