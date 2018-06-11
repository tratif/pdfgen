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

import com.tratif.pdfgen.document.PDF;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class SimplePdfMerger implements PdfMerger {

	private static final MemoryUsageSetting memoryUsageSetting = MemoryUsageSetting.setupMainMemoryOnly(1024 * 1024 * 128);

	public PDF merge(List<PDF> pdfs) {
		PDFMergerUtility merger = new PDFMergerUtility();
		pdfs.forEach(pdf -> merger.addSource(new ByteArrayInputStream(pdf.toByteArray())));
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		merger.setDestinationStream(outputStream);
		try {
			merger.mergeDocuments(memoryUsageSetting);
			return new PDF(outputStream.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("Failure while merging files.", e);
		}
	}
}
