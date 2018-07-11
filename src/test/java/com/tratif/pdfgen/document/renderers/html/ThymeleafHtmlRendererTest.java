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
package com.tratif.pdfgen.document.renderers.html;

import com.google.common.collect.ImmutableMap;
import com.tratif.pdfgen.asserts.helpers.SimpleParameter;
import com.tratif.pdfgen.document.docs.HtmlTemplate;
import com.tratif.pdfgen.utils.ToFileConverter;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.Map;

import static com.tratif.pdfgen.asserts.helpers.TestUtils.asFile;
import static org.assertj.core.api.Assertions.assertThat;

public class ThymeleafHtmlRendererTest {

	private ThymeleafHtmlRenderer renderer;

	@Before
	public void init() {
		renderer = new ThymeleafHtmlRenderer();
	}

	@Test
	public void properlyRendersJava8TimeDialect() throws IOException {
		String template = "<div th:text=\"${#temporals.format(time, 'YYYY-MM-dd HH:mm:ss')}\"></div>";
		Map<String, Object> vars = ImmutableMap.of(
				"time",
				LocalDateTime.of(2012, 3, 15, 7, 3, 20)
		);

		HtmlTemplate htmlTemplate = new HtmlTemplate(ToFileConverter.convert(template, "html"), vars);

		Writer writer = new StringWriter();

		renderer.render(htmlTemplate, writer);
		assertThat(writer.toString())
				.isEqualTo("<div>2012-03-15 07:03:20</div>");
	}

	@Test
	public void bindsParametersToThymeleafHtmlTemplate() throws IOException {
		String template = "<html><head></head><body><span th:text=\"${testObject.content}\"></span></body></html>";
		Map<String, Object> params = ImmutableMap.of("testObject", new SimpleParameter("testContent"));

		HtmlTemplate htmlTemplate = new HtmlTemplate(asFile(template), params);
		StringWriter sw = new StringWriter();
		renderer.render(htmlTemplate, sw);

		assertThat(sw.toString())
				.contains("testContent");
	}
}