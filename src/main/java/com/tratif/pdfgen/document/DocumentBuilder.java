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

import com.tratif.pdfgen.helpers.CommandLineExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class DocumentBuilder {

    private final static Logger log = LoggerFactory.getLogger(DocumentBuilder.class);
    private final String TEMP_FILE_PREFIX = "pdfgen";

    private String html;
    private Map<String, String> params;

    DocumentBuilder(String html) {
        this.html = html;
        params = new HashMap<>();
    }

    public ParameterBuilder properties() {
        return new ParameterBuilder(params);
    }

    public byte[] toPdf() {
        try {
            File html = File.createTempFile(TEMP_FILE_PREFIX, ".html");
            File pdf = File.createTempFile(TEMP_FILE_PREFIX, ".pdf");
            Files.write(html.toPath(), this.html.getBytes());

            CommandLineExecutor executor = new CommandLineExecutor();
            executor.command("wkhtmltopdf")
                    .withArgument("--encoding utf-8")
                    .withArgument(html.toPath().toString())
                    .withArgument(pdf.toPath().toString())
                    .withArguments(params)
                    .execute()
                    .waitFor();

            byte[] bytes = Files.readAllBytes(pdf.toPath());

            if (!html.delete())
                log.warn("{} was not deleted.", html.getPath());

            if (!pdf.delete())
                log.warn("{} was not deleted.", pdf.getPath());

            return bytes;

        } catch (InterruptedException e) {
            log.error("There was problem executing command.");
            throw new RuntimeException("There was problem executing command.", e);
        } catch (IOException e) {
            log.error("There was a problem with the file.");
            throw new RuntimeException("There was a problem with the file.", e);
        }
    }

    public String toHtml() {
        return html;
    }
}
