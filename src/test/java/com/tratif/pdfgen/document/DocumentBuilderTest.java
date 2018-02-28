package com.tratif.pdfgen.document;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class DocumentBuilderTest {

    @Test
    public void isProperPdfFile() {
        byte[] bytes = Document.fromStaticHtml("<h1>hello, world!</h1>")
                .toPdf();

        assertThat(bytes).isNotNull();
        assertThat(bytes.length).isGreaterThan(4);

        // header
        assertThat(bytes[0]).isEqualTo((byte) 0x25); // %
        assertThat(bytes[1]).isEqualTo((byte) 0x50); // P
        assertThat(bytes[2]).isEqualTo((byte) 0x44); // D
        assertThat(bytes[3]).isEqualTo((byte) 0x46); // F
        assertThat(bytes[4]).isEqualTo((byte) 0x2D); // -

        if(bytes[5] == (byte) 0x31 && bytes[6] == (byte) 0x2E && bytes[7] == (byte) 0x33) // version is 1.3 ?
        {
            // file terminator
            assertThat(bytes[bytes.length - 7]).isEqualTo((byte) 0x25); // %
            assertThat(bytes[bytes.length - 6]).isEqualTo((byte) 0x25); // %
            assertThat(bytes[bytes.length - 5]).isEqualTo((byte) 0x45); // E
            assertThat(bytes[bytes.length - 4]).isEqualTo((byte) 0x4F); // O
            assertThat(bytes[bytes.length - 3]).isEqualTo((byte) 0x46); // F
            assertThat(bytes[bytes.length - 2]).isEqualTo((byte) 0x20); // SPACE
            assertThat(bytes[bytes.length - 1]).isEqualTo((byte) 0x0A); // EOL
            return;
        }

        if(bytes[5] == (byte) 0x31 && bytes[6] == (byte) 0x2E && bytes[7] == (byte) 0x34) // version is 1.4 ?
        {
            // file terminator
            assertThat(bytes[bytes.length - 6]).isEqualTo((byte) 0x25); // %
            assertThat(bytes[bytes.length - 5]).isEqualTo((byte) 0x25); // %
            assertThat(bytes[bytes.length - 4]).isEqualTo((byte) 0x45); // E
            assertThat(bytes[bytes.length - 3]).isEqualTo((byte) 0x4F); // O
            assertThat(bytes[bytes.length - 2]).isEqualTo((byte) 0x46); // F
            assertThat(bytes[bytes.length - 1]).isEqualTo((byte) 0x0A); // EOL
            return;
        }

        Assertions.fail("Bad file");
    }

//    @Test
//    public void containsProvidedText() {
//        byte[] bytes = Document.fromStaticHtml("<h1>hello, world!</h1>")
//                .toPdf();
//
//        String content = new String(bytes, StandardCharsets.UTF_8);
//
//
//
//        assertThat(content).containsSequence("hello")
//        .containsSequence("world");
//    }
}