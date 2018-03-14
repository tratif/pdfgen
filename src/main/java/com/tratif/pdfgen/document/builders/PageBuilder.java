package com.tratif.pdfgen.document.builders;

import com.tratif.pdfgen.document.providers.ContentProvider;
import com.tratif.pdfgen.document.providers.StringContentProvider;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;

public class PageBuilder {

    private static TemplateEngine TEMPLATE_ENGINE;
    static {
        TEMPLATE_ENGINE = new TemplateEngine();
        TEMPLATE_ENGINE.setTemplateResolver(new StringTemplateResolver());
    }

    private DocumentBuilder documentBuilder;
    private ParameterBuilder params;

    private ContentProvider contentProvider;

    public PageBuilder(DocumentBuilder documentBuilder) {
        this.documentBuilder = documentBuilder;
        params = new ParameterBuilder(this);
    }

    public ParameterBuilder withParameters() {
        return new ParameterBuilder(this);
    }

    public DocumentBuilder and() {
        return documentBuilder;
    }

    public Map<String, String> getParams() {
        return params.build();
    }

    public InputStream getContent() {
        return contentProvider.getContent();
    }

    public byte[] toPdf() {
        return documentBuilder.toPdf();
    }

    public PageBuilder fromStaticHtml(String html) {
        contentProvider = new StringContentProvider(html);
        return this;
    }

    public PageBuilder fromHtmlTemplate(String htmlTemplate, Map<String, Object> params) {
        StringWriter sw = new StringWriter();
        Context context = new Context();
        context.setVariables(params);
        TEMPLATE_ENGINE.process(htmlTemplate, context, sw);

        contentProvider = new StringContentProvider(sw.toString());
        return this;
    }
}
