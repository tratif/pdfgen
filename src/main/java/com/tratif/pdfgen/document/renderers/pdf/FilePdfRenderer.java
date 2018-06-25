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
import com.tratif.pdfgen.helpers.CommandLineExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

public class FilePdfRenderer implements PdfRenderer {

	private static final Logger log = LoggerFactory.getLogger(FilePdfRenderer.class);

	@Override
	public PdfDocument render(HtmlDocument document, String destinationFilename, Map<String, String> renderParams) {
		try {
			CommandLineExecutor executor = new CommandLineExecutor();
			Process process = executor.command("wkhtmltopdf")
					.withArgument("--encoding utf-8")
					.withArguments(renderParams)
					.withArgument(document.asFile().getPath())
					.withArgument(destinationFilename)
					.execute();

			logStream(process.getErrorStream());
			int exitCode = process.waitFor();

			if (exitCode != 0 && exitCode != 1) {
				throw new RuntimeException("wkhtmltopdf was terminated with exit code " + exitCode);
			}

			return new PdfDocument(new File(destinationFilename));
		} catch (InterruptedException e) {
			log.error("InterruptedException: There was problem executing command.");
			throw new RuntimeException("There was problem executing command.", e);
		} catch (IOException e) {
			log.error("IOException: There was a problem with the file.");
			throw new RuntimeException("There was a problem with the file.", e);
		}
	}

	private void logStream(InputStream stream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		String line;
		while ((line = reader.readLine()) != null)
			log.debug(line);
	}
}
