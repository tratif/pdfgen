package com.tratif.pdfgen.document.renderers;

import com.tratif.pdfgen.document.PDF;
import com.tratif.pdfgen.document.builders.PageBuilder;

import java.util.List;

import static java.util.stream.Collectors.toList;

public interface PdfRenderer {

    PDF render(PageBuilder page);

    default List<PDF> render(List<PageBuilder> pages) {
        return pages.stream()
                .map(this::render)
                .collect(toList());
    }
}
