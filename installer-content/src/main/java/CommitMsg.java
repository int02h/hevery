import hevery.HookFailedException;
import hevery.Hooks;

public class CommitMsg implements Hooks.CommitMsg {

    @Override
    public String onCommitMsg(String message) throws HookFailedException {
        return message;
    }

}
