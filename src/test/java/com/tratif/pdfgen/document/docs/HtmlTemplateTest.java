package com.tratif.pdfgen.document.docs;

import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class HtmlTemplateTest {

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Test
	public void givenInputStreamWhenAsFileThenFileWithProperContentIsReturned() throws IOException {
		InputStream inputStream = new ByteArrayInputStream("test".getBytes());
		HtmlTemplate template = new HtmlTemplate(inputStream, new HashMap<>());

		assertThat(Files.readAllLines(template.asFile().toPath()).get(0))
				.isEqualTo("test");
	}

	@Test
	public void givenFileWhenAsInputStreamThenInputStreamWithProperContentIsReturned() throws IOException {
		File file = temporaryFolder.newFile();
		Files.write(file.toPath(), "test".getBytes());
		HtmlTemplate template = new HtmlTemplate(file, new HashMap<>());

		String stringFromFile = new String(IOUtils.toByteArray(template.asInputStream()));

		assertThat(stringFromFile)
				.isEqualTo("test");
	}
}