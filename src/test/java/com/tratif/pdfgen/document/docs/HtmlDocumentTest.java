/**
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tratif.pdfgen.document.docs;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

public class HtmlDocumentTest {

	@Test
	public void whenCreatedWithFileThenProperlyReturnsInputStream() throws IOException {
		File file = createTempFile();
		InputStream htmlDocument = new HtmlDocument(file).asInputStream();

		assertThat(htmlDocument)
				.isNotNull();
	}

	@Test
	public void whenCreatedWithInputStreamThenProperlyReturnsFile() throws IOException {
		File file = createTempFile();
		FileInputStream fis = new FileInputStream(file);

		File htmlDocument = new HtmlDocument(fis).asFile();

		assertThat(htmlDocument)
				.exists();
	}

	@Test
	public void properlyReturnsStringContent() throws IOException {
		String html = "<html><body>test</body></html>";
		File file = createTempFile();
		Files.write(file.toPath(), html.getBytes());

		String htmlDocument = new HtmlDocument(file).toString();

		assertThat(htmlDocument)
				.isEqualTo(html);
	}

	private File createTempFile() throws IOException {
		File file = File.createTempFile("pdfgen", ".html");
		file.deleteOnExit();
		return file;
	}
}
