package com.tratif.pdfgen.document.builders;

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

    private String content;
    private ParameterBuilder params;

    public PageBuilder(DocumentBuilder documentBuilder) {
        this.documentBuilder = documentBuilder;
        params = new ParameterBuilder(this);
    }

    public PageBuilder fromStaticHtml(String html) {
        content = html;
        return this;
    }

    public PageBuilder fromHtmlTemplate(String htmlTemplate, Map<String, Object> params) {
        StringWriter sw = new StringWriter();
        Context context = new Context();
        context.setVariables(params);
        TEMPLATE_ENGINE.process(htmlTemplate, context, sw);

        content = sw.toString();
        return this;
    }

    public ParameterBuilder withParameters() {
        return new ParameterBuilder(this);
    }

    public DocumentBuilder and() {
        return documentBuilder;
    }

    public ParameterBuilder getParams() {
        return params;
    }

    public String build() {
        return content;
    }

    public byte[] toPdf() {
        return documentBuilder.toPdf();
    }
}
