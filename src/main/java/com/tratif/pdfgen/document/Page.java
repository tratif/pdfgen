package com.tratif.pdfgen.document;

import java.util.HashMap;
import java.util.Map;

public class Page {
    private String content;
    private Map<String, String> params;

    public Page() {
        this("");
    }

    public Page(String content) {
        this.content = content;
        params = new HashMap<>();
    }

    public ParameterBuilder parameters() {
        return new ParameterBuilder(params);
    }

    public String getContent() {
        return content;
    }

    public byte[] toPdfByteArray() {
        DocumentBuilder db = Document.fromStaticHtml(content);
        db.setParameters(params);
        return db.toPdf();
    }
}
