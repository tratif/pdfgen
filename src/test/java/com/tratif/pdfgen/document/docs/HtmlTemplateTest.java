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