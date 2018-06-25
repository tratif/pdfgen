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

	public File asFile() {
		return file;
	}

	public byte[] toByteArray() {
		try {
			return Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			throw new RuntimeException("Unable to read file.");
		}
	}
}
