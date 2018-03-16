package com.tratif.pdfgen.document.renderers;

import java.util.Map;

public interface HtmlRenderer {

    String render(String htmlTemplate, Map<String, Object> params);
}
