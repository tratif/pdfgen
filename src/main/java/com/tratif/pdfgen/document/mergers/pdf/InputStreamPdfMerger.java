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
package com.tratif.pdfgen.document.mergers.pdf;

import com.tratif.pdfgen.document.PDF;
import com.tratif.pdfgen.document.mergers.pdf.PdfMerger;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;

public class InputStreamPdfMerger implements PdfMerger {

	private static final MemoryUsageSetting memoryUsageSetting = MemoryUsageSetting.setupMainMemoryOnly(1024 * 1024 * 128);

	public PDF merge(List<PDF> pdfs) {
		PDFMergerUtility merger = new PDFMergerUtility();
		pdfs.forEach(pdf -> merger.addSource(new BufferedInputStream(pdf.toInputStream())));
		try {
			PipedInputStream inputStream = new PipedInputStream();
			PipedOutputStream outputStream = new PipedOutputStream(inputStream);
			merger.setDestinationStream(outputStream);

			new Thread(() -> {
				try {
					merger.mergeDocuments(memoryUsageSetting);
				} catch (IOException e) {
					throw new RuntimeException("Failed merging files.", e);
				}
			}).start();

			return new PDF(inputStream);
		} catch (IOException e) {
			throw new RuntimeException("Failed merging files.", e);
		}
	}
}
