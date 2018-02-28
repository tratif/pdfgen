package com.tratif.pdfgen.document;

public class Document {

    public static DocumentDetails fromStaticHtml(String html) {
        return new DocumentDetails(html);
    }
}
