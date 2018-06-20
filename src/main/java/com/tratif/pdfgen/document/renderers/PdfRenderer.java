/**
 * Copyright 2018 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tratif.pdfgen.document.renderers;

import com.tratif.pdfgen.document.docs.HtmlDocument;
import com.tratif.pdfgen.document.docs.PdfDocument;

import java.util.List;

import static java.util.stream.Collectors.toList;

public interface PdfRenderer {

	PdfDocument render(HtmlDocument document, String destinationFilename);

	default List<PdfDocument> render(List<HtmlDocument> documents, String destinationFilename) {
		return documents.stream()
				.map(doc -> render(doc, destinationFilename))
				.collect(toList());
	}
}
