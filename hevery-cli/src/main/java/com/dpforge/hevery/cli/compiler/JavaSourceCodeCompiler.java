package com.dpforge.hevery.cli.compiler;

import javax.tools.ToolProvider;
import java.io.File;
import java.util.List;

public class JavaSourceCodeCompiler implements SourceCodeCompiler {

    @Override
    public String getSourceCodeFileExtension() {
        return ".java";
    }

    @Override
    public void compile(File classPath, File outputDir, List<File> sourceCodeFiles) {
        for (File src : sourceCodeFiles) {
            final int code = ToolProvider.getSystemJavaCompiler().run(
                    null,
                    null,
                    null,
                    "-d",
                    outputDir.getAbsolutePath(),
                    "-classpath",
                    getClassPath(classPath),
                    src.getAbsolutePath());
            if (code != 0) {
                throw new RuntimeException("Compilation failed");
            }
        }
    }

    private String getClassPath(final File classPath) {
        final File[] jars = new FileCollector(".jar").collect(classPath);
        final StringBuilder builder = new StringBuilder();
        for (File jar : jars) {
            if (builder.length() > 0) {
                builder.append(File.pathSeparatorChar);
            }
            builder.append(jar.getAbsolutePath());
        }
        return builder.toString();
    }
}
