package com.tratif.pdfgen.document.renderers.pdf;

import com.tratif.pdfgen.document.docs.HtmlDocument;
import com.tratif.pdfgen.document.docs.PdfDocument;
import com.tratif.pdfgen.document.mergers.pdf.InputStreamPdfMerger;
import com.tratif.pdfgen.document.mergers.pdf.PdfMerger;
import com.tratif.pdfgen.document.renderers.PdfRenderer;
import com.tratif.pdfgen.exceptions.PdfgenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Chromehtml2pdfRenderer implements PdfRenderer {

	private static final Logger log = LoggerFactory.getLogger(Chromehtml2pdfRenderer.class);

	@Override
	public PdfDocument render(HtmlDocument document, File destination, Map<String, String> renderParams) {
		try {
			CommandLineExecutor executor = new CommandLineExecutor();
			Process process = executor.command("chromehtml2pdf")
					.withArgument("--out=" + destination.getPath())
					.withArgument("file://" + document.asFile().getPath())
					.withArguments(renderParams)
					.execute();

			logStream(log, process.getErrorStream());
			int exitCode = process.waitFor();
			if (exitCode != 0) {
				throw new PdfgenException("Generating pdf failed due to unknown error. (exit code: " + exitCode + ")");
			}
			return new PdfDocument(destination);

		} catch (InterruptedException e) {
			throw new PdfgenException("There was problem executing command.", e);
		}
	}

	@Override
	public PdfDocument render(List<HtmlDocument> documents, File destination, Map<String, String> renderParams) {
		PdfMerger pdfMerger = new InputStreamPdfMerger();

		List<PdfDocument> pdfDocuments = documents.stream().map(htmlDocument -> {
			try {
				return render(htmlDocument, Files.createTempFile("pdfgen-", ".pdf").toFile(), renderParams);
			} catch (IOException e) {
				throw new PdfgenException("Generating pdf file failed during rendering", e);
			}
		}).collect(Collectors.toList());

		File merged = pdfMerger.merge(pdfDocuments).asFile();
		boolean fileMoved = merged.renameTo(destination);

		if (!fileMoved) {
			throw new PdfgenException("Error during creating output file.");
		}
		return new PdfDocument(destination);
	}
}
