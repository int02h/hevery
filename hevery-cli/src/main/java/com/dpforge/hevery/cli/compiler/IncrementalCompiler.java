package com.dpforge.hevery.cli.compiler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dpforge.hevery.cli.util.FileUtils.getNameWithoutExtension;

public class IncrementalCompiler {
    private final SourceCodeCompiler compiler;
    private final FileCollector sourceCollector;
    private final FileCollector classCollector;

    public IncrementalCompiler(final SourceCodeCompiler compiler) {
        this.compiler = compiler;
        this.sourceCollector = new FileCollector(compiler.getSourceCodeFileExtension());
        this.classCollector = new FileCollector(".class");
    }

    public File[] compile(final File sourceDir, final File classpathDir, final File outputDir) {
        final File[] classFiles = classCollector.collect(outputDir);
        final Map<String, File> classFileNameToFile = new HashMap<>();
        for (File classFile : classFiles) {
            classFileNameToFile.put(getNameWithoutExtension(classFile), classFile);
        }

        final File[] sourceFiles = sourceCollector.collect(sourceDir);
        final List<File> toCompile = new ArrayList<>();

        for (File sourceFile : sourceFiles) {
            final File classFile = classFileNameToFile.get(getNameWithoutExtension(sourceFile));
            if (classFile == null || sourceFile.lastModified() > classFile.lastModified()) {
                toCompile.add(sourceFile);
            }
        }

        if (!toCompile.isEmpty()) {
            compiler.compile(classpathDir, outputDir, toCompile);
        }

        return classCollector.collect(outputDir);
    }
}
