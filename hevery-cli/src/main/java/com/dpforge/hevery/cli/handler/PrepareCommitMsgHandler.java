package com.dpforge.hevery.cli.handler;

import com.dpforge.hevery.cli.CommandLineArguments;
import hevery.CommitSource;
import hevery.HookFailedException;
import hevery.Hooks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PrepareCommitMsgHandler implements HookHandler<Hooks.PrepareCommitMsg> {

    @Override
    public void handle(
            final Hooks.PrepareCommitMsg hook,
            final CommandLineArguments args
    ) throws IOException, HookFailedException {
        args.verifySize().atLeast(1);
        final Path path = args.getRequired(0).asPath();
        final String msg = hook.onPrepareCommitMsg(
                new String(Files.readAllBytes(path)),
                CommitSource.fromValue(args.getOptional(1).asString()),
                args.getOptional(2).asString()
        );
        Files.write(path, msg.getBytes());
    }
}
