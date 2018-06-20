package com.tratif.pdfgen.document.renderers.html;

import com.tratif.pdfgen.document.docs.HtmlTemplate;
import com.tratif.pdfgen.document.renderers.HtmlRenderer;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;

public class FreeMarkerHtmlRenderer implements HtmlRenderer {

	private static final Logger log = LoggerFactory.getLogger(FreeMarkerHtmlRenderer.class);

	private Configuration configuration;

	public FreeMarkerHtmlRenderer() {
		configuration = new Configuration(Configuration.VERSION_2_3_20);
		configuration.setDefaultEncoding("UTF-8");
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	}

	@Override
	public void render(HtmlTemplate page, Writer writer) {
		try {
			Template template = configuration.getTemplate(page.getFilename());
			template.process(page.getParams(), writer);
		} catch (IOException | TemplateException e) {
			throw new RuntimeException("Error while rendering html.");
		}
	}

//	@Override
//	public String render(String htmlTemplate, Map<String, Object> params) {
//		throw new RuntimeException();
//	}
//
//	@Override
//	public void render(String templateName, Map<String, Object> params, FileWriter fileWriter) {
//		try {
//			Template template = configuration.getTemplate(templateName);
//			template.process(params, fileWriter);
//		} catch (IOException | TemplateException e) {
//			log.error("[FreeMarker] Could not process template.");
//		}
//	}
}
