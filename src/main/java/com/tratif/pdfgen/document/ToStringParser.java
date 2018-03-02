package com.tratif.pdfgen.document;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.StringJoiner;

import static java.util.stream.Collectors.toList;

public class ToStringParser {

    public static String parse(InputStream inputStream) {
        List<String> lines = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(toList());

        StringJoiner joiner = new StringJoiner(System.getProperty("line.separator"));
        lines.forEach(joiner::add);

        return joiner.toString();
    }

    public static String parse(Reader reader) {
        try (BufferedReader br = new BufferedReader(reader)) {
            StringBuilder sb = new StringBuilder();
            br.lines().forEach(line -> sb.append(line).append(System.getProperty("line.separator")));

            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed closing the reader.", e);
        }
    }

    public static String parse(URL url) {
        try {
            return parse(new InputStreamReader(url.openStream()));
        } catch(IOException e) {
            throw new RuntimeException("Failed opening url stream", e);
        }
    }

    public static String parse(File file) {
        try {
            return parse(new FileInputStream(file));
        } catch(FileNotFoundException e) {
            throw new RuntimeException("File not found", e);
        }
    }

    public static String parse(Path path) {
        return parse(path.toFile());
    }
}
