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

import com.tratif.pdfgen.document.docs.HtmlTemplate;
import com.tratif.pdfgen.document.renderers.HtmlRenderer;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Paths;

public class FreeMarkerHtmlRenderer implements HtmlRenderer {

	private Configuration configuration;

	// TODO: 6/22/18 implement own template loader to resolver both relative and absolute paths
	public FreeMarkerHtmlRenderer() {
		configuration = new Configuration(Configuration.VERSION_2_3_20);
		configuration.setDefaultEncoding("UTF-8");
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	}

	@Override
	public void render(HtmlTemplate page, Writer writer) {
		try {
			//todo make it configurable - freemarker is not able to read templates in custom locations in test scope
			//			configuration.setTemplateLoader(new FileTemplateLoader(new File("/opt/tascent/reports/templates/")));
			Template template = configuration.getTemplate(page.asFile().getPath());
			template.process(page.getParams(), writer);
		} catch (IOException | TemplateException e) {
			throw new RuntimeException("Error while rendering html.", e);
		}
	}
}
