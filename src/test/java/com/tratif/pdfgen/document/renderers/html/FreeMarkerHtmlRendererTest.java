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
import com.tratif.pdfgen.document.docs.HtmlTemplate;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.StringWriter;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class FreeMarkerHtmlRendererTest {

	private FreeMarkerHtmlRenderer renderer;

	@Before
	public void init() {
		renderer = new FreeMarkerHtmlRenderer();
	}

	@Test
	public void properlyRendersFtlTemplate() {
		Map<String, Object> context = ImmutableMap.of("variable", "test");

		HtmlTemplate htmlTemplate = new HtmlTemplate(new File("src/test/resources/test_template.ftl"), context);

		StringWriter sw = new StringWriter();
		renderer.render(htmlTemplate, sw);

		assertThat(sw.toString())
				.contains("test");
	}
}