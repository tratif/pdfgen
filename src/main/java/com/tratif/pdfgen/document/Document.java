package com.tratif.pdfgen.document;

public class Document {

    public static DocumentBuilder fromStaticHtml(String html) {
        return new DocumentBuilder(html);
    }
}
