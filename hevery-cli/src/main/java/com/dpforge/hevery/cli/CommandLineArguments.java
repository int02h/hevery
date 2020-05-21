package com.dpforge.hevery.cli;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Path;

public class CommandLineArguments {

    private final String[] args;

    CommandLineArguments(String[] args) {
        this.args = args;
    }

    public OptionalArgument getOptional(int index) {
        final String value = (0 <= index && index < args.length) ? args[index] : null;
        return new OptionalArgument(value);
    }

    public RequiredArgument getRequired(int index) {
        if (index < 0 || index >= args.length) {
            throw new IllegalArgumentException("Required argument with index " + index + " does not exist");
        }
        return new RequiredArgument(args[index]);
    }

    CommandLineArguments shiftLeft(int count) {
        String[] shifted = new String[Math.max(0, args.length - count)];
        System.arraycopy(args, count, shifted, 0, shifted.length);
        return new CommandLineArguments(shifted);
    }

    public SizeVerifier verifySize() {
        return new SizeVerifier() {
            @Override
            public void atLeast(int size) {
                if (args.length < size) {
                    throw new IllegalArgumentException("Expected at least " + size + " arguments but was " + args.length);
                }
            }

            @Override
            public void exactly(final int size) {
                if (args.length != size) {
                    throw new IllegalArgumentException("Expected exactly " + size + " arguments but was " + args.length);
                }
            }
        };
    }

    public static class OptionalArgument {

        @Nullable
        private final String value;

        OptionalArgument(@Nullable final String value) {
            this.value = value;
        }

        @Nullable
        public File asFile() {
            return (value != null) ? new File(value) : null;
        }

        @Nullable
        public Path asPath() {
            File file = asFile();
            return (file != null) ? file.toPath() : null;
        }

        @Nullable
        public String asString() {
            return value;
        }
    }

    public static class RequiredArgument {
        private final String value;

        RequiredArgument(final String value) {
            this.value = value;
        }

        public File asFile() {
            return new File(value);
        }

        public Path asPath() {
            return asFile().toPath();
        }

        public String asString() {
            return value;
        }
    }

    public interface SizeVerifier {
        void atLeast(int size);

        void exactly(int size);
    }
}
