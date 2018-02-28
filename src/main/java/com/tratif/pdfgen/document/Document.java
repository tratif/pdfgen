package com.tratif.pdfgen.document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Document {

    public static DocumentBuilder fromStaticHtml(String html) {
        return new DocumentBuilder(html);
    }

    public static DocumentBuilder fromStaticHtml(InputStream inputStream) throws IOException {
        List<String> lines = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.toList());

        StringJoiner joiner = new StringJoiner(" ");
        lines.forEach(joiner::add);

        return new DocumentBuilder(joiner.toString());
    }

//    public static DocumentBuilder fromStaticHtml(Reader reader) {
//
//    }
//
//    public static DocumentBuilder fromStaticHtml(URL url) {
//
//    }
//
//    public static DocumentBuilder fromStaticHtml(File file) {
//
//    }
//
//    public static DocumentBuilder fromStaticHtml(Path path) {
//
//    }
}
