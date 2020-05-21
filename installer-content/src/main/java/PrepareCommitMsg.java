import hevery.CommitSource;
import hevery.HookFailedException;
import hevery.Hooks;

import javax.annotation.Nullable;

public class PrepareCommitMsg implements Hooks.PrepareCommitMsg {

    @Override
    public String onPrepareCommitMsg(
            String message,
            @Nullable CommitSource source,
            @Nullable String sha1) throws HookFailedException {
        return message;
    }

}
