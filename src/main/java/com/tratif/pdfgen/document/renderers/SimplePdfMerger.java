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
