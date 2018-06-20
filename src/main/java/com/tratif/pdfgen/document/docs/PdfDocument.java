package com.tratif.pdfgen.document.docs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PdfDocument extends RenderableDocument {

	public PdfDocument(String filename) {
		super(filename);
	}

	public byte[] toByteArray() {
		try {
			return Files.readAllBytes(Paths.get(getFilename()));
		} catch (IOException e) {
			throw new RuntimeException("Unable to read file.");
		}
	}
}
