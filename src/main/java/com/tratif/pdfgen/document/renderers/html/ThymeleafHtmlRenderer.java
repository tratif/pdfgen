/**
 * Copyright 2018 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tratif.pdfgen.document.renderers.html;

//public class ThymeleafHtmlRenderer implements HtmlRenderer {
//	private TemplateEngine templateEngine;
//
//	public ThymeleafHtmlRenderer() {
//		templateEngine = new TemplateEngine();
//		templateEngine.addDialect(new Java8TimeDialect());
//		templateEngine.setTemplateResolver(new StringTemplateResolver());
//	}

//	@Override
//	public String render(String htmlTemplate, Map<String, Object> params) {
//		templateEngine.setTemplateResolver(new StringTemplateResolver());
//
//		StringWriter sw = new StringWriter();
//		Context context = new Context();
//		context.setVariables(params);
//		templateEngine.process(htmlTemplate, context, sw);
//
//		return sw.toString();
//	}
//
//	@Override
//	public void render(String templateName, Map<String, Object> params, FileWriter fileWriter) {
//		templateEngine.setTemplateResolver(new FileTemplateResolver());
//
//		Context context = new Context();
//		context.setVariables(params);
//		templateEngine.process(templateName, context, fileWriter);
//	}

//	@Override
//	public RenderedDocument render(List<RenderableDocument> pages, FileWriter fileWriter) {
////		templateEngine.setTemplateResolver(new FileTemplateResolver());
////
////		pages.stream()
////			.map(page -> {
////				if (page instanceof TemplateDocument) {
////					TemplateDocument doc = (TemplateDocument) page;
//////					render(doc.getContentProvider().getContent(), doc.getParams(), fileWriter);
////				}
////			})
//
//		return null;
//	}
//}
