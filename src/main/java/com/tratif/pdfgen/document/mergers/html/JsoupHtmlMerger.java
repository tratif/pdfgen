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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class JsoupHtmlMerger implements HtmlMerger {

//	@Override
//	public String merge(List<String> htmls) {
//		if (htmls.size() == 1) {
//			return htmls.get(0);
//		}
//
//		Document base = Jsoup.parse("<html><head></head><body></body></html>");
//		htmls.forEach(html -> {
//			Document doc = Jsoup.parse(html);
//			base.head().append(doc.head().html());
//			base.body().append(doc.body().html());
//		});
//
//		return base.html();
//	}

	@Override
	public String merge(List<HtmlDocument> htmls) {
		List<String> htmlStrings = convertToStrings(htmls);

		if (htmlStrings.size() == 1) {
			return htmlStrings.get(0);
		}

		Document base = Jsoup.parse("<html><head></head><body></body></html>");
		htmlStrings.forEach(html -> {
			Document doc = Jsoup.parse(html);
			base.head().append(doc.head().html());
			base.body().append(doc.body().html());
		});

		return base.html();
	}

	private List<String> convertToStrings(List<HtmlDocument> htmls) {
		return htmls.stream()
				.map(html -> {
					try {
						return new String(Files.readAllBytes(Paths.get(html.getFilename())), "UTF-8");
					} catch (IOException e) {
						throw new RuntimeException("Failed to load file");
					}
				})
				.collect(toList());
	}
}
