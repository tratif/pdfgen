package com.tratif.pdfgen.document.renderers;

import com.tratif.pdfgen.document.PDF;
import org.apache.pdfbox.io.MemoryUsageSetting;

import java.util.List;

public interface PdfMerger {

    PDF merge(List<PDF> pdfs);
}
