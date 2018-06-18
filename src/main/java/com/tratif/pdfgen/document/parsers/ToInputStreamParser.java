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
package com.tratif.pdfgen.document.parsers;

import org.apache.commons.io.input.ReaderInputStream;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class ToInputStreamParser {

	public static InputStream parse(String string) {
		return new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
	}

	public static InputStream parse(Reader reader) {
		return new ReaderInputStream(reader, StandardCharsets.UTF_8);
	}

	public static InputStream parse(URL url) {
		try {
			return url.openStream();
		} catch (IOException e) {
			throw new RuntimeException("Failed opening url stream", e);
		}
	}

	public static InputStream parse(File file) {
		try {
			return new BufferedInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found", e);
		}
	}

	public static InputStream parse(Path path) {
		return parse(path.toFile());
	}
}
