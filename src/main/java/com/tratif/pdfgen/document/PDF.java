package com.tratif.pdfgen.document;

import com.tratif.pdfgen.helpers.Utils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class PDF {

    private InputStream content;

    public PDF(InputStream content) {
        this.content = content;
    }

    public PDF(byte[] content) {
        this.content = new ByteArrayInputStream(content);
    }

    public byte[] toByteArray() {
        try {
            return IOUtils.toByteArray(content);
        } catch (IOException e) {
            throw new RuntimeException("Failed reading input stream", e);
        }
    }

    public void toFile(File destination) {
        if (destination.getParentFile() != null && !destination.getParentFile().exists()) {
            throw new RuntimeException("Following path does not exist: " + destination.getParentFile().toString());
        }

        try {
            Utils.writeStreamToFile(destination, content);
//            FileUtils.copyToFile(content, destination);
        } catch (IOException e) {
            throw new RuntimeException("Failed saving to file.", e);
        }
    }

    public InputStream toInputStream() {
        return content;
    }
}
