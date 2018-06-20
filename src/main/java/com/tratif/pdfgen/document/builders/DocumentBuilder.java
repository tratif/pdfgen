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
import com.tratif.pdfgen.document.docs.PdfDocument;
import com.tratif.pdfgen.document.mergers.html.JsoupHtmlMerger;
import com.tratif.pdfgen.document.providers.HtmlTemplateEngineProvider;
import com.tratif.pdfgen.document.renderers.HtmlRenderer;
import com.tratif.pdfgen.document.renderers.PdfRenderer;
import com.tratif.pdfgen.document.renderers.html.HtmlTemplateEngine;
import com.tratif.pdfgen.document.renderers.pdf.FilePdfRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

public class DocumentBuilder {

	private static final Logger log = LoggerFactory.getLogger(DocumentBuilder.class);

	private List<HtmlDocument> pages;
	private PdfRenderer pdfRenderer;
	private HtmlTemplateEngine templateEngine;

	public DocumentBuilder() {
		pages = new ArrayList<>();
		pdfRenderer = new FilePdfRenderer();
		templateEngine = HtmlTemplateEngine.THYMELEAF;
	}

	void addPage(HtmlDocument document) {
		pages.add(document);
	}

	public PageBuilder withPage() {
		return new PageBuilder(this);
	}

	public DocumentBuilder withTemplateEngine(HtmlTemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
		return this;
	}

	public PdfDocument toPdf() {
		checkForEmptyPages();

		HtmlDocument htmlDocument = toHtml();
		return pdfRenderer.render(htmlDocument, getFilename("pdf"));
	}

	public HtmlDocument toHtml() {
		checkForEmptyPages();
		HtmlRenderer htmlRenderer = HtmlTemplateEngineProvider.get(templateEngine);

		if (pages.size() > 1) {
			return mergePages(htmlRenderer);
		}

		return renderPages(htmlRenderer).get(0);
	}

	private HtmlDocument mergePages(HtmlRenderer htmlRenderer) {
		List<HtmlDocument> renderedPages = renderPages(htmlRenderer);
		String merge = new JsoupHtmlMerger().merge(renderedPages);
		String filename = getFilename("html");
		try {
			Files.write(Paths.get(filename), merge.getBytes());
		} catch (IOException e) {
			throw new RuntimeException("Failed to merge htmls");
		}

		return new HtmlDocument(filename);
	}

	private List<HtmlDocument> renderPages(HtmlRenderer htmlRenderer) {
		return pages.stream()
				.map(page -> {
					if (page instanceof HtmlTemplate) {
						try {
							String filename = getFilename("html");
							htmlRenderer.render((HtmlTemplate) page, new FileWriter(filename));
							return new HtmlDocument(filename);
						} catch (IOException e) {
							throw new RuntimeException("Unable to render html.");
						}
					}

					return page;
				})
				.collect(toList());
	}

	private String getFilename(String extension) {
		return "/tmp/" + UUID.randomUUID() + "." + extension;
	}

	private void checkForEmptyPages() {
		if (pages.isEmpty()) {
			throw new IllegalStateException("There is nothing to render.");
		}
	}

	//----------------------

//	public PageBuilder withPage() {
//		PageBuilder pageBuilder = new PageBuilder(this);
//		pages.add(pageBuilder);
//		return pageBuilder;
//	}

//	public byte[] toPdf() {
//		return toPdfObject().toByteArray();
//	}

//	public PDF toPdfObject() {
//		if (pages.isEmpty()) {
//			throw new IllegalStateException("Nothing to render");
//		}
//
//		List<PDF> pdfs = renderer.render(pages);
//		if (pdfs.size() == 1) {
//			return pdfs.get(0);
//		}
//
//		return merger.merge(pdfs);
//	}

//	public String toHtml(HtmlTemplateEngine templateEngine) {
//	public String toHtml() {
//		// TODO: 6/18/18 ...
////		HtmlRenderer htmlRenderer = HtmlTemplateEngineProvider.get(templateEngine);
//
//		if(pages.isEmpty()) {
//			throw new IllegalStateException("Nothing to render");
//		}
//
//		List<String> htmls = pages.stream()
//				.map(page -> {
//					try {
//						return IOUtils.toString(page.getContent(), "UTF-8");
//					} catch (IOException e) {
//						log.warn("Failed to read input stream.");
//						return null;
//					}
//				})
//				.collect(toList());
//
//		return htmlMerger.merge(htmls);
//	}
}
