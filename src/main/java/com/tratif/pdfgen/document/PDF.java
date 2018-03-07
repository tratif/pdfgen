package com.tratif.pdfgen.document;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDF {
    private List<Page> pages;

    public PDF() {
        pages = new ArrayList<>();
    }

    public void addPage(Page page) {
        pages.add(page);
    }

    public void save(File file) {
        if(pages.size() <= 0)
            throw new IllegalStateException("Nothing to save.");

        PDFMergerUtility merger = new PDFMergerUtility();
        PDDocument pdf;
        try {
            pdf = PDDocument.load(pages.get(0).toPdfByteArray());
            for(int i = 1; i < pages.size(); i++) {
                PDDocument toMerge = PDDocument.load(pages.get(i).toPdfByteArray());
                merger.appendDocument(pdf, toMerge);
            }
            pdf.save(file.toString());
            pdf.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
