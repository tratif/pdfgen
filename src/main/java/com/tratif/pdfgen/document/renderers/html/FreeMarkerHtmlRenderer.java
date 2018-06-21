package com.tratif.pdfgen.document.renderers.html;

import com.tratif.pdfgen.document.docs.HtmlTemplate;
import com.tratif.pdfgen.document.renderers.HtmlRenderer;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.IOException;
import java.io.Writer;

public class FreeMarkerHtmlRenderer implements HtmlRenderer {

	private Configuration configuration;

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
