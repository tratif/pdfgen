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
package com.tratif.pdfgen.document.mergers.html;

import com.tratif.pdfgen.document.docs.HtmlDocument;
import com.tratif.pdfgen.utils.ToFileConverter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class JsoupHtmlMerger implements HtmlMerger {

	@Override
	public HtmlDocument merge(List<HtmlDocument> pages) {
		if (pages.size() == 1) {
			return pages.get(0);
		}

		List<String> htmlStrings = convertToStrings(pages);

		Document base = Jsoup.parse("<html><head></head><body></body></html>");
		htmlStrings.forEach(html -> {
			Document doc = Jsoup.parse(html);
			base.head().append(doc.head().html());
			base.body().append(doc.body().html());
		});

		try {
			return new HtmlDocument(
					ToFileConverter.convert(base.html(), "html")
			);
		} catch (IOException e) {
			throw new RuntimeException("Failed to save template file");
		}
	}

	private List<String> convertToStrings(List<HtmlDocument> pages) {
		return pages.stream()
				.map(html -> {
					try {
						return new String(Files.readAllBytes(Paths.get(html.asFile().getPath())), "UTF-8");
					} catch (IOException e) {
						throw new RuntimeException("Failed to load file");
					}
				})
				.collect(toList());
	}
}
