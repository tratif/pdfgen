package com.tratif.pdfgen.document.builders;

import com.tratif.pdfgen.document.renderers.HtmlRenderer;
import com.tratif.pdfgen.document.renderers.ThymeleafHtmlRenderer;

import java.util.Map;

public class PageBuilder {

    private static HtmlRenderer htmlRenderer = new ThymeleafHtmlRenderer();

    private DocumentBuilder parentBuilder;

    private String content;
    private ParameterBuilder params;

    public PageBuilder(DocumentBuilder parentBuilder) {
        this.parentBuilder = parentBuilder;
        params = new ParameterBuilder(this);
    }

    public PageBuilder fromStaticHtml(String html) {
        content = html;
        return this;
    }

    public PageBuilder fromHtmlTemplate(String htmlTemplate, Map<String, Object> params) {
        content = htmlRenderer.render(htmlTemplate, params);
        return this;
    }

    public ParameterBuilder withParameters() {
        return new ParameterBuilder(this);
    }

    public DocumentBuilder and() {
        return parentBuilder;
    }

    public ParameterBuilder getParams() {
        return params;
    }

    public String build() {
        return content;
    }

    public byte[] toPdf() {
        return parentBuilder.toPdf();
    }
}
