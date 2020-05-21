package com.dpforge.hevery.installer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;

class CommandExecutor {
    private final File workingDir;

    CommandExecutor(final File workingDir) {
        this.workingDir = workingDir;
    }

    ExecutionResult execute(final String command) throws IOException {
        final Process process = new ProcessBuilder()
                .directory(workingDir)
                .command(command.split(" "))
                .start();
        final int exitCode;
        try {
            exitCode = process.waitFor();
        } catch (InterruptedException e) {
            InterruptedIOException rethrow = new InterruptedIOException("Fail to wait for process finished");
            rethrow.initCause(e);
            throw rethrow;
        }
        return new ExecutionResult(exitCode, convertInputStringToList(process.getInputStream()));
    }

    private static List<String> convertInputStringToList(InputStream inputStream) throws IOException {
        List<String> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                result.add(line + System.getProperty("line.separator"));
            }
        }
        return result;
    }
}
