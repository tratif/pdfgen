package com.tratif.pdfgen.document.renderers.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

public class SimpleHtmlMerger implements HtmlMerger {

	@Override
	public String merge(List<String> htmls) {
		if (htmls.size() == 1) {
			return htmls.get(0);
		}

		Document base = Jsoup.parse("<html><head></head><body></body></html>");
		htmls.forEach(html -> {
			Document doc = Jsoup.parse(html);
			base.head().append(doc.head().html());
			base.body().append(doc.body().html());
		});

		return base.html();
	}
}
