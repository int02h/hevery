package com.dpforge.hevery.cli.handler;

import com.dpforge.hevery.cli.CommandLineArguments;
import hevery.HookFailedException;
import hevery.Hooks;

public class PreCommitHandler implements HookHandler<Hooks.PreCommit> {

    @Override
    public void handle(final Hooks.PreCommit hook, final CommandLineArguments args) throws HookFailedException {
        hook.onPreCommit();
    }

}
