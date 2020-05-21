package hevery;

import javax.annotation.Nullable;

public enum CommitSource {
    MESSAGE("message"),
    TEMPLATE("template"),
    MERGE("merge"),
    SQUASH("squash"),
    COMMIT("commit");

    private final String value;

    CommitSource(final String value) {
        this.value = value;
    }

    @Nullable
    public static CommitSource fromValue(@Nullable final String value) {
        for (CommitSource source : values()) {
            if (source.value.equals(value)) {
                return source;
            }
        }
        return null;
    }
}
