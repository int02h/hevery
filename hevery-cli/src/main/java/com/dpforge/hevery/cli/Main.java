package com.dpforge.hevery.cli;

import com.dpforge.hevery.cli.compiler.IncrementalCompiler;
import com.dpforge.hevery.cli.compiler.JavaSourceCodeCompiler;
import com.dpforge.hevery.cli.handler.CommitMsgHandler;
import com.dpforge.hevery.cli.handler.HookHandler;
import com.dpforge.hevery.cli.handler.PreCommitHandler;
import com.dpforge.hevery.cli.handler.PrepareCommitMsgHandler;
import hevery.HookFailedException;
import hevery.Hooks;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import static com.dpforge.hevery.cli.util.FileUtils.ensureDirectoryExists;
import static com.dpforge.hevery.cli.util.TextUtils.isNullOrEmpty;

public class Main {

    public static void main(String[] args) throws Exception {
        final HookRegistry registry = new HookRegistry();
        registry.registerHook("prepare-commit-msg", Hooks.PrepareCommitMsg.class, PrepareCommitMsgHandler.class);
        registry.registerHook("pre-commit", Hooks.PreCommit.class, PreCommitHandler.class);
        registry.registerHook("commit-msg", Hooks.CommitMsg.class, CommitMsgHandler.class);

        main(new CommandLineArguments(args), registry);
    }

    private static void main(
            final CommandLineArguments args,
            final HookRegistry registry
    ) throws IOException, ReflectiveOperationException {
        args.verifySize().atLeast(2);
        final File dir = args.getRequired(0).asFile();
        final String hookName = args.getRequired(1).asString();

        final File classpathDir = new File(dir, "libs");
        final File buildDir = ensureDirectoryExists(new File(dir, "build"));

        final File[] classFiles = new IncrementalCompiler(new JavaSourceCodeCompiler())
                .compile(dir, classpathDir, buildDir);
        final ClassLoader classLoader = new URLClassLoader(new URL[]{buildDir.toURI().toURL()});

        final Class<?> targetClass = registry.getHookClass(hookName);
        for (File classFile : classFiles) {
            final String className = classFile.getName().replace(".class", "");
            final Class<?> clazz = classLoader.loadClass(className);
            if (targetClass.isAssignableFrom(clazz)) {
                //noinspection rawtypes
                final HookHandler rawHandler = registry.getHandler(hookName);
                try {
                    //noinspection unchecked
                    rawHandler.handle(clazz.newInstance(), args.shiftLeft(2));
                } catch (HookFailedException ex) {
                    final String error = (isNullOrEmpty(ex.getMessage())) ? hookName + " failed" : ex.getMessage();
                    System.err.println(error);
                    System.exit(42);
                }
            }
        }
    }

}
