package com.tratif.pdfgen.helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class CommandLineExecutor {

    private String cmd;
    private List<String> args;

    public CommandLineExecutor() {
        args = new ArrayList<>();
    }

    public CommandLineExecutor command(String cmd) {
        args = new ArrayList<>();
        this.cmd = cmd;
        return this;
    }

    public CommandLineExecutor withArgument(String arg) {
        args.add(arg);
        return this;
    }

    public Process execute() {
        Runtime runtime = Runtime.getRuntime();
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(cmd);
        args.forEach(joiner::add);
        try {
            return runtime.exec(joiner.toString());
        } catch(IOException e) {
            throw new RuntimeException("Running command has failed.", e);
        }
    }

    public CommandLineExecutor withArguments(Map<String, String> properties) {
        properties.entrySet().stream()
                .map(this::formatOptionalArgument)
                .forEach(args::add);

        return this;
    }

    private String formatOptionalArgument(Map.Entry<String, String> entry) {
        if (entry.getValue().equals(""))
            return entry.getKey();
        return entry.getKey() + " " + entry.getValue();
    }
}
