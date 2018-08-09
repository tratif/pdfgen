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
package com.tratif.pdfgen.document.mergers.pdf;

import com.tratif.pdfgen.document.docs.HtmlDocument;
import com.tratif.pdfgen.document.mergers.html.JsoupHtmlMerger;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.tratif.pdfgen.asserts.helpers.TestUtils.asFile;
import static org.assertj.core.api.Assertions.assertThat;

public class JsoupHtmlMergerTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	private JsoupHtmlMerger htmlMerger = new JsoupHtmlMerger();

	@Test
	public void properlyMergesTwoHtmlDocuments() throws IOException {
		String html1 = "<html><head><link href=\"head1\"></head><body><p>body1</p></body></html>";
		String html2 = "<html><head><link href=\"head2\"></head><body><p>body2</p></body></html>";

		List<HtmlDocument> htmlDocuments = Arrays.asList(
				new HtmlDocument(asFile(html1)),
				new HtmlDocument(asFile(html2))
		);

		String mergedHtml = htmlMerger.merge(htmlDocuments).toString();
		assertThat(mergedHtml)
				.contains(
						"<link href=\"head1\">",
						"<link href=\"head2\">",
						"<p>body1</p>",
						"<p>body2</p>"
				);
	}
}