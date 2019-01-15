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

import java.util.HashMap;
import java.util.Map;

public class WkhtmltopdfParameterBuilder {
	private DocumentBuilder parentBuilder;
	private Map<String, String> params;

	public static WkhtmltopdfParameterBuilder parameters() {
		return new WkhtmltopdfParameterBuilder(new HashMap<>());
	}

	WkhtmltopdfParameterBuilder(Map<String, String> params) {
		this.params = params;
	}

	WkhtmltopdfParameterBuilder(DocumentBuilder parentBuilder) {
		this.parentBuilder = parentBuilder;
		this.params = new HashMap<>();
	}

	public WkhtmltopdfParameterBuilder landscape() {
		params.put("--orientation", "Landscape");
		return this;
	}

	public WkhtmltopdfParameterBuilder portrait() {
		params.put("--orientation", "Portrait");
		return this;
	}

	public WkhtmltopdfParameterBuilder pageHeight(String height) {
		params.put("--page-height", height);
		return this;
	}

	public WkhtmltopdfParameterBuilder pageWidth(String width) {
		params.put("--page-width", width);
		return this;
	}

	public WkhtmltopdfParameterBuilder pageSize(String size) {
		params.put("--page-size", size);
		return this;
	}

	public WkhtmltopdfParameterBuilder a4() {
		params.put("--page-size", "A4");
		return this;
	}

	public WkhtmltopdfParameterBuilder zoom(double zoom) {
		params.put("--zoom", Double.toString(zoom));
		return this;
	}

	public WkhtmltopdfParameterBuilder lowQuality() {
		params.put("--lowquality", "");
		return this;
	}

	public WkhtmltopdfParameterBuilder marginLeft(String size) {
		params.put("--margin-left", size);
		return this;
	}

	public WkhtmltopdfParameterBuilder marginTop(String size) {
		params.put("--margin-top", size);
		return this;
	}

	public WkhtmltopdfParameterBuilder marginRight(String size) {
		params.put("--margin-right", size);
		return this;
	}

	public WkhtmltopdfParameterBuilder marginBottom(String size) {
		params.put("--margin-bottom", size);
		return this;
	}

	public WkhtmltopdfParameterBuilder minimumFontSize(int size) {
		params.put("--minimum-font-size", Integer.toString(size));
		return this;
	}

	public WkhtmltopdfParameterBuilder noBackground() {
		params.put("--no-background", "");
		return this;
	}

	public WkhtmltopdfParameterBuilder grayscale() {
		params.put("--grayscale", "");
		return this;
	}

	public WkhtmltopdfParameterBuilder disableSmartShrinking() {
		params.put("--disable-smart-shrinking", "");
		return this;
	}

	public WkhtmltopdfParameterBuilder enableSmartShrinking() {
		params.put("--enable-smart-shrinking", "");
		return this;
	}

	public WkhtmltopdfParameterBuilder footerHtml(String filename) {
		params.put("--footer-html", filename);
		return this;
	}

	public WkhtmltopdfParameterBuilder headerHtml(String filename) {
		params.put("--header-html", filename);
		return this;
	}

	public DocumentBuilder and() {
		return parentBuilder;
	}

	public Map<String, String> build() {
		return params;
	}
}
