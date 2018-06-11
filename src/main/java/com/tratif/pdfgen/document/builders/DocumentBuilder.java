/**
 * Copyright 2018 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tratif.pdfgen.document.builders;

import com.tratif.pdfgen.document.PDF;
import com.tratif.pdfgen.document.renderers.html.HtmlMerger;
import com.tratif.pdfgen.document.renderers.html.HtmlRenderer;
import com.tratif.pdfgen.document.renderers.html.SimpleHtmlMerger;
import com.tratif.pdfgen.document.renderers.html.ThymeleafHtmlRenderer;
import com.tratif.pdfgen.document.renderers.pdf.InputStreamPdfMerger;
import com.tratif.pdfgen.document.renderers.pdf.InputStreamPdfRenderer;
import com.tratif.pdfgen.document.renderers.pdf.PdfMerger;
import com.tratif.pdfgen.document.renderers.pdf.PdfRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class DocumentBuilder {

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
		List<String> htmls = pages.stream()
				.map(PageBuilder::toHtml)
				.collect(toList());

		return htmlMerger.merge(htmls);
	}
}
