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
import com.tratif.pdfgen.document.parsers.ToInputStreamParser;
import com.tratif.pdfgen.document.providers.ContentProvider;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

public class PageBuilder {

	private DocumentBuilder parentBuilder;
	private ParameterBuilder params;
	private HtmlDocument document;

	PageBuilder(DocumentBuilder parentBuilder) {
		this.parentBuilder = parentBuilder;
		params = new ParameterBuilder(this);
	}

	public ParameterBuilder withParameters() {
		params = new ParameterBuilder(this);
		return params;
	}

	public DocumentBuilder and() {
		document.setRenderParams(params.build());
		parentBuilder.addPage(document);
		return parentBuilder;
	}

	public PageBuilder fromStaticHtml(String html) {
		return fromStaticHtml(asFile(ToInputStreamParser.parse(html)));
	}

	public PageBuilder fromStaticHtml(Reader reader) {
		return fromStaticHtml(asFile(ToInputStreamParser.parse(reader)));
	}

	public PageBuilder fromStaticHtml(URL url) {
		return fromStaticHtml(asFile(ToInputStreamParser.parse(url)));
	}

	public PageBuilder fromStaticHtml(Path path) {
		return fromStaticHtml(asFile(ToInputStreamParser.parse(path)));
	}

	public PageBuilder fromStaticHtml(InputStream inputStream) {
		return fromStaticHtml(asFile(inputStream));
	}

	//------- From template -------

	public PageBuilder fromHtmlTemplate(String htmlTemplate, Map<String, Object> params) {
		return fromHtmlTemplate(
				asFile(ToInputStreamParser.parse(htmlTemplate)),
				params
		);
	}

	public PageBuilder fromHtmlTemplate(InputStream inputStream, Map<String, Object> params) {
		return fromHtmlTemplate(asFile(inputStream), params);
	}

	public PageBuilder fromHtmlTemplate(Reader reader, Map<String, Object> params) {
		return fromHtmlTemplate(
				asFile(ToInputStreamParser.parse(reader)),
				params
		);
	}

	public PageBuilder fromHtmlTemplate(URL url, Map<String, Object> params) {
		return fromHtmlTemplate(
				asFile(ToInputStreamParser.parse(url)),
				params
		);
	}

	public PageBuilder fromHtmlTemplate(Path path, Map<String, Object> params) {
		return fromHtmlTemplate(
				asFile(ToInputStreamParser.parse(path)),
				params
		);
	}

	public PageBuilder fromStaticHtml(File file) {
		document = new HtmlDocument(file.getPath());
		return this;
	}

	public PageBuilder fromHtmlTemplate(File file, Map<String, Object> params) {
		document = new HtmlTemplate(file.getPath(), params);
		return this;
	}

	private File asFile(InputStream inputStream) {
		try {
			String filename = "/tmp/" + UUID.randomUUID() + ".html";
			FileWriter fw = new FileWriter(filename);
			String inputString = IOUtils.toString(inputStream, "UTF-8");
			fw.write(inputString);
			fw.close();

			return new File(filename);
		} catch (IOException e) {
			throw new RuntimeException("Unable to save file for further processing.");
		}
	}
}
