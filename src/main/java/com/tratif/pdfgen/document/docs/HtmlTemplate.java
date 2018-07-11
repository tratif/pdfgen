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

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.Map;

import static java.util.Objects.isNull;

public class HtmlTemplate {

	private File file;
	private InputStream inputStream;
	private Map<String, Object> params;

	public HtmlTemplate(File file, Map<String, Object> params) {
		this.file = file;
		this.params = params;
	}

	public HtmlTemplate(InputStream inputStream, Map<String, Object> params) {
		this.inputStream = inputStream;
		this.params = params;
	}

	public File asFile() throws IOException {
		if (isNull(file)) {
			file = Files.createTempFile("pdfgen", ".pdf").toFile();
			FileUtils.copyInputStreamToFile(inputStream, file);
		}

		return file;
	}

	public InputStream asInputStream() throws FileNotFoundException {
		if (isNull(inputStream)) {
			inputStream = new FileInputStream(file);
		}

		return inputStream;
	}

	public Map<String, Object> getParams() {
		return params;
	}
}
