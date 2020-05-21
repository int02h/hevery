package com.dpforge.hevery.cli.handler;

import com.dpforge.hevery.cli.CommandLineArguments;
import hevery.HookFailedException;

import java.io.IOException;

@FunctionalInterface
public interface HookHandler<T> {
    void handle(T hook, CommandLineArguments args) throws HookFailedException, IOException;
}
