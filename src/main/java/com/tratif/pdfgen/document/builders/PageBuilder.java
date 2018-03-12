package com.tratif.pdfgen.document.builders;

import com.tratif.pdfgen.document.Page;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.io.StringWriter;
import java.util.Map;

public class PageBuilder {

    private static TemplateEngine TEMPLATE_ENGINE;
    static {
        TEMPLATE_ENGINE = new TemplateEngine();
        TEMPLATE_ENGINE.setTemplateResolver(new StringTemplateResolver());
    }

    private DocumentBuilder documentBuilder;
    private Page page;

    public PageBuilder(DocumentBuilder documentBuilder, Page page) {
        this.documentBuilder = documentBuilder;
        this.page = page;
    }

    public PageBuilder fromStaticHtml(String html) {
        page.setContent(html);
        return this;
    }

    public PageBuilder fromHtmlTemplate(String htmlTemplate, Map<String, Object> params) {
        StringWriter sw = new StringWriter();
        Context context = new Context();
        context.setVariables(params);
        TEMPLATE_ENGINE.process(htmlTemplate, context, sw);

        page.setContent(sw.toString());
        return this;
    }

    public ParameterBuilder withParameters() {
        return new ParameterBuilder(this, page.getParams());
    }

    public DocumentBuilder and() {
        return documentBuilder;
    }
}
