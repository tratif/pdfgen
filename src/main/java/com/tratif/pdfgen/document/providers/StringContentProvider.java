package com.tratif.pdfgen.document.providers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class StringContentProvider implements ContentProvider {

    private String content;

    public StringContentProvider(String content) {
        this.content = content;
    }

    @Override
    public InputStream getContent() {
        return new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
    }
}
