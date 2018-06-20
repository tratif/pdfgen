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

import com.tratif.pdfgen.document.docs.HtmlDocument;
import com.tratif.pdfgen.document.docs.HtmlTemplate;
import com.tratif.pdfgen.document.docs.Page;
import com.tratif.pdfgen.document.docs.PdfDocument;
import com.tratif.pdfgen.document.mergers.html.HtmlMerger;
import com.tratif.pdfgen.document.mergers.html.JsoupHtmlMerger;
import com.tratif.pdfgen.document.providers.HtmlTemplateEngineProvider;
import com.tratif.pdfgen.document.renderers.PdfRenderer;
import com.tratif.pdfgen.document.renderers.html.HtmlTemplateEngine;
import com.tratif.pdfgen.document.renderers.pdf.FilePdfRenderer;
import com.tratif.pdfgen.utils.FileNameGenerator;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class DocumentBuilder {

	private List<Page> pages;
	private PdfRenderer pdfRenderer;
	private HtmlMerger htmlMerger;
	private ParameterBuilder renderParams;

	public DocumentBuilder() {
		pages = new ArrayList<>();
		pdfRenderer = new FilePdfRenderer();
		htmlMerger = new JsoupHtmlMerger();
		renderParams = new ParameterBuilder(this);
	}

	void addPage(HtmlDocument htmlDocument) {
		pages.add(new Page(htmlDocument));
	}

	void addPage(HtmlTemplate htmlTemplate, HtmlTemplateEngine templateEngine) {
		pages.add(new Page(
				htmlTemplate,
				HtmlTemplateEngineProvider.get(templateEngine)
		));
	}

	public PageBuilder withPage() {
		return new PageBuilder(this);
	}

	public ParameterBuilder withParameters() {
		renderParams = new ParameterBuilder(this);
		return renderParams;
	}


	public PdfDocument toPdf() {
		checkForEmptyPages();

		HtmlDocument htmlDocument = toHtml();
		return pdfRenderer.render(
				htmlDocument,
				FileNameGenerator.asString("pdf"),
				renderParams.build()
		);
	}

	public HtmlDocument toHtml() {
		checkForEmptyPages();

		List<HtmlDocument> renderedPages = renderPages();
		return mergePages(renderedPages);
	}

	private HtmlDocument mergePages(List<HtmlDocument> renderedPages) {
		return htmlMerger.merge(renderedPages);
	}

	private List<HtmlDocument> renderPages() {
		return pages.stream()
				.map(Page::render)
				.collect(toList());
	}

	private void checkForEmptyPages() {
		if (pages.isEmpty()) {
			throw new IllegalStateException("There is nothing to render.");
		}
	}
}
