package com.tratif.pdfgen.asserts.helpers;

import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ImageParser extends PDFStreamEngine {

    private List<Image> images;

    private ImageParser() {
        images = new ArrayList<>();
    }

    @Override
    protected void processOperator(Operator operator, List<COSBase> operands) throws IOException {
        String operation = operator.getName();
        if (!"Do".equals(operation)) {
            super.processOperator(operator, operands);
            return;
        }

        COSName objectName = (COSName)operands.get(0);
        PDXObject xobject = getResources().getXObject(objectName);
        if(xobject instanceof PDImageXObject)
        {
            PDImageXObject image = (PDImageXObject)xobject;
            images.add(image.getImage());
        }
    }

    public static List<Image> parse(File file) {
        try
        {
            ImageParser parser = new ImageParser();
            PDDocument document = PDDocument.load(file);

            for(PDPage page : document.getPages())
                parser.processPage(page);

            document.close();

            return parser.images;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from PDF file.", e);
        }
    }
}
