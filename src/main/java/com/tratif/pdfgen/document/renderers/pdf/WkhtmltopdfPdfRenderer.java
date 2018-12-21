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
package com.tratif.pdfgen.document.renderers.pdf;

import com.tratif.pdfgen.document.docs.HtmlDocument;
import com.tratif.pdfgen.document.docs.PdfDocument;
import com.tratif.pdfgen.document.renderers.PdfRenderer;
import com.tratif.pdfgen.exceptions.PdfgenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

public class WkhtmltopdfPdfRenderer implements PdfRenderer {

	private static final Logger log = LoggerFactory.getLogger(WkhtmltopdfPdfRenderer.class);

	@Override
	public PdfDocument render(HtmlDocument document, File destination, Map<String, String> renderParams) {
		return render(singletonList(document), destination, renderParams);
	}

	@Override
	public PdfDocument render(List<HtmlDocument> documents, File destination, Map<String, String> renderParams) {
		try {
			CommandLineExecutor executor = new CommandLineExecutor();
			Process process = executor.command("wkhtmltopdf")
					.withArgument("--encoding utf-8")
					.withArguments(renderParams)
					.withArguments(documents.stream().map(doc -> doc.asFile().getPath()).collect(toList()))
					.withArgument(destination.getPath())
					.execute();

			logStream(log, process.getErrorStream());
			int exitCode = process.waitFor();

			if (exitCode != 0) {
				throw new PdfgenException("Generating pdf failed due to unknown error.");
			}

			return new PdfDocument(destination);
		} catch (InterruptedException e) {
			throw new PdfgenException("There was problem executing command.", e);
		}
	}

}
