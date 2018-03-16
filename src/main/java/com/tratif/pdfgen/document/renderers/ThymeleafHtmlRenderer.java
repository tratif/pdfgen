package com.tratif.pdfgen.document.renderers;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.io.StringWriter;
import java.util.Map;

public class ThymeleafHtmlRenderer implements HtmlRenderer {
    private TemplateEngine templateEngine;

    public ThymeleafHtmlRenderer() {
        templateEngine = new TemplateEngine();
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
