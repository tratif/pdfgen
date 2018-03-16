package com.tratif.pdfgen.document.builders;

import com.tratif.pdfgen.document.parsers.ToInputStreamParser;
import com.tratif.pdfgen.document.providers.ContentProvider;
import com.tratif.pdfgen.document.providers.InputStreamContentProvider;
import com.tratif.pdfgen.document.providers.StringContentProvider;
import org.apache.commons.io.IOUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
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

    public PageBuilder withPage() {
        return documentBuilder.withPage();
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
        StringWriter sw = new StringWriter();
        Context context = new Context();
        context.setVariables(params);
        String htmlTemplate;
        try {
            htmlTemplate = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed reading input stream", e);
        }
        TEMPLATE_ENGINE.process(htmlTemplate, context, sw);

        contentProvider = new StringContentProvider(sw.toString());
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
