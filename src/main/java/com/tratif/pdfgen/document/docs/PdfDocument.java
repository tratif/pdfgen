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

import com.tratif.pdfgen.exceptions.PdfgenException;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class PdfDocument {

	private File file;

	public PdfDocument(File file) {
		this.file = file;
	}

	public File asFile() {
		return file;
	}

	public InputStream asInputStream() {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new PdfgenException("Unable to read file.", e);
		}
	}

	public byte[] toByteArray() {
		try {
			return Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			throw new PdfgenException("Unable to read file.", e);
		}
	}
}