package com.tratif.pdfgen.document.providers;

import com.tratif.pdfgen.document.renderers.EngineNotSupportedException;
import com.tratif.pdfgen.document.renderers.HtmlRenderer;
import com.tratif.pdfgen.document.renderers.html.*;

import java.util.HashMap;
import java.util.Map;

public class HtmlTemplateEngineProvider {

	private static final Map<HtmlTemplateEngine, HtmlRenderer> RENDERER_REGISTRY;

	static {
		RENDERER_REGISTRY = new HashMap<>();
//		RENDERER_REGISTRY.put(HtmlTemplateEngine.THYMELEAF, new ThymeleafHtmlRenderer());
		RENDERER_REGISTRY.put(HtmlTemplateEngine.FREEMARKER, new FreeMarkerHtmlRenderer());
	}

	public static HtmlRenderer get(HtmlTemplateEngine templateEngine) {
		if (!RENDERER_REGISTRY.containsKey(templateEngine)) {
			throw new EngineNotSupportedException();
		}

		return RENDERER_REGISTRY.get(templateEngine);
	}
}
