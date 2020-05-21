package com.dpforge.hevery.cli.compiler;

import java.io.File;
import java.util.List;

public interface SourceCodeCompiler {

    String getSourceCodeFileExtension();

    void compile(File classPath, File outputDir, List<File> sourceCodeFiles);

}
