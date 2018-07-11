package com.tratif.pdfgen.document.docs;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

public class HtmlDocumentTest {

	@Test
	public void whenCreatedWithFileThenProperlyReturnsInputStream() throws IOException {
		File file = createTempFile();
		HtmlDocument htmlDocument = new HtmlDocument(file);

		assertThat(htmlDocument.asInputStream())
				.isNotNull();
	}

	@Test
	public void whenCreatedWithInputStreamThenProperlyReturnsFile() throws IOException {
		File file = createTempFile();
		FileInputStream fis = new FileInputStream(file);

		HtmlDocument htmlDocument = new HtmlDocument(fis);

		assertThat(htmlDocument.asFile())
				.exists();
	}

	@Test
	public void properlyReturnsStringContent() throws IOException {
		String html = "<html><body>test</body></html>";
		File file = createTempFile();
		Files.write(file.toPath(), html.getBytes());

		assertThat(new HtmlDocument(file).asString())
				.isEqualTo(html);
	}

	private File createTempFile() throws IOException {
		File file = File.createTempFile("pdfgen", ".html");
		file.deleteOnExit();
		return file;
	}
}
