package com.tratif.pdfgen.document;

import java.util.HashMap;
import java.util.Map;

public class Page {

    private String content;
    private Map<String, String> params;

    public Page() {
        this("", new HashMap<>());
    }

    public Page(String content) {
        this(content, new HashMap<>());
    }

    public Page(String content, Map<String, String> params) {
        this.content = content;
        this.params = params;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
