package com.tratif.pdfgen.document.renderers;

public class EngineNotSupportedException extends RuntimeException {
	public EngineNotSupportedException() {
		super("Html engine is not supported.");
	}
}
