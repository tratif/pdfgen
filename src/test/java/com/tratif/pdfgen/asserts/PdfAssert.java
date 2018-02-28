package com.tratif.pdfgen.asserts;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class PdfAssert extends AbstractAssert<PdfAssert, byte[]> {
    public PdfAssert(byte[] bytes) {
        super(bytes, byte[].class);
    }

    public static PdfAssert assertThat(byte[] bytes) {
        return new PdfAssert(bytes);
    }

    public PdfAssert isNotNull() {
        Assertions.assertThat(actual).isNotNull();

        return this;
    }

    public PdfAssert hasMinimumLength(int length) {
        Assertions.assertThat(actual.length).isGreaterThan(length);

        return this;
    }
}
