package com.tratif.pdfgen.document.template;

import java.util.HashMap;
import java.util.Map;

public abstract class RenderableDocument {

	private String filename;
	private Map<String, String> terminalParams;

	public RenderableDocument(String filename) {
		this.filename = filename;
		terminalParams = new HashMap<>();
	}

	public String getFilename() {
		return filename;
	}

	public Map<String, String> getTerminalParams() {
		return terminalParams;
	}

	public void setTerminalParams(Map<String, String> terminalParams) {
		this.terminalParams = terminalParams;
	}
}
