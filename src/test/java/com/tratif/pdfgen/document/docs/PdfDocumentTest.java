package com.tratif.pdfgen.document.docs;

import com.tratif.pdfgen.document.Document;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.tratif.pdfgen.asserts.PdfAssert.assertThat;

public class PdfDocumentTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void containsProperData() {
		PdfDocument pdf = Document.fromStaticHtml("<h1>First page</h1>")
				.and()
				.toPdf();

		assertThat(pdf.toByteArray())
				.isProperPdfFile()
				.contains("First")
				.contains("page");
	}
}