package com.dpforge.hevery.installer;

import java.util.List;

class ExecutionResult {
    final int exitCode;

    final List<String> output;

    ExecutionResult(final int exitCode, final List<String> output) {
        this.exitCode = exitCode;
        this.output = output;
    }

    boolean isSuccess() {
        return exitCode == 0;
    }
}
