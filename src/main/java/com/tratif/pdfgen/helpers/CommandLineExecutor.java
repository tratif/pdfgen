package com.tratif.pdfgen.helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
            throw new RuntimeException("Running command has failed.");
        }
    }
}
