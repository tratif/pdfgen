/**
 * Copyright 2018 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tratif.pdfgen.document.docs;

import com.tratif.pdfgen.exceptions.PdfgenException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;

import static java.util.Objects.isNull;

public class HtmlDocument {

	private File file;
	private InputStream inputStream;

	public HtmlDocument(File file) {
		this.file = file;
	}

	public HtmlDocument(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public File asFile() {
		if (isNull(file)) {
			try {
				file = Files.createTempFile("pdfgen", ".html").toFile();
				FileUtils.copyInputStreamToFile(inputStream, file);
			} catch (IOException e) {
				throw new PdfgenException("Failed creating file from input stream.", e);
			}
		}

		return file;
	}

	public InputStream asInputStream() {
		if (isNull(inputStream)) {
			try {
				inputStream = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				throw new PdfgenException("Failed to open file.", e);
			}
		}

		return inputStream;
	}

	public String asString() {
		if (isNull(file)) {
			try {
				return IOUtils.toString(inputStream, "UTF-8");
			} catch (IOException e) {
				throw new PdfgenException("Failed to read input stream.", e);
			}
		}

		try {
			return new String(Files.readAllBytes(file.toPath()), "UTF-8");
		} catch (IOException e) {
			throw new PdfgenException("Failed to read file.", e);
		}
	}
}
