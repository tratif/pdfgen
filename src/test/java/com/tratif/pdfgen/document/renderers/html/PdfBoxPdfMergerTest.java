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
package com.tratif.pdfgen.document.renderers.html;

import com.tratif.pdfgen.document.docs.HtmlDocument;
import com.tratif.pdfgen.document.mergers.html.JsoupHtmlMerger;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PdfBoxPdfMergerTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	private JsoupHtmlMerger htmlMerger = new JsoupHtmlMerger();

	@Test
	public void mergesTwoHtmlsToOne() throws IOException {
		String html1 = "<html><head><link href=\"head1\"></head><body><p>body1</p></body></html>";
		String html2 = "<html><head><link href=\"head2\"></head><body><p>body2</p></body></html>";

		List<HtmlDocument> htmls = Arrays.asList(
				new HtmlDocument(asFile(html1)),
				new HtmlDocument(asFile(html2))
		);

		assertThat(htmlMerger.merge(htmls).asString())
				.contains(
						"<link href=\"head1\">",
						"<link href=\"head2\">",
						"<p>body1</p>",
						"<p>body2</p>"
				);
	}

	private File asFile(String content) throws IOException {
		File file = folder.newFile();
		Files.write(file.toPath(), content.getBytes());
		file.deleteOnExit();
		return file;
	}
}