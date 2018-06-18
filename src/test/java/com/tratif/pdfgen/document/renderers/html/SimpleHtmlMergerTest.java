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

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class SimpleHtmlMergerTest {

	private SimpleHtmlMerger htmlMerger = new SimpleHtmlMerger();

	@Test
	public void mergesTwoHtmlsToOne() {
		String html1 = "<html><head><link href=\"head1\"></head><body><p>body1</p></body></html>";
		String html2 = "<html><head><link href=\"head2\"></head><body><p>body2</p></body></html>";

		List<String> htmls = Arrays.asList(html1, html2);

		assertThat(htmlMerger.merge(htmls))
				.contains(
						"<link href=\"head1\">",
						"<link href=\"head2\">",
						"<p>body1</p>",
						"<p>body2</p>"
				);
	}

}