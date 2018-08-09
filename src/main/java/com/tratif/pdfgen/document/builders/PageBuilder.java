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

import com.tratif.pdfgen.document.docs.HtmlDocument;
import com.tratif.pdfgen.document.docs.HtmlTemplate;
import com.tratif.pdfgen.document.parsers.ToInputStreamParser;
import com.tratif.pdfgen.document.renderers.html.HtmlTemplateEngine;
import com.tratif.pdfgen.exceptions.PdfgenException;
import com.tratif.pdfgen.utils.ToFileConverter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Path;
import java.util.Map;

import static java.util.Objects.isNull;

public class PageBuilder {

	private DocumentBuilder parentBuilder;
	private HtmlDocument htmlDocument;
	private HtmlTemplate htmlTemplate;
	private HtmlTemplateEngine templateEngine;

	PageBuilder(DocumentBuilder parentBuilder) {
		this.parentBuilder = parentBuilder;

		templateEngine = HtmlTemplateEngine.THYMELEAF;
	}

	public DocumentBuilder and() {
		if (isNull(htmlDocument) && isNull(htmlTemplate)) {
			throw new IllegalStateException("No content added to page.");
		}

		if (!isNull(htmlDocument)) {
			parentBuilder.addPage(htmlDocument);
		} else {
			parentBuilder.addPage(htmlTemplate, templateEngine);
		}

		return parentBuilder;
	}

	public PageBuilder fromStaticHtml(String html) {
		try {
			File file = ToFileConverter.convert(html, "html");
			return fromStaticHtml(file);
		} catch (IOException e) {
			throw new PdfgenException("Failed to save template to file for further processing.", e);
		}
	}

	public PageBuilder fromStaticHtml(Reader reader) {
		return fromStaticHtml(ToInputStreamParser.parse(reader));
	}

	public PageBuilder fromStaticHtml(URL url) {
		return fromStaticHtml(ToInputStreamParser.parse(url));
	}

	public PageBuilder fromStaticHtml(Path path) {
		return fromStaticHtml(ToInputStreamParser.parse(path));
	}

	public PageBuilder fromStaticHtml(InputStream inputStream) {
		htmlDocument = new HtmlDocument(inputStream);
		return this;
	}

	public PageBuilder fromStaticHtml(File file) {
		htmlDocument = new HtmlDocument(file);
		return this;
	}

	//------- From template -------

	public PageBuilder fromHtmlTemplate(String htmlTemplate, Map<String, Object> params) {
		try {
			File file = ToFileConverter.convert(htmlTemplate, "html");
			return fromHtmlTemplate(
					file,
					params
			);
		} catch (IOException e) {
			throw new PdfgenException("Failed to save template to file for further processing.", e);
		}
	}

	public PageBuilder fromHtmlTemplate(Reader reader, Map<String, Object> params) {
		return fromHtmlTemplate(
				ToInputStreamParser.parse(reader),
				params
		);
	}

	public PageBuilder fromHtmlTemplate(URL url, Map<String, Object> params) {
		return fromHtmlTemplate(
				ToInputStreamParser.parse(url),
				params
		);
	}

	public PageBuilder fromHtmlTemplate(Path path, Map<String, Object> params) {
		return fromHtmlTemplate(
				ToInputStreamParser.parse(path),
				params
		);
	}

	public PageBuilder fromHtmlTemplate(InputStream inputStream, Map<String, Object> params) {
		htmlTemplate = new HtmlTemplate(inputStream, params);
		return this;
	}

	public PageBuilder fromHtmlTemplate(File file, Map<String, Object> params) {
		htmlTemplate = new HtmlTemplate(file, params);
		return this;
	}

	public PageBuilder withTemplateEngine(HtmlTemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
		return this;
	}
}
