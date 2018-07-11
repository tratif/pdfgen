package com.tratif.pdfgen.exceptions;

public class PdfgenException extends RuntimeException {

	public PdfgenException() {
		super();
	}

	public PdfgenException(String s) {
		super(s);
	}

	public PdfgenException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public PdfgenException(Throwable throwable) {
		super(throwable);
	}
}
