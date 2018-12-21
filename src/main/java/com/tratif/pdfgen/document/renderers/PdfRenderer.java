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
package com.tratif.pdfgen.document.renderers;

import com.tratif.pdfgen.document.docs.HtmlDocument;
import com.tratif.pdfgen.document.docs.PdfDocument;
import org.slf4j.Logger;

import java.io.*;
import java.util.List;
import java.util.Map;

public interface PdfRenderer {

	PdfDocument render(HtmlDocument document, File destination, Map<String, String> renderParams);

	PdfDocument render(List<HtmlDocument> documents, File destination, Map<String, String> renderParams);

	default void logStream(Logger log, InputStream stream) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
			String line;
			while ((line = reader.readLine()) != null) {
				log.debug(line);
			}
		} catch (IOException e) {
			log.warn("There was a problem reading stream.", e);
		}
	}
}
