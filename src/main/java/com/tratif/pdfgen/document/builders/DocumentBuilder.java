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
import com.tratif.pdfgen.document.renderers.html.HtmlMerger;
import com.tratif.pdfgen.document.renderers.html.SimpleHtmlMerger;
import com.tratif.pdfgen.document.renderers.pdf.InputStreamPdfMerger;
import com.tratif.pdfgen.document.renderers.pdf.InputStreamPdfRenderer;
import com.tratif.pdfgen.document.renderers.pdf.PdfMerger;
import com.tratif.pdfgen.document.renderers.pdf.PdfRenderer;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class DocumentBuilder {

	private static final Logger log = LoggerFactory.getLogger(DocumentBuilder.class);

	private List<PageBuilder> pages;
	private PdfMerger merger = new InputStreamPdfMerger();
	private PdfRenderer renderer = new InputStreamPdfRenderer();
	private HtmlMerger htmlMerger = new SimpleHtmlMerger();

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
		if (pages.isEmpty())
			throw new IllegalStateException("Nothing to render");

		List<PDF> pdfs = renderer.render(pages);
		if (pdfs.size() == 1) {
			return pdfs.get(0);
		}

		return merger.merge(pdfs);
	}

	public String toHtml() {
		if(pages.isEmpty())
			throw new IllegalStateException("Nothing to render");

		List<String> htmls = pages.stream()
				.map(page -> {
					try {
						return IOUtils.toString(page.getContent(), "UTF-8");
					} catch (IOException e) {
						log.warn("Failed to read input stream.");
						return null;
					}
				})
				.collect(toList());

		return htmlMerger.merge(htmls);
	}
}
