package com.tratif.pdfgen;

import com.tratif.pdfgen.document.Document;

public class TestApp {
    public static void main(String[] args) throws Exception {
        byte[] bytes = Document.fromStaticHtml("<h1>hello, world!</h1>")
                .toPdf();
    }
}
