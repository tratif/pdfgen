package com.tratif.pdfgen.document.providers;

import java.io.InputStream;

public class InputStreamContentProvider implements ContentProvider {

    private InputStream content;

    public InputStreamContentProvider(InputStream content) {
        this.content = content;
    }

    @Override
    public InputStream getContent() {
        return content;
    }
}
