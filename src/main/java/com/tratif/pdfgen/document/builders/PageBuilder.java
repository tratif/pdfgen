package com.tratif.pdfgen.document.builders;

import com.tratif.pdfgen.document.parsers.ToInputStreamParser;
import com.tratif.pdfgen.document.parsers.ToStringParser;
import com.tratif.pdfgen.document.providers.ContentProvider;
import com.tratif.pdfgen.document.providers.InputStreamContentProvider;
import com.tratif.pdfgen.document.providers.StringContentProvider;
import com.tratif.pdfgen.document.renderers.HtmlRenderer;
import com.tratif.pdfgen.document.renderers.ThymeleafHtmlRenderer;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Path;
import java.util.Map;

public class PageBuilder {

    private static HtmlRenderer htmlRenderer = new ThymeleafHtmlRenderer();

    private DocumentBuilder parentBuilder;
    private ParameterBuilder params;

    private ContentProvider contentProvider;

    public PageBuilder(DocumentBuilder parentBuilder) {
        this.parentBuilder = parentBuilder;
        params = new ParameterBuilder(this);
    }

    public ParameterBuilder withParameters() {
        return new ParameterBuilder(this);
    }

    public DocumentBuilder and() {
        return parentBuilder;
    }

    public PageBuilder withPage() {
        return parentBuilder.withPage();
    }

    public Map<String, String> getParams() {
        return params.build();
    }

    public InputStream getContent() {
        return contentProvider.getContent();
    }

    public byte[] toPdf() {
        return parentBuilder.toPdf();
    }

    public PageBuilder fromStaticHtml(InputStream inputStream) {
        contentProvider = new InputStreamContentProvider(inputStream);
        return this;
    }

    public PageBuilder fromStaticHtml(String html) {
        contentProvider = new InputStreamContentProvider(ToInputStreamParser.parse(html));
        return this;
    }

    public PageBuilder fromStaticHtml(Reader reader) {
        contentProvider = new InputStreamContentProvider(ToInputStreamParser.parse(reader));
        return this;
    }

    public PageBuilder fromStaticHtml(URL url) {
        contentProvider = new InputStreamContentProvider(ToInputStreamParser.parse(url));
        return this;
    }

    public PageBuilder fromStaticHtml(File file) {
        contentProvider = new InputStreamContentProvider(ToInputStreamParser.parse(file));
        return this;
    }

    public PageBuilder fromStaticHtml(Path path) {
        contentProvider = new InputStreamContentProvider(ToInputStreamParser.parse(path));
        return this;
    }

    public PageBuilder fromHtmlTemplate(InputStream inputStream, Map<String, Object> params) {
        String htmlTemplate = ToStringParser.parse(inputStream);
        String html = htmlRenderer.render(htmlTemplate, params);
        contentProvider = new StringContentProvider(html);
        return this;
    }

    public PageBuilder fromHtmlTemplate(String htmlTemplate, Map<String, Object> params) {
        return fromHtmlTemplate(ToInputStreamParser.parse(htmlTemplate), params);
    }

    public PageBuilder fromHtmlTemplate(Reader reader, Map<String, Object> params) {
        return fromHtmlTemplate(ToInputStreamParser.parse(reader), params);
    }

    public PageBuilder fromHtmlTemplate(URL url, Map<String, Object> params) {
        return fromHtmlTemplate(ToInputStreamParser.parse(url), params);
    }

    public PageBuilder fromHtmlTemplate(File file, Map<String, Object> params) {
        return fromHtmlTemplate(ToInputStreamParser.parse(file), params);
    }

    public PageBuilder fromHtmlTemplate(Path path, Map<String, Object> params) {
        return fromHtmlTemplate(ToInputStreamParser.parse(path), params);
    }
}
