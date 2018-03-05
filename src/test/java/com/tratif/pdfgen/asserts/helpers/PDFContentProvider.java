package com.tratif.pdfgen.asserts.helpers;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;

import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFContentProvider implements Closeable {

    private File file;
    private PDDocument document;

    public PDFContentProvider(File file) {
        this.file = file;
        initialize();
    }

    private void initialize() {
        try {
            PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
            parser.parse();
            document = new PDDocument(parser.getDocument());
        } catch(FileNotFoundException e) {
            throw new RuntimeException("File was not found.", e);
        } catch(IOException e) {
            throw new RuntimeException("Error while reading or parsing file.", e);
        }
    }

    public List<BufferedImage> getAllImages() {
        List<BufferedImage> images = new ArrayList<>();

        for(PDPage page : document.getPages()) {
            images.addAll(getImagesFromResources(page.getResources()));
        }

        return images;
    }

    private List<BufferedImage> getImagesFromResources(PDResources resources) {
        List<BufferedImage> images = new ArrayList<>();

        try {
            for (COSName xObjectName : resources.getXObjectNames()) {
                PDXObject xObject = resources.getXObject(xObjectName);

                if (xObject instanceof PDFormXObject) {
                    images.addAll(getImagesFromResources(((PDFormXObject) xObject).getResources()));
                } else if (xObject instanceof PDImageXObject) {
                    images.add(((PDImageXObject) xObject).getImage());
                }
            }
        } catch(IOException e) { }

        return images;
    }

    public String getText() {
        return getText(1, document.getNumberOfPages());
    }

    public String getText(int fromPage, int toPage) {
        try {
            PDFTextStripper textStripper = new PDFTextStripper();
            textStripper.setStartPage(fromPage);
            textStripper.setEndPage(toPage);

            return textStripper.getText(document);
        } catch(IOException e) {
            throw new RuntimeException("Error while reading file.", e);
        }
    }

    @Override
    public void close() {
        try {
            document.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to close the file.", e);
        }
    }
}
