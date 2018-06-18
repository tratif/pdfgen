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
package com.tratif.pdfgen.document.renderers.html;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.io.StringWriter;
import java.util.Map;

public class ThymeleafHtmlRenderer implements HtmlRenderer {
	private TemplateEngine templateEngine;

	public ThymeleafHtmlRenderer() {
		templateEngine = new TemplateEngine();
		templateEngine.addDialect(new Java8TimeDialect());
		templateEngine.setTemplateResolver(new StringTemplateResolver());
	}

	@Override
	public String render(String htmlTemplate, Map<String, Object> params) {
		StringWriter sw = new StringWriter();
		Context context = new Context();
		context.setVariables(params);
		templateEngine.process(htmlTemplate, context, sw);

		return sw.toString();
	}
}
