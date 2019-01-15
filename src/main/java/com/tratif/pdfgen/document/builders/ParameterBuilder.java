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
package com.tratif.pdfgen.document.builders;

import com.tratif.pdfgen.exceptions.PdfgenException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ParameterBuilder {
	private DocumentBuilder parentBuilder;
	private Map<String, String> params;

	public static ParameterBuilder parameters() {
		return new ParameterBuilder(new HashMap<>());
	}

	ParameterBuilder(Map<String, String> params) {
		this.params = params;
	}

	ParameterBuilder(DocumentBuilder parentBuilder) {
		this.parentBuilder = parentBuilder;
		this.params = new HashMap<>();
	}

	public ParameterBuilder landscape() {
		params.put("--landscape", "true");
		return this;
	}

	public ParameterBuilder portrait() {
		params.put("--landscape", "false");
		return this;
	}

	public ParameterBuilder pageHeight(String height) {
		params.put("--height", height);
		return this;
	}

	public ParameterBuilder pageWidth(String width) {
		params.put("--width", width);
		return this;
	}

	public ParameterBuilder pageFormat(String format) {
		params.put("--pageFormat", format);
		return this;
	}

	public ParameterBuilder a4() {
		params.put("--pageFormat", "A4");
		return this;
	}

	public ParameterBuilder scale(double scale) {
		params.put("--scale", Double.toString(scale));
		return this;
	}

	public ParameterBuilder marginLeft(String size) {
		params.put("--marginLeft", size);
		return this;
	}

	public ParameterBuilder marginTop(String size) {
		params.put("--marginTop", size);
		return this;
	}

	public ParameterBuilder marginRight(String size) {
		params.put("--marginRight", size);
		return this;
	}

	public ParameterBuilder marginBottom(String size) {
		params.put("--marginBottom", size);
		return this;
	}

	/**
	 * this method enables printing with background defined in html file.
	 */
	public ParameterBuilder withBackground() {
		params.put("--printBackground", "true");
		return this;
	}

	public ParameterBuilder displayOnlyHeader() {
		params.put("--displayHeaderFooter", "true");
		emptyFooter();
		return this;
	}

	public ParameterBuilder displayOnlyFooter() {
		params.put("--displayHeaderFooter", "true");
		emptyHeader();
		return this;
	}

	public ParameterBuilder displayHeaderAndFooter() {
		params.put("--displayHeaderFooter", "true");
		return this;
	}

	public ParameterBuilder footerHtml(String path) {
		try {
			params.put("--footerTemplate", readFromFile(path));
		} catch (IOException e) {
			throw new PdfgenException("Error while loading footer", e);
		}
		return this;
	}

	public ParameterBuilder headerHtml(String path) {
		try {
			params.put("--headerTemplate", readFromFile(path));
		} catch (IOException e) {
			throw new PdfgenException("Error while loading header", e);
		}
		return this;
	}

	public ParameterBuilder emptyHeader() {
		params.put("--headerTemplate", "<p></p>");
		return this;
	}

	public ParameterBuilder emptyFooter() {
		params.put("--footerTemplate", "<p></p>");
		return this;
	}

	public DocumentBuilder and() {
		return parentBuilder;
	}

	public Map<String, String> build() {
		return params;
	}

	private String readFromFile(String path) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, Charset.forName("UTF-8"));
	}
}
