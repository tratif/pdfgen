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
package com.tratif.pdfgen.document.mergers.pdf;

import com.tratif.pdfgen.document.docs.PdfDocument;
import com.tratif.pdfgen.exceptions.PdfgenException;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

public class InputStreamPdfMerger implements PdfMerger {

	private static final MemoryUsageSetting memoryUsageSetting = MemoryUsageSetting.setupMainMemoryOnly(1024 * 1024 * 128);

	public PdfDocument merge(List<PdfDocument> pdfDocuments) {
		PDFMergerUtility merger = new PDFMergerUtility();
		pdfDocuments.forEach(pdf -> merger.addSource(new BufferedInputStream(pdf.asInputStream())));
		try {
			PipedInputStream inputStream = new PipedInputStream();
			PipedOutputStream outputStream = new PipedOutputStream(inputStream);
			merger.setDestinationStream(outputStream);

			new Thread(() -> {
				try {
					merger.mergeDocuments(memoryUsageSetting);
				} catch (IOException e) {
					throw new PdfgenException("Failed merging files.", e);
				}
			}).start();

			File destination = Files.createTempFile("pdfgen", "pdf").toFile();
			FileUtils.copyInputStreamToFile(inputStream, destination);

			return new PdfDocument(destination);
		} catch (IOException e) {
			throw new PdfgenException("Failed merging files.", e);
		}
	}
}
