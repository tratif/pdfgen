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
package com.tratif.pdfgen.document;

import com.tratif.pdfgen.document.builders.DocumentBuilder;
import com.tratif.pdfgen.document.builders.PageBuilder;
import com.tratif.pdfgen.document.docs.RenderableDocument;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Path;
import java.util.Map;

public class Document {

	public static PageBuilder fromStaticHtml(String html) {
		return new DocumentBuilder()
				.withPage()
				.fromStaticHtml(html);
	}

	public static PageBuilder fromStaticHtml(InputStream inputStream) {
		return new DocumentBuilder()
				.withPage()
				.fromStaticHtml(inputStream);
	}

	public static PageBuilder fromStaticHtml(Reader reader) {
		return new DocumentBuilder()
				.withPage()
				.fromStaticHtml(reader);
	}

	public static PageBuilder fromStaticHtml(URL url) {
		return new DocumentBuilder()
				.withPage()
				.fromStaticHtml(url);
	}

	public static PageBuilder fromStaticHtml(File file) {
		return new DocumentBuilder()
				.withPage()
				.fromStaticHtml(file);
	}

	public static PageBuilder fromStaticHtml(Path path) {
		return new DocumentBuilder()
				.withPage()
				.fromStaticHtml(path);
	}

	public static PageBuilder fromHtmlTemplate(String htmlTemplate, Map<String, Object> params) {
		return new DocumentBuilder()
				.withPage()
				.fromHtmlTemplate(htmlTemplate, params);
	}

	public static PageBuilder fromHtmlTemplate(InputStream inputStream, Map<String, Object> params) {
		return new DocumentBuilder()
				.withPage()
				.fromHtmlTemplate(inputStream, params);
	}

	public static PageBuilder fromHtmlTemplate(Reader reader, Map<String, Object> params) {
		return new DocumentBuilder()
				.withPage()
				.fromHtmlTemplate(reader, params);
	}

	public static PageBuilder fromHtmlTemplate(URL url, Map<String, Object> params) {
		return new DocumentBuilder()
				.withPage()
				.fromHtmlTemplate(url, params);
	}

	public static PageBuilder fromHtmlTemplate(File file, Map<String, Object> params) {
		return new DocumentBuilder()
				.withPage()
				.fromHtmlTemplate(file, params);
	}

	public static PageBuilder fromHtmlTemplate(Path path, Map<String, Object> params) {
		return new DocumentBuilder()
				.withPage()
				.fromHtmlTemplate(path, params);
	}

	public static PageBuilder withPage() {
		return new DocumentBuilder().withPage();
	}
}
