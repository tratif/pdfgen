package com.tratif.pdfgen.document.renderers.pdf;

import com.tratif.pdfgen.document.docs.HtmlDocument;
import com.tratif.pdfgen.document.docs.PdfDocument;
import com.tratif.pdfgen.document.renderers.PdfRenderer;
import com.tratif.pdfgen.helpers.CommandLineExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FilePdfRenderer implements PdfRenderer {

	private static final Logger log = LoggerFactory.getLogger(FilePdfRenderer.class);

	@Override
	public PdfDocument render(HtmlDocument document, String destinationFilename) {
		try {
			CommandLineExecutor executor = new CommandLineExecutor();
			Process process = executor.command("wkhtmltopdf")
					.withArgument("--encoding utf-8")
					.withArguments(document.getRenderParams())
					.withArgument(document.getFilename())
					.withArgument(destinationFilename)
					.execute();

			logStream(process.getErrorStream());
			int exitCode = process.waitFor();

			if (exitCode != 0 && exitCode != 1) {
				throw new RuntimeException("wkhtmltopdf was terminated with exit code " + exitCode);
			}

			return new PdfDocument(destinationFilename);
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
