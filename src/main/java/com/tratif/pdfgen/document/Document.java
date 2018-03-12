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

import com.tratif.pdfgen.document.builders.DocumentBuilder;
import com.tratif.pdfgen.document.builders.ToStringParser;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Path;
import java.util.Map;

public class Document {

    public final static String TEMP_FILE_PREFIX = "pdfgen";

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

    public static DocumentBuilder fromHtmlTemplate(String htmlTemplate, Map<String, Object> params) {
        return new DocumentBuilder()
                .withPage()
                    .fromHtmlTemplate(htmlTemplate, params)
                    .and();
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

    public static DocumentBuilder withPage() {
        return new DocumentBuilder();
    }
}
