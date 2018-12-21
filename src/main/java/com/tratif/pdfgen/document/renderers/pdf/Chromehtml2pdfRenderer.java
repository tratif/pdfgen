package com.tratif.pdfgen.document.renderers.pdf;

import com.google.common.io.CharStreams;
import com.tratif.pdfgen.document.docs.HtmlDocument;
import com.tratif.pdfgen.document.docs.PdfDocument;
import com.tratif.pdfgen.document.renderers.PdfRenderer;
import com.tratif.pdfgen.exceptions.PdfgenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chromehtml2pdfRenderer implements PdfRenderer {

	private static final String HEADER = "<html>"
			+ "<head>"
			+ "    <style>"
			+ "        body {"
			+ "            width: 100%;"
			+ "        }"
			+ "        .grid-container {"
			+ "            display: grid;"
			+ "            grid-template-columns: repeat(2, 297px);"
			+ "            row-gap: 0;"
			+ "            font-size: 12px;"
			+ "        }"
			+ "        .grid-area {"
			+ "            grid-template-areas:"
			+ "                    \"report-title       confidentiality-info\""
			+ "                    \"generation-date    generation-date\";"
			+ "        }"
			+ "        .report-title {"
			+ "            grid-area: report-title;"
			+ "            padding-left: 10px;"
			+ "        }"
			+ ""
			+ "        .generation-date {"
			+ "            grid-area: generation-date;"
			+ "            padding-left: 10px;"
			+ "            border-top: 2px solid black;"
			+ "        }"
			+ ""
			+ "        .confidentiality-info {"
			+ "            grid-area: confidentiality-info;"
			+ "            text-align: right;"
			+ "            color: red;"
			+ "            padding-right: 10px;"
			+ "        }"
			+ "    </style>"
			+ "</head>"
			+ "<body>"
			+ "    <div class=\"grid-container grid-area\">"
			+ "        <div class=\"confidentiality-info\">"
			+ "            CONFIDENTIAL"
			+ "        </div>"
			+ "        <div class=\"report-title\">"
			+ "            TITLE"
			+ "        </div>"
			+ "        <div class=\"generation-date\">"
			+ "            DATE"
			+ "        </div>"
			+ "    </div>"
			+ "</body>"
			+ "</html>";

	private static final Logger log = LoggerFactory.getLogger(Chromehtml2pdfRenderer.class);
	private static String chromehtml2pdf = "/home/mk/.nvm/versions/node/v9.4.0/bin/chromehtml2pdf";

	@Override public PdfDocument render(HtmlDocument document, File destination, Map<String, String> renderParams) {
		try {
			CommandLineExecutor executor = new CommandLineExecutor();
			Process process = executor.command(chromehtml2pdf)
					.withArgument("--out=" + destination.getPath())
					.withArgument("file://" + document.asFile().getPath())
					.withArguments(renderParams)
					.execute();

//			logStream(log, process.getErrorStream());

			System.out.println(CharStreams.toString(new InputStreamReader(process.getInputStream())));
			System.out.println(CharStreams.toString(new InputStreamReader(process.getErrorStream())));
			int exitCode = process.waitFor();


			if (exitCode != 0) {
				throw new PdfgenException("Generating pdf failed due to unknown error.");
			}

			return new PdfDocument(destination);

		} catch (InterruptedException | IOException e) {
			throw new PdfgenException("There was problem executing command.", e);
		}
	}

	@Override public PdfDocument render(List<HtmlDocument> documents, File destination, Map<String, String> renderParams) {
		return null;
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		Chromehtml2pdfRenderer renderer = new Chromehtml2pdfRenderer();

		Map<String, String> params = new HashMap<>();
		params.put("--printBackground", "true");
		params.put("--displayHeaderFooter", "true");
		params.put("--marginTop", "70px");
		params.put("--headerTemplate", "\'<p style=\"font-size: 12px;\">Abecadlo</p>\'");


		HtmlDocument document = new HtmlDocument(new File("/home/mk/Dokumenty/raport.html"));
		File destination = new File("/home/mk/Dokumenty/report-gen-test.pdf");

		renderer.render(document, destination, params);

//		String[] cmd = {chromehtml2pdf, "--out=/home/mk/Dokumenty/report-gen-test.pdf", "file:///home/mk/Dokumenty/raport.html",
//						"--printBackground", "true", "--displayHeaderFooter", "true", "--marginTop", "70px", "--headerTemplate", HEADER};
//
//		Runtime runtime = Runtime.getRuntime();
//		Process p = runtime.exec(cmd);
//
//		System.out.println(CharStreams.toString(new InputStreamReader(p.getInputStream())));
//		System.out.println(CharStreams.toString(new InputStreamReader(p.getErrorStream())));
//		int exitCode = p.waitFor();
//
//
//		if (exitCode != 0) {
//			throw new PdfgenException("Generating pdf failed due to unknown error.");
//		}

	}

}
