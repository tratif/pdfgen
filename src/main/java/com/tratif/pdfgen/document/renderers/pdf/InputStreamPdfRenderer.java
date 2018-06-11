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
package com.tratif.pdfgen.document.renderers.pdf;

import com.tratif.pdfgen.document.Document;
import com.tratif.pdfgen.document.PDF;
import com.tratif.pdfgen.document.builders.PageBuilder;
import com.tratif.pdfgen.helpers.CommandLineExecutor;
import com.tratif.pdfgen.helpers.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class InputStreamPdfRenderer implements PdfRenderer {

	private static final Logger log = LoggerFactory.getLogger(InputStreamPdfRenderer.class);

	public PDF render(PageBuilder page) {
		try {
			File html = File.createTempFile(Document.TEMP_FILE_PREFIX, ".html");
			File pdf = File.createTempFile(Document.TEMP_FILE_PREFIX, ".pdf");

			Utils.writeStreamToFile(html, page.getContent());

			CommandLineExecutor executor = new CommandLineExecutor();
			Process process = executor.command("wkhtmltopdf")
					.withArgument("--encoding utf-8")
					.withArguments(page.getParams())
					.withArgument(html.toPath().toString())
					.withArgument(pdf.toPath().toString())
					.execute();

			logStream(process.getErrorStream());
			int exitCode = process.waitFor();

			if (exitCode != 0) {
				throw new RuntimeException("wkhtmltopdf was terminated with exit code " + exitCode);
			}

			if (!html.delete()) {
				log.warn("{} was not deleted.", html.getPath());
			}

			pdf.deleteOnExit();

			return new PDF(new BufferedInputStream(new FileInputStream(pdf)));

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
