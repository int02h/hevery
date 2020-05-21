package hevery;

import javax.annotation.Nullable;

public interface Hooks {

    interface PreCommit {
        void onPreCommit() throws HookFailedException;
    }

    interface PrepareCommitMsg {
        String onPrepareCommitMsg(
                String message,
                @Nullable CommitSource source,
                @Nullable String sha1
        ) throws HookFailedException;
    }

    interface CommitMsg {
        String onCommitMsg(String message) throws HookFailedException;
    }

}
