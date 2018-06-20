package com.tratif.pdfgen.document.docs;

import java.util.Map;

public class HtmlTemplate extends HtmlDocument {

	private Map<String, Object> params;

	public HtmlTemplate(String filename, Map<String, Object> params) {
		super(filename);

		this.params = params;
	}

	public Map<String, Object> getParams() {
		return params;
	}
}
