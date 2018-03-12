package com.tratif.pdfgen.document;

public class PDF {

    private byte[] content;

    public PDF() {

    }

    public PDF(byte[] content) {
        this.content = content;
    }

    public byte[] toByteArray() {
        return content;
    }
}
