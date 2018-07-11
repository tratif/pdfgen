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

import com.google.common.collect.ImmutableMap;
import com.tratif.pdfgen.asserts.helpers.SimpleParameter;
import com.tratif.pdfgen.document.builders.DocumentBuilder;
import com.tratif.pdfgen.document.docs.PdfDocument;
import com.tratif.pdfgen.document.renderers.html.HtmlTemplateEngine;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

import static com.tratif.pdfgen.asserts.PdfAssert.assertThat;

public class DocumentBuilderTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void generatesProperPdfFile() {
		PdfDocument pdf = Document.fromStaticHtml("<h1>hello, world!</h1>")
				.withTemplateEngine(HtmlTemplateEngine.FREEMARKER)
				.and()
				.toPdf();

		assertThat(pdf.toByteArray())
				.isProperPdfFile();
	}

	@Test
	public void rendersPdfWithAdditionalParameters() {
		byte[] pdf = Document.fromStaticHtml("<h1>This is first page</h1>")
				.withTemplateEngine(HtmlTemplateEngine.FREEMARKER)
				.and()
				.withParameters()
				.noBackground()
				.zoom(2)
				.and()
				.toPdf().toByteArray();

		assertThat(pdf)
				.contains("This")
				.contains("first")
				.contains("page");
	}

	@Test
	public void rendersPdfWithMultiplePages() {
		byte[] pdf = Document.withPage()
				.fromStaticHtml("<h1>Title</h1>")
					.withTemplateEngine(HtmlTemplateEngine.FREEMARKER)
				.and()
				.withPage()
				.fromStaticHtml("<p>Second page</p>")
					.withTemplateEngine(HtmlTemplateEngine.FREEMARKER)
				.and()
				.toPdf().toByteArray();

		assertThat(pdf)
				.contains("Title")
				.contains("Second")
				.contains("page");
	}

	@Test
	public void rendersPdfWithMultiplePagesAndParameters() {
		byte[] pdfDocument = Document.withPage()
				.fromStaticHtml("<h1>Title</h1>")
				.and()
				.withPage()
				.fromStaticHtml("<p>Second page</p>")
				.and()
				.withParameters()
				.noBackground()
				.a4()
				.and()
				.toPdf().toByteArray();

		assertThat(pdfDocument)
				.contains("Title")
				.contains("Second")
				.contains("page");
	}

	@Test
	public void bindsParametersToSingleHtmlTemplate() {
		Map<String, Object> params = ImmutableMap.of("testObject", new SimpleParameter("testContent"));

		byte[] pdf = Document.withPage()
				.fromHtmlTemplate("<html><head></head><body><span th:text=\"${testObject.content}\">TEST</span></body></html>", params)
				.and()
				.toPdf().toByteArray();

		assertThat(pdf)
				.contains("testContent");
	}

	@Test
	public void bindParametersToMultipleHtmlTemplates() {
		Map<String, Object> params = ImmutableMap.of(
				"testObject", new SimpleParameter("testContent"),
				"testObject2", new SimpleParameter("testContent2")
		);

		byte[] pdf = Document.withPage()
				.fromHtmlTemplate("<span th:text=\"${testObject.content}\">TEST</span>", params)
				.and()
				.withPage()
				.fromHtmlTemplate("<p th:text=\"${testObject2.content}\">TEST</p>", params)
				.and()
				.toPdf().toByteArray();

		assertThat(pdf)
				.contains("testContent")
				.contains("testContent2");
	}

	@Test
	public void rendersProperPdfFromHtmlTemplateAndParameters() {
		Map<String, Object> params = ImmutableMap.of(
				"testObject", new SimpleParameter("testContent"),
				"testObject2", new SimpleParameter("testContent2")
		);

		byte[] pdf = Document.withPage()
				.fromHtmlTemplate("<span th:text=\"${testObject.content}\">TEST</span>", params)
				.and()
				.withPage()
				.fromHtmlTemplate("<p th:text=\"${testObject2.content}\">TEST</p>", params)
				.and()
				.withParameters()
				.zoom(2)
				.landscape()
				.grayscale()
				.and()
				.toPdf().toByteArray();

		assertThat(pdf)
				.contains("testContent")
				.contains("testContent2");
	}

	@Test
	public void whenNoPagesToRenderThenIllegalStateException() {
		exception.expect(IllegalStateException.class);
		exception.expectMessage("nothing to render");

		new DocumentBuilder().toPdf();
	}
}