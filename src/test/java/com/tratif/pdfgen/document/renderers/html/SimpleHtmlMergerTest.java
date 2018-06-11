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