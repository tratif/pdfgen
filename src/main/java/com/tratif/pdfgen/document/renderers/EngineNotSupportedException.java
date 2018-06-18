package com.tratif.pdfgen.document.renderers.html;

public class HtmlEngineNotSupportedException extends RuntimeException {
	public HtmlEngineNotSupportedException() {
		super("Html engine is not supported.");
	}
}
