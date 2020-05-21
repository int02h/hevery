package com.dpforge.hevery.cli.handler;

import com.dpforge.hevery.cli.CommandLineArguments;
import hevery.HookFailedException;
import hevery.Hooks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CommitMsgHandler implements HookHandler<Hooks.CommitMsg> {

    @Override
    public void handle(final Hooks.CommitMsg hook, final CommandLineArguments args) throws HookFailedException, IOException {
        args.verifySize().exactly(1);
        final Path path = args.getRequired(0).asPath();
        final String msg = hook.onCommitMsg(new String(Files.readAllBytes(path)));
        Files.write(path, msg.getBytes());
    }

}
