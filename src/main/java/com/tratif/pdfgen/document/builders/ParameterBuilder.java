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

public class ParameterBuilder {
	private DocumentBuilder parentBuilder;
	private Map<String, String> params;

	ParameterBuilder(DocumentBuilder parentBuilder) {
		this.parentBuilder = parentBuilder;
		this.params = new HashMap<>();
	}

	public ParameterBuilder landscape() {
		params.put("--orientation", "Landscape");
		return this;
	}

	public ParameterBuilder portrait() {
		params.put("--orientation", "Portrait");
		return this;
	}

	public ParameterBuilder pageHeight(String height) {
		params.put("--page-height", height);
		return this;
	}

	public ParameterBuilder pageWidth(String width) {
		params.put("--page-width", width);
		return this;
	}

	public ParameterBuilder pageSize(String size) {
		params.put("--page-size", size);
		return this;
	}

	public ParameterBuilder a4() {
		params.put("--page-size", "A4");
		return this;
	}

	public ParameterBuilder zoom(double zoom) {
		params.put("--zoom", Double.toString(zoom));
		return this;
	}

	public ParameterBuilder lowQuality() {
		params.put("--lowquality", "");
		return this;
	}

	public ParameterBuilder marginLeft(String size) {
		params.put("--margin-left", size);
		return this;
	}

	public ParameterBuilder marginTop(String size) {
		params.put("--margin-top", size);
		return this;
	}

	public ParameterBuilder marginRight(String size) {
		params.put("--margin-right", size);
		return this;
	}

	public ParameterBuilder marginBottom(String size) {
		params.put("--margin-bottom", size);
		return this;
	}

	public ParameterBuilder minimumFontSize(int size) {
		params.put("--minimum-font-size", Integer.toString(size));
		return this;
	}

	public ParameterBuilder noBackground() {
		params.put("--no-background", "");
		return this;
	}

	public ParameterBuilder grayscale() {
		params.put("--grayscale", "");
		return this;
	}

	public ParameterBuilder disableSmartShrinking() {
		params.put("--disable-smart-shrinking", "");
		return this;
	}

	public ParameterBuilder enableSmartShrinking() {
		params.put("--enable-smart-shrinking", "");
		return this;
	}

	public ParameterBuilder footerHtml(String filename) {
		params.put("--footer-html", filename);
		return this;
	}

	public ParameterBuilder headerHtml(String filename) {
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
