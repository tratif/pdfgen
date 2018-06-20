package com.tratif.pdfgen.document.docs;

import com.tratif.pdfgen.document.renderers.HtmlRenderer;
import com.tratif.pdfgen.utils.FileNameGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.util.Objects.nonNull;

public class Page {

	private HtmlDocument htmlDocument;
	private HtmlTemplate htmlTemplate;

	private HtmlRenderer htmlRenderer;

	public Page(HtmlDocument htmlDocument) {
		this.htmlDocument = htmlDocument;
	}

	public Page(HtmlTemplate htmlTemplate, HtmlRenderer htmlRenderer) {
		this.htmlTemplate = htmlTemplate;
		this.htmlRenderer = htmlRenderer;
	}

	public HtmlDocument render() {
		if (nonNull(htmlDocument)) {
			return htmlDocument;
		}

		File file = FileNameGenerator.asFile("html");
		try {
			htmlRenderer.render(htmlTemplate, new FileWriter(file));
			return new HtmlDocument(file);
		} catch (IOException e) {
			throw new RuntimeException("Error while generating HTML from template.");
		}
	}
}
