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
package com.tratif.pdfgen.document.docs;

import com.tratif.pdfgen.document.renderers.HtmlRenderer;
import com.tratif.pdfgen.exceptions.PdfgenException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import static java.util.Objects.nonNull;

public class Page {

	private HtmlDocument htmlDocument;
	private HtmlTemplate htmlTemplate;

	private HtmlRenderer htmlRenderer;

	public Page(HtmlDocument htmlDocument) {
		this.htmlDocument = htmlDocument;
	}

	public Page(HtmlTemplate htmlTemplate, HtmlRenderer htmlRenderer) {
		this.htmlTemplate = htmlTemplate;
		this.htmlRenderer = htmlRenderer;
	}

	public HtmlDocument render() {
		if (nonNull(htmlDocument)) {
			return htmlDocument;
		}

		try {
			File file = Files.createTempFile("pdfgen", ".html").toFile();
			htmlRenderer.render(htmlTemplate, new FileWriter(file));
			return new HtmlDocument(file);
		} catch (IOException e) {
			throw new PdfgenException("Error while generating HTML from template.", e);
		}
	}
}
