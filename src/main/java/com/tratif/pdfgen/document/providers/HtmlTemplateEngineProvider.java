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
package com.tratif.pdfgen.document.providers;

import com.tratif.pdfgen.document.renderers.HtmlRenderer;
import com.tratif.pdfgen.document.renderers.html.FreeMarkerHtmlRenderer;
import com.tratif.pdfgen.document.renderers.html.HtmlTemplateEngine;
import com.tratif.pdfgen.document.renderers.html.ThymeleafHtmlRenderer;
import com.tratif.pdfgen.exceptions.PdfgenException;

import java.util.HashMap;
import java.util.Map;

public class HtmlTemplateEngineProvider {

	private static final Map<HtmlTemplateEngine, HtmlRenderer> RENDERER_REGISTRY;

	static {
		RENDERER_REGISTRY = new HashMap<>();
		RENDERER_REGISTRY.put(HtmlTemplateEngine.THYMELEAF, new ThymeleafHtmlRenderer());
		RENDERER_REGISTRY.put(HtmlTemplateEngine.FREEMARKER, new FreeMarkerHtmlRenderer());
	}

	public static HtmlRenderer get(HtmlTemplateEngine templateEngine) {
		if (!RENDERER_REGISTRY.containsKey(templateEngine)) {
			throw new PdfgenException(templateEngine.toString() + " template engine is not supported.");
		}

		return RENDERER_REGISTRY.get(templateEngine);
	}
}
