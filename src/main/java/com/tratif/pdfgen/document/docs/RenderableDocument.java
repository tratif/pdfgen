package com.tratif.pdfgen.document.docs;

import java.util.HashMap;
import java.util.Map;

public abstract class RenderableDocument {

	private String filename;
	private Map<String, String> renderParams;

	public RenderableDocument(String filename) {
		this.filename = filename;
		renderParams = new HashMap<>();
	}

	public String getFilename() {
		return filename;
	}

	public Map<String, String> getRenderParams() {
		return renderParams;
	}

	public void setRenderParams(Map<String, String> renderParams) {
		this.renderParams = renderParams;
	}
}
