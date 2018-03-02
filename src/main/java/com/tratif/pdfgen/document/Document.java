package com.tratif.pdfgen.document;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Document {

    public static DocumentBuilder fromStaticHtml(String html) {
        return new DocumentBuilder(html);
    }

    public static DocumentBuilder fromStaticHtml(InputStream inputStream) {
        List<String> lines = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.toList());

        StringJoiner joiner = new StringJoiner(" ");
        lines.forEach(joiner::add);

        return fromStaticHtml(joiner.toString());
    }

    public static DocumentBuilder fromStaticHtml(Reader reader) {
        try (BufferedReader br = new BufferedReader(reader)) {
            StringBuilder sb = new StringBuilder();
            br.lines()
                    .forEach(sb::append);

            return fromStaticHtml(sb.toString());
        } catch (IOException e) {
            throw new RuntimeException("Failed closing the reader.", e);
        }
    }

    public static DocumentBuilder fromStaticHtml(URL url) {
        try {
            return fromStaticHtml(new InputStreamReader(url.openStream()));
        } catch(IOException e) {
            throw new RuntimeException("Failed opening url stream", e);
        }
    }

    public static DocumentBuilder fromStaticHtml(File file) {
        try {
            return fromStaticHtml(new FileInputStream(file));
        } catch(FileNotFoundException e) {
            throw new RuntimeException("File not found", e);
        }
    }

    public static DocumentBuilder fromStaticHtml(Path path) {
        return fromStaticHtml(path.toFile());
    }
}
