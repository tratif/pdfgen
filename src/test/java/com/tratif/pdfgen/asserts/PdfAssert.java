package com.tratif.pdfgen.asserts;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

public class PdfAssert extends AbstractAssert<PdfAssert, byte[]> {

    PdfAssert(byte[] bytes) {
        super(bytes, PdfAssert.class);
    }

    public static PdfAssert assertThat(byte[] bytes) {
        return new PdfAssert(bytes);
    }

    public PdfAssert hasMinimumLength(int length) {
        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(actual.length).isGreaterThan(length);

        return this;
    }

    public PdfAssert isProperPdfFile() {
        Assertions.assertThat(actual).isNotNull();
        hasMinimumLength(4);

        //header
        Assertions.assertThat(actual[0]).isEqualTo((byte) 0x25); // %
        Assertions.assertThat(actual[1]).isEqualTo((byte) 0x50); // P
        Assertions.assertThat(actual[2]).isEqualTo((byte) 0x44); // D
        Assertions.assertThat(actual[3]).isEqualTo((byte) 0x46); // F
        Assertions.assertThat(actual[4]).isEqualTo((byte) 0x2D); // -

        if(actual[5] == (byte) 0x31 && actual[6] == (byte) 0x2E && actual[7] == (byte) 0x33) // version is 1.3 ?
        {
            // file terminator
            Assertions.assertThat(actual[actual.length - 7]).isEqualTo((byte) 0x25); // %
            Assertions.assertThat(actual[actual.length - 6]).isEqualTo((byte) 0x25); // %
            Assertions.assertThat(actual[actual.length - 5]).isEqualTo((byte) 0x45); // E
            Assertions.assertThat(actual[actual.length - 4]).isEqualTo((byte) 0x4F); // O
            Assertions.assertThat(actual[actual.length - 3]).isEqualTo((byte) 0x46); // F
            Assertions.assertThat(actual[actual.length - 2]).isEqualTo((byte) 0x20); // SPACE
            Assertions.assertThat(actual[actual.length - 1]).isEqualTo((byte) 0x0A); // EOL
            return this;
        }

        if(actual[5] == (byte) 0x31 && actual[6] == (byte) 0x2E && actual[7] == (byte) 0x34) // version is 1.4 ?
        {
            // file terminator
            Assertions.assertThat(actual[actual.length - 6]).isEqualTo((byte) 0x25); // %
            Assertions.assertThat(actual[actual.length - 5]).isEqualTo((byte) 0x25); // %
            Assertions.assertThat(actual[actual.length - 4]).isEqualTo((byte) 0x45); // E
            Assertions.assertThat(actual[actual.length - 3]).isEqualTo((byte) 0x4F); // O
            Assertions.assertThat(actual[actual.length - 2]).isEqualTo((byte) 0x46); // F
            Assertions.assertThat(actual[actual.length - 1]).isEqualTo((byte) 0x0A); // EOL
            return this;
        }

        Assertions.fail("Not proper pdf file");
        return this;
    }

    public PdfAssert contains(String str) {
        Assertions.assertThat(actual).isNotNull();

        File file;
        try {
            file = File.createTempFile("pdfgen", ".pdf");
            Files.write(file.toPath(), actual);
        } catch(IOException e) {
            throw new RuntimeException("Failed to create temp file or write to it.", e);
        }

        PDFTextStripper pdfStripper;
        PDDocument pdDoc;
        COSDocument cosDoc;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            PDFParser parser = new PDFParser(randomAccessFile);
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            pdfStripper.setStartPage(1);
            pdfStripper.setEndPage(5);
            String parsedText = pdfStripper.getText(pdDoc);

            Assertions.assertThat(parsedText).containsSubsequence(str);
        } catch (Exception e) {
            e.printStackTrace();
        }

        file.delete();
        return this;
    }
}
