package com.tratif.pdfgen.document;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URL;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;

public class Document {

    public static DocumentBuilder fromStaticHtml(String html) {
        return new DocumentBuilder(html);
    }

    public static DocumentBuilder fromStaticHtml(InputStream inputStream) {
        return fromStaticHtml(ToStringParser.parse(inputStream));
    }

    public static DocumentBuilder fromStaticHtml(Reader reader) {
        return fromStaticHtml(ToStringParser.parse(reader));
    }

    public static DocumentBuilder fromStaticHtml(URL url) {
        return fromStaticHtml(ToStringParser.parse(url));
    }

    public static DocumentBuilder fromStaticHtml(File file) {
        return fromStaticHtml(ToStringParser.parse(file));
    }

    public static DocumentBuilder fromStaticHtml(Path path) {
        return fromStaticHtml(ToStringParser.parse(path));
    }

    public static DocumentBuilder fromHtmlTemplate(String html, Map<String, Object> args) {
        ITemplateResolver resolver = new StringTemplateResolver();
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        StringWriter sw = new StringWriter();
        Context context = new Context(Locale.forLanguageTag("PL"), args);
        engine.process(html, context, sw);

        return new DocumentBuilder(sw.toString());
    }

    public static DocumentBuilder fromHtmlTemplate(InputStream inputStream, Map<String, Object> args) {
        return fromHtmlTemplate(ToStringParser.parse(inputStream), args);
    }

    public static DocumentBuilder fromHtmlTemplate(Reader reader, Map<String, Object> args) {
        return fromHtmlTemplate(ToStringParser.parse(reader), args);
    }

    public static DocumentBuilder fromHtmlTemplate(URL url, Map<String, Object> args) {
        return fromHtmlTemplate(ToStringParser.parse(url), args);
    }

    public static DocumentBuilder fromHtmlTemplate(File file, Map<String, Object> args) {
        return fromHtmlTemplate(ToStringParser.parse(file), args);
    }

    public static DocumentBuilder fromHtmlTemplate(Path path, Map<String, Object> args) {
        return fromHtmlTemplate(ToStringParser.parse(path), args);
    }
}
