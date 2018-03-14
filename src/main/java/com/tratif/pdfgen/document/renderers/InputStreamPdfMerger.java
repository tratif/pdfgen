package com.tratif.pdfgen.document.renderers;

import com.tratif.pdfgen.document.PDF;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.*;
import java.util.List;

public class InputStreamPdfMerger implements PdfMerger {

    public PDF merge(List<PDF> pdfs, MemoryUsageSetting memoryUsageSetting) {
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
