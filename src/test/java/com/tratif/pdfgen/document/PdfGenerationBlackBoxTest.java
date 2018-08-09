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

import com.tratif.pdfgen.document.renderers.html.HtmlTemplateEngine;
import org.junit.Test;
import org.thymeleaf.TemplateEngine;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import static com.tratif.pdfgen.asserts.PdfAssert.assertThat;

public class PdfGenerationBlackBoxTest {

	@Test
	public void pdfHasProperContentFromString() {
		String html = "<h1>Hello world</h1>";
		byte[] pdfDocument = Document.fromStaticHtml(html)
				.and()
				.toPdf()
				.toByteArray();

		assertThat(pdfDocument)
				.contains("Hello")
				.contains("world");
	}

	@Test
	public void pdfHasProperContentFromInputStream() {
		InputStream inputStream = new ByteArrayInputStream("<h1>Hello world</h1>".getBytes());
		byte[] pdfDocument = Document.fromStaticHtml(inputStream)
				.and()
				.toPdf()
				.toByteArray();

		assertThat(pdfDocument)
				.contains("Hello")
				.contains("world");
	}

	@Test
	public void pdfHasProperContentFromReader() {
		Reader reader = new StringReader("<h1>Hello world</h1>");
		byte[] pdfDocument = Document.fromStaticHtml(reader)
				.and()
				.toPdf()
				.toByteArray();

		assertThat(pdfDocument)
				.contains("Hello")
				.contains("world");

		Document.fromHtmlTemplate("123")
				.
	}
}
