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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class Page {
    private File contentFile;
    private Map<String, String> params;

    public Page() {
        this("");
    }

    public Page(String contentFile) {
        params = new HashMap<>();
        saveContentToTempFile(contentFile);
    }

    public Page(String htmlTemplate, Map<String, Object> bindMap) {
        this(Document.fromHtmlTemplate(htmlTemplate, bindMap).toHtml());
    }

    private void saveContentToTempFile(String content) {
        try {
            this.contentFile = File.createTempFile(Document.TEMP_FILE_PREFIX, ".pdf");
            Files.write(this.contentFile.toPath(), content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("There was a problem saving file.", e);
        }
    }

    public void render() {
        try {
            String content = new String(Files.readAllBytes(contentFile.toPath()));
            Files.write(
                    contentFile.toPath(),
                    Document.fromStaticHtml(content)
                            .setParameters(params)
                            .toPdf()
            );
        } catch (IOException e) {
            throw new RuntimeException("Critical error: temp file was not found.", e);
        }
    }

    public ParameterBuilder parameters() {
        return new ParameterBuilder(params);
    }

    public File getContentFile() {
        return contentFile;
    }
}
