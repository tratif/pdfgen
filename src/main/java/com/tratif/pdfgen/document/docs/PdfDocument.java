package com.tratif.pdfgen.document.docs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PdfDocument {

	private File file;

	public PdfDocument(File file) {
		this.file = file;
	}

	//todo verify if that is needed maybe File will be good to return as in HtmlDocument
	public String location() {
		return file.getAbsolutePath();
	}

	public byte[] toByteArray() {
		try {
			return Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			throw new RuntimeException("Unable to read file.");
		}
	}
}
