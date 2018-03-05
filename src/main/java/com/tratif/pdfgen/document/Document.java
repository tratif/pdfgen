/**
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tratif.pdfgen.document;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URL;
import java.nio.file.Path;
import java.util.Map;

public class Document {

    private static TemplateEngine TEMPLATE_ENGINE;

    static {
        TEMPLATE_ENGINE = new TemplateEngine();
        TEMPLATE_ENGINE.setTemplateResolver(new StringTemplateResolver());
    }


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
        StringWriter sw = new StringWriter();
        Context context = new Context();
        context.setVariables(args);
        TEMPLATE_ENGINE.process(html, context, sw);

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
