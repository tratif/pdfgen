package com.tratif.pdfgen.document;

import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PDF {
    private static final String ID = UUID.randomUUID().toString();
    private List<Page> pages;

    public PDF() {
        pages = new ArrayList<>();
    }

    public void addPage(Page page) {
        pages.add(page);
    }

    //TODO: test
    public void save(File file) {
        List<String> tempFileRegistry = new ArrayList<>();
        int counter = 0;
        for(Page page : pages) {
            DocumentBuilder db = new DocumentBuilder(page.getContent());
            db.setParameters(page.getParams());
            try {
                Path filename = getTempFilename(counter++);
                tempFileRegistry.add(filename.toString());
                Files.write(filename, db.toPdf());
            } catch (IOException e) {
                throw new RuntimeException("There was a problem saving to file.", e);
            }
        }

        PDFMergerUtility merger = new PDFMergerUtility();
        merger.setDestinationFileName("pdfgen-merged-docs.pdf");
        for(String filepath : tempFileRegistry) {
            try {
                merger.addSource(filepath);
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Should never happen", e);
            }
        }

        try {
            merger.mergeDocuments(null);
        } catch (IOException e) {
            throw new RuntimeException("There was a problem saving to file.", e);
        }
    }

    private Path getTempFilename(int counter) {
        return Paths.get("pdfgen-" + ID + "-" + counter + ".pdf");
    }
}
