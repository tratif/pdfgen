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

import com.tratif.pdfgen.document.Page;
import com.tratif.pdfgen.document.renderers.SimplePdfRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DocumentBuilder {

    private final static Logger log = LoggerFactory.getLogger(DocumentBuilder.class);

    private List<Page> pages;
    private Page currentPage;

    public DocumentBuilder() {
        pages = new ArrayList<>();
    }

    public DocumentBuilder(String html) {
        this();
        setCurrentPage(new Page(html));
    }

    public PageBuilder withPage() {
        setCurrentPage(new Page());
        return new PageBuilder(this, currentPage);
    }

    public byte[] toPdf() {
        if(pages.size() <= 0)
            throw new IllegalStateException("Nothing to render");

        return new SimplePdfRenderer().render(pages.get(0)).toByteArray();
    }

    private void setCurrentPage(Page currentPage) {
        this.currentPage = currentPage;
        pages.add(currentPage);
    }
}
