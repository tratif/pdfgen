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

import com.tratif.pdfgen.utils.FileNameGenerator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.util.StringJoiner;

import static java.util.Objects.isNull;

public class HtmlDocument {

	private static final Logger log = LoggerFactory.getLogger(HtmlDocument.class);

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
			file = FileNameGenerator.asFile("html");
			try {
				FileUtils.copyInputStreamToFile(inputStream, file);
			} catch (IOException e) {
				log.error("Failed to copy input stream to file.");
			}
		}

		return file;
	}

	//todo verify if that is needed maybe File is enough
	public String location() {
		return file.getAbsolutePath();
	}

	public InputStream asInputStream() {
		if (!isNull(inputStream)) {
			try {
				inputStream = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				log.error("Failed to open file.");
			}
		}

		return inputStream;
	}

	public String asString() {
		if (isNull(file)) {
			try {
				return IOUtils.toString(inputStream, "UTF-8");
			} catch (IOException e) {
				log.error("Failed to read input stream.");
				return null;
			}
		}

		StringJoiner sj = new StringJoiner("");

		try {
			Files.readAllLines(file.toPath()).forEach(sj::add);
		} catch (IOException e) {
			log.error("Failed to read file.");
			return null;
		}

		return sj.toString();
	}
}
